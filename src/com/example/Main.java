package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;

public class Main extends Application {

    private Label uriLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Open URI Example");

        VBox root = new VBox();
        root.setPadding(new Insets(20, 20, 20, 20));
        uriLabel = new Label();
        uriLabel.setText("No URI opened");
        root.getChildren().add(uriLabel);

        Scene scene = new Scene(root, 200, 60);
        primaryStage.setScene(scene);
        primaryStage.show();

        if (!getParameters().getRaw().isEmpty()) {
            uriLabel.setText(getParameters().getRaw().get(0));
        }

        Desktop.getDesktop().setOpenURIHandler((event) -> {
            System.out.println("Open URI: " + event.getURI());
            Platform.runLater(() -> {
                uriLabel.setText(event.getURI().toString());
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
