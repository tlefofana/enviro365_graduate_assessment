package com.eviro.assessment.grad001.thabisolefofana.fileparser;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class ProcessFile implements FileParser {
    private final static String dbUrl = "jdbc:h2:mem:appDB";

    static final String USER = "sa";
    static final String PASS = "";

    private String name;
    private String surname;
    private String imageType;
    @Override
    public void parseCSV(File csvFile) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {

        requireNonNull( csvFile );
        if( ! (csvFile.exists() && csvFile.canRead() )){
            throw new FileNotFoundException( "Required CSV input file " + csvFile.getPath() + " not found." );
        }

        parseCsvSource( new LineNumberReader(
                new FileReader( csvFile )
        ));
    }

    @Override
    public File convertCSVDataToImage(String base64ImageData) throws IOException {

        byte[] data = Base64.decodeBase64(base64ImageData);

        String fileType = this.imageType.trim().split("/")[1];
        String filePath = "src/main/resources/static/"+this.name+"."+fileType;
        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(data);
        }
        return new File(filePath);
    }

    @Override
    public URI createImageLink(File fileImage) throws URISyntaxException {

        String fileType = this.imageType.trim().split("/")[1];
        String fileName = this.name+"."+fileType;

        final String uri = "http://localhost:8080/v1/api/image/"+this.name+"/"+this.surname+"/"+fileName;
        Map<String, String> params = new HashMap<String, String>();
        params.put("str", "my_String");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class, params);

        return new URI(response.replace("\"","").trim());
    }

    public void parseCsvSource( LineNumberReader lnr ) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {
        String line;

        lnr.readLine();
        while ((line = lnr.readLine()) != null) {
            String[] columns = getColumns(line);

            this.name = columns[0];
            this.surname = columns[1];
            this.imageType = columns[2];

            File file = convertCSVDataToImage(columns[3]);
            URI uri =createImageLink(file);

            insertData(lnr.getLineNumber() - 1, columns[0], columns[1], uri);
        }
    }

    public String[] getColumns(String line) {
        return line.split(",");
    }

    public static void insertData(int id, String name, String surname, URI httpimagelink  ) throws SQLException, SQLException, ClassNotFoundException, MalformedURLException {

        try(final Connection connection = DriverManager.getConnection(dbUrl,USER,PASS); final PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO account_profile(id, name, surname, httpimagelink) VALUES (?, ?, ?, ?)"
        )){
            stmt.setInt( 1, id );
            stmt.setString( 2, name );
            stmt.setString( 3, surname );
            stmt.setString( 4, httpimagelink.toString() );
            final boolean gotAResultSet = stmt.execute();

            if( gotAResultSet ){
                throw new RuntimeException( "Unexpectedly got a SQL resultset." );
            }else{
                final int updateCount = stmt.getUpdateCount();
                if( updateCount == 1 ){
                    System.out.print("");
                }else{
                    throw new RuntimeException( "Expected 1 row to be inserted, but got " + updateCount );
                }
            }
        }
    }
}
