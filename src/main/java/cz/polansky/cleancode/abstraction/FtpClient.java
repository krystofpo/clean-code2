package cz.polansky.cleancode.abstraction;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FtpClient {

    public List<File> listFiles(String path){
        //something
        return new ArrayList<>();
    }
}
