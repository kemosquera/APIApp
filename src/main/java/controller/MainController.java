/**
 * Controller for the NASA Mars Photo Viewer application.
 * Handles UI interaction and API communication for loading Mars rover photos.
 *
 * Created by Kenlly Mosquera
 * For COMP JAVA - Assignment 2
 */

package controller;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoResponse;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainController {

    @FXML
    private TextField solField;

    @FXML
    private Button loadButton;

    @FXML
    private ListView<String> photoListView;

    private final String API_KEY = "cmOHvKjvpP6FWd45nVu4I3hUZ4JUsZOZKCgC21D1";

    // Store the current list of Photo objects for selection
    private List<Photo> currentPhotos;

    // Store the main scene so we can go back from the detail scene
    private static Scene mainScene;

    public static Scene getMainScene() {
        return mainScene;
    }

    @FXML
    public void initialize() {
        loadButton.setOnAction(e -> loadPhotos());
    }

    /**
     * Loads photos from the NASA API using the given sol number,
     * then displays them in the ListView.
     */
    private void loadPhotos() {
        try {
            String sol = solField.getText().trim();
            if (sol.isEmpty()) return;

            String urlStr = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=" + sol + "&api_key=" + API_KEY;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            PhotoResponse response = gson.fromJson(reader, PhotoResponse.class);
            reader.close();

            currentPhotos = response.getPhotos(); // store it for click use
            photoListView.getItems().clear();

            if (currentPhotos.isEmpty()) {
                photoListView.getItems().add("No photos found for Sol " + sol);
            } else {
                for (Photo p : currentPhotos) {
                    photoListView.getItems().add(p.toString());
                }
            }

            // Store the main scene (first time only)
            if (mainScene == null) {
                mainScene = loadButton.getScene();
            }

            // Double click on a photo to show detail view
            photoListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    int selectedIndex = photoListView.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0 && selectedIndex < currentPhotos.size()) {
                        showPhotoDetails(currentPhotos.get(selectedIndex));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            photoListView.getItems().add("Error loading photos.");
        }
    }

    /**
     * Switches to the detail view scene and passes the selected photo.
     */
    private void showPhotoDetails(Photo selectedPhoto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/photo-detail.fxml"));
            Scene detailScene = new Scene(loader.load());

            PhotoDetailController controller = loader.getController();
            Stage stage = (Stage) photoListView.getScene().getWindow();
            controller.setPhoto(selectedPhoto, stage);
            stage.setScene(detailScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}