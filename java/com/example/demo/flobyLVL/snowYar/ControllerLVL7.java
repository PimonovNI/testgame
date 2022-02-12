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

public class ControllerLVL7 extends ScriptMain implements Initializable {

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
    private Rectangle ground1,ground2,ground3,ground4,ground5,ground6,ground7,ground8, ground9,ground10, ground11, ground12,
            ground13, ground14, ground15, ground16, ground17, ground18, ground19, indicatorRegionForCameraTopLeft,
            indicatorRegionForCameraBottomRight, indicatorRegionForCameraTopLeft1, indicatorRegionForCameraBottomRight1, portal1, portal2;

    @FXML
    private AnchorPane pers, mainAnc, ladder1, ladder2, simpleNPS1, campfire1;

    @FXML
    private ProgressBar health, stamina, experiences;

    @FXML
    private Label lab, labDirectionIndicator;

    @FXML
    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, i1, i2, imageInfoBar, imgDec1, imgDec2, imgDec3, imgDec4,
            imgDec5, imgDec6, imgDec7, imgDec8, imgDec9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pers.setLayoutX(DataScriptFloby.layoutStartXPers);
        pers.setLayoutY(DataScriptFloby.layoutStartYPers);

        allGround1.addAll(List.of(ground1, ground2, ground3,ground4, ground5,ground6, ground7,ground8,ground9,ground10,
                ground11, ground12, ground13, ground14, ground15,ground16, ground17,ground18, ground19,
                indicatorRegionForCameraTopLeft,indicatorRegionForCameraBottomRight, indicatorRegionForCameraTopLeft1,
                indicatorRegionForCameraBottomRight1));
        portal.addAll(List.of(portal1, portal2));
        ladder.addAll(List.of(ladder1, ladder2));
        blockForNPS1.add(simpleNPS1);
        campfire.add(campfire1);

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

        for (Rectangle rectangle : allGround1){
           rectangle.setOpacity(0);
        }

        graphicView.addAll(List.of(i1, i2, imgDec1, imgDec2, imgDec3, imgDec4,imgDec5, imgDec6, imgDec7, imgDec8, imgDec9));

        mainAnc.setLayoutX(mainAnc.getLayoutX() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setLayoutY(mainAnc.getLayoutY() * DataScriptFloby.deltaScreenWindowGame);

        mainAnc.setPrefWidth(mainAnc.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setPrefHeight(mainAnc.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);

        imgDec1.setImage(DataGraphics.slowSnow.getImage());
        imgDec2.setImage(DataGraphics.slowSnow.getImage());
        imgDec3.setImage(DataGraphics.slowSnow.getImage());
        imgDec4.setImage(DataGraphics.slowSnow.getImage());
        imgDec5.setImage(DataGraphics.slowSnow.getImage());
        imgDec6.setImage(DataGraphics.slowSnow.getImage());
        imgDec7.setImage(DataGraphics.slowSnow.getImage());
        imgDec8.setImage(DataGraphics.slowSnow.getImage());
        imgDec9.setImage(DataGraphics.slowSnow.getImage());

        startScript(this.pers, this.allGround1, this.blockForNPS1, this.graphicView, this.portal, this.campfire, this.chest, this.ladder,
                this.indicatorRegionForCameraTopLeft,
                this.indicatorRegionForCameraBottomRight,this.indicatorRegionForCameraTopLeft1, this.indicatorRegionForCameraBottomRight1,
                this.health, this.stamina, this.experiences, this.persAnimation,
                this.imgIconForImageFreezingEffect, this.backgroundImage, this.imageInfoBar);

        findPortal();
    }

    void findPortal(){

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                if (campfire1.getBoundsInParent().intersects(pers.getBoundsInParent()) && DataScriptFloby.savePoint!=3){
                    DataScriptFloby.savePoint = 3;
                    DataScriptFloby.saveDataGame();
                }

                if (portal1.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 166;
                        DataScriptFloby.layoutStartYPers = 845;
                        DataScriptFloby.thisLocation = 2;

                        endScript();

                        Stage stage = (Stage) pers.getScene().getWindow();

                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL1.class.getResource("lvl2.fxml"));

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

                        DataScriptFloby.layoutStartXPers = 1590;
                        DataScriptFloby.layoutStartYPers = 600;
                        DataScriptFloby.thisLocation = 5;

                        endScript();

                        Stage stage = (Stage) pers.getScene().getWindow();

                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL1.class.getResource("lvl5.fxml"));

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
