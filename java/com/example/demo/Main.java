package com.example.demo;

import com.example.demo.scripts.DataScriptFloby;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    public static double delta;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Pane root = fxmlLoader.load();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        DataScriptFloby.widthScreenWindowGame = screenBounds.getWidth();
        DataScriptFloby.heightScreenWindowGame = screenBounds.getHeight();
        DataScriptFloby.deltaScreenWindowGame = screenBounds.getWidth() / 1280;

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.getIcons().add(new Image(new FileInputStream("src/main/ico.png")));

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}