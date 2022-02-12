package com.example.demo;

import com.example.demo.scripts.DataMusic;
import com.example.demo.scripts.DataScriptFloby;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Intro {

    public static double deltaMainScene = DataScriptFloby.deltaScreenWindowGame;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MediaView media;

    private MediaPlayer mediaPlayer;

    @FXML
    void initialize() {

        DataMusic.isMenuMusic = true;
        DataMusic.startMenuMusic();

        media.setFitWidth(1280 * deltaMainScene);
        media.setFitHeight(720 * deltaMainScene);

        try {
            mediaPlayer = new MediaPlayer(new Media((new File("src/main/intro/intro.mp4")).toURI().toURL().toString()));
            media.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            int j=0;
            @Override
            public void run() {
                if (j==1){
                    Platform.runLater(() -> {
                        mediaPlayer.stop();

                        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"));

                        Stage stage = (Stage) media.getScene().getWindow();
                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = fxmlLoader.getRoot();
                        Scene scene = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);
                        stage.setScene(scene);
                        stage.show();
                    });

                    timer.cancel();
                    return;
                }
                j++;
            }
        },0,1);
    }
}
