/**
 * Controller for the NASA Mars Photo Viewer application.
 * Handles UI interaction and API communication for loading Mars rover photos.
 *
 * Created by Kenlly Mosquera
 * For COMP JAVA - Assignment 2
 */

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;

import java.io.InputStream;
import java.net.URL;

public class PhotoDetailController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label idLabel, earthDateLabel, cameraLabel;

    @FXML
    private Button backButton;

    private Stage stage;

    public void setPhoto(Photo photo, Stage currentStage) {
        this.stage = currentStage;

        String imageUrl = photo.getImageUrl().replace("http://", "https://");
        System.out.println("Image URL: " + imageUrl);

        try {
            // Fallback-safe method: download image and wrap it in Image object
            InputStream stream = new URL(imageUrl).openStream();
            Image image = new Image(stream);
            imageView.setImage(image);

            // Optional debug output
            System.out.println("Image loading error: " + image.isError());
            System.out.println("Image width: " + image.getWidth());
            System.out.println("Image height: " + image.getHeight());

            // Make sure image view has proper sizing
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(560);
            imageView.setFitHeight(280);
            imageView.setSmooth(true);
            imageView.setVisible(true);

        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }

        // Set metadata labels
        idLabel.setText("Photo ID: " + photo.getId());
        earthDateLabel.setText("Earth Date: " + photo.getEarthDate());
        if (photo.getCamera() != null) {
            cameraLabel.setText("Camera: " + photo.getCamera().getFullName());
        } else {
            cameraLabel.setText("Camera: Unknown");
        }

        // Handle back button
        backButton.setOnAction(e -> stage.setScene(MainController.getMainScene()));
    }
}