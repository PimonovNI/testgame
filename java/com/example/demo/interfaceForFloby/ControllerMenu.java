package com.example.demo.interfaceForFloby;

import com.example.demo.scripts.DataScriptFloby;
import com.example.demo.scripts.ScriptMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ex, save, load;

    @FXML
    private VBox Vbox;

    @FXML
    void exited(ActionEvent event) {
        Stage stage = (Stage) ex.getScene().getWindow();
        stage.close();
        DataScriptFloby.isExitedTheGame = true;
    }

    @FXML
    void save(ActionEvent event) {
        DataScriptFloby.saveDataGame();
    }

    @FXML
    void load(ActionEvent event) {
        DataScriptFloby.loadDataGame();
        ScriptMain.loadScene = true;
        ((Stage) ex.getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            Scene scene = Vbox.getScene();
            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE){
                    ((Stage) scene.getWindow()).close();
                }
            });
        });
        Vbox.setStyle("-fx-background-color: rgba(0,0,0,0);");
    }
}
