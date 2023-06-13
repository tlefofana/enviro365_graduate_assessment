package com.eviro.assessment.grad001.thabisolefofana.fileparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

@SpringBootApplication
public class FileParserApplication {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {
        SpringApplication.run(FileParserApplication.class, args);

        ProcessFile pf = new ProcessFile();
        String path = new File("src/main/resources/1672815113084-GraduateDev_AssessmentCsv_Ref003.csv").getAbsolutePath();
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException("file not found...");
        }
        pf.parseCSV(file);
    }
}
