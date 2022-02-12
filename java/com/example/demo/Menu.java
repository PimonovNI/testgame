package com.example.demo;

import com.example.demo.proceduralGeneration.ControllerMainGeneration;
import com.example.demo.scripts.DataGraphics;
import com.example.demo.scripts.DataMusic;
import com.example.demo.scripts.DataScriptFloby;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Menu {

    public static double deltaMainScene = DataScriptFloby.deltaScreenWindowGame;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView background, snow, load;

    @FXML
    private Button cont, newGame, exit;

    @FXML
    void initialize() {
        DataScriptFloby.myItems.add("0");
        DataScriptFloby.myItems.add("1");
        DataScriptFloby.myItems.add("1");
        DataScriptFloby.myItems.add("2");
        DataScriptFloby.myItems.add("2");
        DataScriptFloby.myItems.add("4");
        DataScriptFloby.myItems.add("5");
        DataScriptFloby.myItems.add("5");
        DataScriptFloby.myItems.add("6");
        DataScriptFloby.myItems.add("6");
        DataScriptFloby.myItems.add("7");
        DataScriptFloby.myItems.add("7");
        DataScriptFloby.myItems.add("8");
        DataScriptFloby.myItems.add("9");
        DataScriptFloby.myItems.add("9");
        DataScriptFloby.myItems.add("10");
        DataScriptFloby.myItems.add("11");
        DataScriptFloby.myItems.add("11");
        DataScriptFloby.myItems.add("12");
        DataScriptFloby.myItems.add("13");
        DataScriptFloby.myItems.add("17");
        DataScriptFloby.myItems.add("17");
        DataScriptFloby.myItems.add("17");

        DataScriptFloby.myDrawing.add("ax");
        DataScriptFloby.myDrawing.add("feather");
        DataScriptFloby.myDrawing.add("back");
        DataScriptFloby.myDrawing.add("torch");

        if (DataMusic.isMenuMusic == false){
            DataMusic.isMenuMusic = true;
            DataMusic.startMenuMusic();
        }

        ArrayList <ImageView> list = new ArrayList<>(List.of(background,snow, load));

        for (ImageView view : list){
            view.setFitHeight(720 * deltaMainScene);
            view.setFitWidth(1280 * deltaMainScene);
        }

        ArrayList <Button> buttons = new ArrayList<>(List.of(cont, newGame, exit));

        for (Button button : buttons){
            button.setLayoutX(button.getLayoutX() * deltaMainScene);
            button.setLayoutY(button.getLayoutY() * deltaMainScene);
            button.setPrefHeight(button.getPrefHeight() * deltaMainScene);
            button.setPrefWidth(button.getPrefWidth() * deltaMainScene);
            button.setOpacity(0);
        }

        try {
            background.setImage(new Image(new FileInputStream("src/main/img/menu/Menu.png")));
            load.setImage(new Image(new FileInputStream("src/main/img/loadScreen/loadScreen1.png")));
            load.setOpacity(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Duration duration = Duration.millis(200);
        new Timeline(new KeyFrame(duration, new KeyValue(load.opacityProperty(), 0))).play();

        DataGraphics.setBackGroundAnimation();

        snow.setImage(DataGraphics.slowSnow.getImage());

    }

    public void onCont(ActionEvent event) {
        DataScriptFloby.loadDataGame();
        DataScriptFloby.loadSavedScene((Stage) snow.getScene().getWindow());
        DataMusic.isMenuMusic = false;
        DataMusic.stopMenuMusic();
    }

    public void toNewGame(ActionEvent event) {
        DataMusic.isMenuMusic = false;
        DataMusic.stopMenuMusic();
        /*DataScriptFloby.saveDataGame();
        DataScriptFloby.loadSavedScene((Stage) snow.getScene().getWindow());*/
        loadGeneration();
    }

    public void toExited(ActionEvent event) {

        ((Stage) snow.getScene().getWindow()).close();
        DataMusic.isMenuMusic = false;
        DataMusic.stopMenuMusic();

    }

    void loadGeneration(){
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerMainGeneration.class.getResource("mainGeneration.fxml"));

        Stage stage = (Stage) snow.getScene().getWindow();
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);
        stage.setScene(scene);
        stage.show();
    }
}
