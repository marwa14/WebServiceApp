package com.example.hp.callwebserviceapplication.Tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP on 09/11/2017.
 */

public class UtilsClass {

    public String getResponse(String url) throws IOException {
        String response = "";
        URL url_service = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url_service.openConnection();
        connection.setRequestMethod(Constantes.getMethod);
        connection.connect();
        if (connection.getResponseCode() == Constantes.codeSuccess) {
            InputStream stream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }
            rd.close();
            response = buffer.toString();
            connection.disconnect();
            return response;
        }


        return response;
    }
}
