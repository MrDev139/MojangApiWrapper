package me.mrdev.mojang;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Requests {


    public String PostRequest(String url , String... args) {
        try {
            URL site = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type" , "application/json; utf-8");
            connection.setDoOutput(true);
            String[] nonNull = Arrays.stream(args)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
            if(nonNull.length == 0) return null;
            String Args = new Gson().toJson(nonNull);
            try (OutputStream out = connection.getOutputStream()) {
                byte[] bytes = Args.getBytes(StandardCharsets.UTF_8);
                out.write(bytes , 0 , bytes.length);
            }

            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input , StandardCharsets.UTF_8));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line.trim());
            }
            reader.close();
            connection.disconnect();
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String GetRequest(String url) {
        try {
            URL site = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input , StandardCharsets.UTF_8));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line.trim());
            }
            reader.close();
            connection.disconnect();
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
