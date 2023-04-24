package cz.polansky.cleancode.abstraction.clean;

import cz.polansky.cleancode.abstraction.Error;
import cz.polansky.cleancode.abstraction.*;
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
import java.util.stream.Stream;

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
        clients.stream()
                .filter(this::isClientsBusinessHoursNow)
                .forEach(this::downloadAndConvertAndPersistPublicationsFor);
    }

    private void downloadAndConvertAndPersistPublicationsFor(Client client) {
        List<Error> errors = new ArrayList<>();

        Stream<List<String>> linesFromFiles = readLinesFromFilesInFtpAndFillErrors(client, errors);
        Stream<String> csvRows = convertToCsvRowsAndFillErrors(client, errors, linesFromFiles);
        Stream<Publication> publications = convertToPublicationsAndFillErrors(client, errors, csvRows);
        long publicationsCount = persistAndCountPublications(publications);

        errorReportingService.sendErrorReport(client, errors);
        monitoringService.record(client, publicationsCount);
    }

    private Stream<List<String>> readLinesFromFilesInFtpAndFillErrors(Client client, List<Error> errors) {
        return ftpClient.listFiles(client.getRemoteDirectory()).stream()
                .filter(file -> !file.isDirectory())
                .map(file -> {
                    try {
                        return Files.readAllLines(file.toPath());
                    } catch (IOException e) {
                        errors.add(new Error("Cannot read from file...."));
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    private Stream<String> convertToCsvRowsAndFillErrors(Client client, List<Error> errors, Stream<List<String>> linesFromFiles) {
        return linesFromFiles
                .map(lines -> csvReader.readContent(client.isContainsHeader(), lines))
                .flatMap(Collection::stream)
                .peek(content -> {
                    if (!isContentValid(client, content)) {
                        errors.add(new Error("invalid Content...."));
                    }
                })
                .filter(content -> isContentValid(client, content));
    }

    private boolean isContentValid(Client client, String content) {
        return content.startsWith(client.getFormat().prefix) && !content.contains(INVALID_CHAR);
    }

    private Stream<Publication> convertToPublicationsAndFillErrors(Client client, List<Error> errors, Stream<String> csvRows) {
        Formatter formatter = formatService.getFormatter(client.getFormat());
        return csvRows
                .map(content -> {
                    try {
                        Publication publication = convertToPublicationAndSetLink(formatter, content);
                        return publication;
                    } catch (Exception e) {
                        errors.add(new Error("cannot convert to publication..."));
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    private long persistAndCountPublications(Stream<Publication> publications) {
        return publications
                .peek(dao::persist)
                .count();
    }

    private Publication convertToPublicationAndSetLink(Formatter formatter, String content) throws Exception {
        Publication publication = formatter.convertToPublication(content);
        List<Metadata> metadata = dao.findMetadataFor(publication);
        publication.setCreated(LocalDateTime.now());
        if (isEmpty(metadata)) {
            publication.setLink(NONE);
        } else if (metadata.size() == 1) {
            publication.setLink(ONE);
        } else {
            publication.setLink(MULTIPLE);
        }
        return publication;
    }

    private boolean isClientsBusinessHoursNow(Client client) {
        LocalTime now = LocalTime.now();
        return (client.getTimeRange().getFrom().equals(now) || client.getTimeRange().getFrom().isBefore(now))
                && (client.getTimeRange().getTo().equals(now) || client.getTimeRange().getTo().isAfter(now));
    }
}
