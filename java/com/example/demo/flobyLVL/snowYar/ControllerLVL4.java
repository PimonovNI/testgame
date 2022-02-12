package com.example.demo.flobyLVL.snowYar;

import com.example.demo.scripts.DataGraphics;
import com.example.demo.scripts.DataScriptFloby;
import com.example.demo.scripts.ScriptMain;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerLVL4 extends ScriptMain implements Initializable {

    AnimationTimer animationTimer;

    ArrayList <Rectangle> allGround1 = new ArrayList<>();
    ArrayList <Rectangle> portal = new ArrayList<>();
    ArrayList <AnchorPane> blockForNPS1 = new ArrayList<>();
    ArrayList <AnchorPane> campfire = new ArrayList<>();
    ArrayList <AnchorPane> chest = new ArrayList<>();
    ArrayList <ImageView> graphicView = new ArrayList<>();
    ArrayList <AnchorPane> ladder = new ArrayList<>();

    private MediaPlayer mediaPlayer;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle ground1,ground2,ground3,ground4,ground5,ground6,indicatorRegionForCameraTopLeft,
            indicatorRegionForCameraBottomRight, portal1, portal2;

    @FXML
    private AnchorPane pers, simpleNPS1, simpleNPS2, simpleNPS3, mainAnc;

    @FXML
    private ProgressBar health, stamina, experiences;

    @FXML
    private Label lab, labDirectionIndicator;

    @FXML
    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, i1, i2, imageInfoBar, imgDec1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pers.setLayoutX(DataScriptFloby.layoutStartXPers);
        pers.setLayoutY(DataScriptFloby.layoutStartYPers);

        allGround1.addAll(List.of(ground1,ground2,ground3,ground4,ground5,ground6,indicatorRegionForCameraTopLeft,
                indicatorRegionForCameraBottomRight));
        portal.addAll(List.of(portal1,portal2));
        blockForNPS1.addAll(List.of(simpleNPS1, simpleNPS2, simpleNPS3));
        graphicView.addAll(List.of(i1, i2, imgDec1));

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

        for (Rectangle rectangle : allGround1){
            rectangle.setOpacity(0);
        }

        mainAnc.setLayoutX(mainAnc.getLayoutX() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setLayoutY(mainAnc.getLayoutY() * DataScriptFloby.deltaScreenWindowGame);

        mainAnc.setPrefWidth(mainAnc.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setPrefHeight(mainAnc.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);

        imgDec1.setImage(DataGraphics.slowSnow.getImage());

        startScript(this.pers, this.allGround1, this.blockForNPS1, this.graphicView, this.portal, this.campfire, this.chest, this.ladder,
                this.indicatorRegionForCameraTopLeft,
                this.indicatorRegionForCameraBottomRight, this.health, this.stamina, this.experiences, this.persAnimation,
                this.imgIconForImageFreezingEffect, this.backgroundImage,this.imageInfoBar);

        findPortal();
    }

    void findPortal(){

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (portal1.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 157;
                        DataScriptFloby.layoutStartYPers = 629;
                        DataScriptFloby.thisLocation = 5;

                        endScript();

                        Stage stage = (Stage) pers.getScene().getWindow();

                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL5.class.getResource("lvl5.fxml"));

                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Parent root = fxmlLoader.getRoot();

                        Scene scene = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);

                        stage.setScene(scene);
                        stage.show();

                        stopTimer();
                    });
                }
                else if (portal2.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 1781;
                        DataScriptFloby.layoutStartYPers = 648;
                        DataScriptFloby.thisLocation = 3;

                        endScript();

                        Stage stage = (Stage) pers.getScene().getWindow();

                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL0.class.getResource("lvl3.fxml"));

                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Parent root = fxmlLoader.getRoot();

                        Scene scene = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);

                        stage.setScene(scene);
                        stage.show();

                        stopTimer();
                    });
                }
            }
        };
        animationTimer.start();
    }

    void stopTimer(){
        animationTimer.stop();
    }
}
