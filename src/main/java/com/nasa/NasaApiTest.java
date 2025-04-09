package com.nasa;

import com.google.gson.Gson;
import model.PhotoResponse;
import model.Photo;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NasaApiTest {
    public static void main(String[] args) {
        try {
            String apiKey = "cmOHvKjvpP6FWd45nVu4I3hUZ4JUsZOZKCgC21D1";
            String urlStr = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=" + apiKey;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            PhotoResponse response = gson.fromJson(reader, PhotoResponse.class);
            reader.close();

            List<Photo> photos = response.getPhotos();
            for (int i = 0; i < Math.min(5, photos.size()); i++) {
                System.out.println(photos.get(i));
                System.out.println("-----------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}