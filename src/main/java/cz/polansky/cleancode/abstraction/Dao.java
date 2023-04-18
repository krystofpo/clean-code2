package cz.polansky.cleancode.abstraction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dao {
    public void persist(Publication publication) {

    }

    public List<Metadata> findMetadataFor(Publication publication) {
        return null;
    }
}
