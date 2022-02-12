package com.example.demo;

import com.example.demo.scripts.DataScriptFloby;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Start implements Initializable {

    @FXML
    private ImageView a;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            int j=0;
            @Override
            public void run() {
                if (j==1){

                    Platform.runLater(() -> {
                        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("intro.fxml"));

                        Stage stage = (Stage) a.getScene().getWindow();
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
        },0,200);
    }
}
