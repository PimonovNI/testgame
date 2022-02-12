package com.example.demo.flobyLVL.snowYar;

import com.example.demo.scripts.DataGraphics;
import com.example.demo.scripts.DataScriptFloby;
import com.example.demo.scripts.ScriptMain;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerLVL1 extends ScriptMain implements Initializable {

    public static int pastLocation = 1;
    double deltaMain = DataScriptFloby.deltaScreenWindowGame;

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
    private Rectangle ground1,ground2,ground3,ground4,ground5,ground6,ground7,ground8, indicatorRegionForCameraTopLeft,
            indicatorRegionForCameraBottomRight, portal1, portal2, portal3;

    @FXML
    private AnchorPane pers, simpleNPS1, simpleNPS2, simpleNPS3, chest1, mainAnc;

    @FXML
    private ProgressBar health, stamina, experiences;

    @FXML
    private Label lab, labDirectionIndicator;

    @FXML
    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, i1, i2, imageInfoBar, imgDec1, imgDec2, loadScreen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataScriptFloby.saveDataGame();

        pers.setLayoutX(DataScriptFloby.layoutStartXPers);
        pers.setLayoutY(DataScriptFloby.layoutStartYPers);

        allGround1.addAll(List.of(ground1, ground2, ground3,ground4, ground5, ground6,ground7,ground8,indicatorRegionForCameraTopLeft,
                indicatorRegionForCameraBottomRight));
        portal.addAll(List.of(portal1, portal2,portal3));
        chest.add(chest1);

        blockForNPS1.addAll(List.of(simpleNPS1, simpleNPS2, simpleNPS3));

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

        for (Rectangle rectangle : allGround1){
            rectangle.setOpacity(0);
        }

        graphicView.addAll(List.of(i1, i2, imgDec1, imgDec2));

        mainAnc.setLayoutX(mainAnc.getLayoutX() * deltaMain);
        mainAnc.setLayoutY(mainAnc.getLayoutY() * deltaMain);

        mainAnc.setPrefWidth(mainAnc.getPrefWidth() * deltaMain);
        mainAnc.setPrefHeight(mainAnc.getPrefHeight() * deltaMain);

        imgDec1.setImage(DataGraphics.slowSnow.getImage());
        imgDec2.setImage(DataGraphics.slowSnow.getImage());

        loadScreen.setFitWidth(loadScreen.getFitWidth() * deltaMain);
        loadScreen.setFitHeight(loadScreen.getFitHeight() * deltaMain);

        try {
            loadScreen.setImage(new Image(new FileInputStream("src/main/img/loadScreen/loadScreen1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (pastLocation == 0 || pastLocation == 2){
            pers.setLayoutY(570);
        }

        startScript(this.pers, this.allGround1, this.blockForNPS1, this.graphicView, this.portal, this.campfire, this.chest, this.ladder,
                this.indicatorRegionForCameraTopLeft,
                this.indicatorRegionForCameraBottomRight, this.health, this.stamina, this.experiences, this.persAnimation,
                this.imgIconForImageFreezingEffect, this.backgroundImage, this.imageInfoBar);

        if (pastLocation == 0){
            ScriptMain.stopAnimation = true;
            pers.setLayoutX(1150 * deltaMain);
            loadScreen.setOpacity(1);

            try {
                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            persAnimation.setScaleX(-1);
            persAnimation.setLayoutX(0);
            persAnimation.setLayoutY(0);
            persAnimation.setFitWidth(50 * deltaMain);
            persAnimation.setFitHeight(50 * deltaMain);

            Timeline timelineMove = new Timeline(new KeyFrame(Duration.millis(200)));

            Timeline timelineOpacity = new Timeline(new KeyFrame(Duration.millis(300),
                    new KeyValue(loadScreen.opacityProperty(),0),
                    new KeyValue(pers.layoutXProperty(), 1050 * deltaMain)));

            Timeline timelineWait = new Timeline(new KeyFrame(Duration.millis(100)));

            timelineWait.setOnFinished(event -> timelineOpacity.play());

            timelineOpacity.setOnFinished(event -> timelineMove.play());

            timelineMove.setOnFinished(event -> {

                findPortal();
                ScriptMain.stopAnimation = false;

            });

            timelineWait.play();
        }
        else if (pastLocation == 2){
            ScriptMain.stopAnimation = true;
            pers.setLayoutX(30 * deltaMain);
            loadScreen.setOpacity(1);

            try {
                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            persAnimation.setScaleX(1);
            persAnimation.setLayoutX(0);
            persAnimation.setLayoutY(0);
            persAnimation.setFitWidth(50 * deltaMain);
            persAnimation.setFitHeight(50 * deltaMain);

            Timeline timelineMove = new Timeline(new KeyFrame(Duration.millis(200)));

            Timeline timelineOpacity = new Timeline(new KeyFrame(Duration.millis(300),
                    new KeyValue(loadScreen.opacityProperty(),0),
                    new KeyValue(pers.layoutXProperty(), 100 * deltaMain)));

            Timeline timelineWait = new Timeline(new KeyFrame(Duration.millis(100)));

            timelineWait.setOnFinished(event -> timelineOpacity.play());

            timelineOpacity.setOnFinished(event -> timelineMove.play());

            timelineMove.setOnFinished(event -> {

                findPortal();
                ScriptMain.stopAnimation = false;

            });

            timelineWait.play();
        }
        else{
            loadScreen.setOpacity(0);
            findPortal();
        }

    }

    void findPortal(){

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                if (portal1.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 130;
                        DataScriptFloby.layoutStartYPers = 570;
                        DataScriptFloby.thisLocation = 0;
                        ControllerLVL0.pastLocation = 1;

                        endScript();

                        try {
                            persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        persAnimation.setScaleX(1);
                        persAnimation.setLayoutX(0);
                        persAnimation.setLayoutY(0);
                        persAnimation.setFitWidth(50 * deltaMain);
                        persAnimation.setFitHeight(50 * deltaMain);


                        Stage stage = (Stage) pers.getScene().getWindow();

                        Timeline timelineMove = new Timeline(new KeyFrame(Duration.millis((indicatorRegionForCameraBottomRight.getLayoutX() - pers.getLayoutX())/DataScriptFloby.defaultSpeed * 25 * deltaMain),
                                new KeyValue(pers.layoutXProperty(), indicatorRegionForCameraBottomRight.getLayoutX() - 20)));

                        Timeline timelineOpacity = new Timeline(new KeyFrame(Duration.millis(300),
                                new KeyValue(loadScreen.opacityProperty(),1)));

                        Timeline timelineWait = new Timeline(new KeyFrame(Duration.millis(100)));

                        timelineMove.setOnFinished(event -> timelineOpacity.play());

                        timelineWait.setOnFinished(event -> {

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lvl0.fxml"));

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

                        timelineOpacity.setOnFinished(event -> timelineWait.play());

                        timelineMove.play();

                        stopTimer();
                    });
                }
                else if (portal2.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 876;
                        DataScriptFloby.layoutStartYPers = 505;
                        DataScriptFloby.thisLocation = 5;

                        endScript();

                        Stage stage = (Stage) pers.getScene().getWindow();

                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL0.class.getResource("lvl5.fxml"));

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
                else if (portal3.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 1768;
                        DataScriptFloby.layoutStartYPers = 854;
                        DataScriptFloby.thisLocation = 2;
                        ControllerLVL2.pastLocation = 1;

                        endScript();

                        try {
                            persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        persAnimation.setScaleX(-1);
                        persAnimation.setLayoutX(0);
                        persAnimation.setLayoutY(0);
                        persAnimation.setFitWidth(50 * deltaMain);
                        persAnimation.setFitHeight(50 * deltaMain);


                        Stage stage = (Stage) pers.getScene().getWindow();

                        Timeline timelineMove = new Timeline(new KeyFrame(Duration.millis((pers.getLayoutX() + pers.getWidth())/DataScriptFloby.defaultSpeed * 25 * deltaMain),
                                new KeyValue(pers.layoutXProperty(), -pers.getWidth())));

                        Timeline timelineOpacity = new Timeline(new KeyFrame(Duration.millis(300),
                                new KeyValue(loadScreen.opacityProperty(),1)));

                        Timeline timelineWait = new Timeline(new KeyFrame(Duration.millis(100)));

                        timelineMove.setOnFinished(event -> timelineOpacity.play());

                        timelineWait.setOnFinished(event -> {

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lvl2.fxml"));

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

                        timelineOpacity.setOnFinished(event -> timelineWait.play());

                        timelineMove.play();

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
