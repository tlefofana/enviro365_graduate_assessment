package com.eviro.assessment.grad001.thabisolefofana.fileparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public interface FileParser {

    void parseCSV(File csvFile) throws IOException, SQLException, ClassNotFoundException, URISyntaxException;
    File convertCSVDataToImage(String base64ImageData) throws IOException;
    URI createImageLink(File fileImage) throws URISyntaxException;
}
