package cz.polansky.cleancode.abstraction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static cz.polansky.cleancode.abstraction.Link.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@AllArgsConstructor
public class LoadService {
    private List<Client> clients;
    private FtpClient ftpClient;
    private CsvReader csvReader;
    private FormatService formatService;
    private ErrorReportingService errorReportingService;
    private Dao dao;
    private MonitoringService monitoringService;

    private final static String INVALID_CHAR = "@&?";

    public void load() {
        LocalTime now = LocalTime.now();
        clients.stream()
                .filter(client -> (client.getTimeRange().getFrom().equals(now) || client.getTimeRange().getFrom().isBefore(now))
                        && (client.getTimeRange().getTo().equals(now) || client.getTimeRange().getTo().isAfter(now)))
                .forEach(client -> {
                    List<Error> errors = new ArrayList<>();
                    final AtomicInteger counter = new AtomicInteger();
                    Formatter formatter = formatService.getFormatter(client.getFormat());
                    ftpClient.listFiles(client.getRemoteDirectory()).stream()
                            .filter(file -> !file.isDirectory())
                            .map(file -> {
                                try {
                                    return Files.readAllLines(file.toPath());
                                } catch (IOException e) {
                                    errors.add(new Error("Cannot read from file...."));
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .map(lines -> csvReader.readContent(client.isContainsHeader(), lines))
                            .flatMap(Collection::stream)
                            .peek(content -> {
                                if (!content.startsWith(client.getFormat().prefix) || content.contains(INVALID_CHAR)) {
                                    errors.add(new Error("invalid Content...."));
                                }
                            })
                            .filter(content -> content.startsWith(client.getFormat().prefix) && !content.contains(INVALID_CHAR))
                            .map(content -> {
                                try {
                                    Publication publication = formatter.convertToPublication(content);
                                    List<Metadata> metadata = dao.findMetadataFor(publication);
                                    publication.setCreated(LocalDateTime.now());
                                    if (isEmpty(metadata)){
                                        publication.setLink(NONE);
                                    } else if(metadata.size()==1){
                                        publication.setLink(ONE);
                                    } else {
                                        publication.setLink(MULTIPLE);
                                    }
                                    return publication;
                                } catch (Exception e) {
                                    errors.add(new Error("cannot convert to format..."));
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .forEach(publication -> {
                                dao.persist(publication);
                                counter.incrementAndGet();
                            });

                    errorReportingService.sendErrorReport(client, errors);
                    monitoringService.record(client, counter.get());
                });
    }
}
