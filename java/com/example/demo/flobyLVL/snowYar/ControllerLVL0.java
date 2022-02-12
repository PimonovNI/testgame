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

public class ControllerLVL0 extends ScriptMain implements Initializable {

    public static int pastLocation = 0;

    ArrayList <Rectangle> allGround1 = new ArrayList<>();
    ArrayList <Rectangle> portal = new ArrayList<>();
    ArrayList <AnchorPane> blockForNPS1 = new ArrayList<>();
    ArrayList <AnchorPane> campfire = new ArrayList<>();
    ArrayList <AnchorPane> chest = new ArrayList<>();
    ArrayList <ImageView> graphicView = new ArrayList<>();
    ArrayList <AnchorPane> ladder = new ArrayList<>();

    private MediaPlayer mediaPlayer;

    AnimationTimer animationTimer;

    double deltaMain = DataScriptFloby.deltaScreenWindowGame;

    @FXML
    private Rectangle ground1,ground2,ground3,ground4,ground5, indicatorRegionForCameraTopLeft,
                    indicatorRegionForCameraBottomRight, portal1;

    @FXML
    private AnchorPane pers, mainAnc;

    @FXML
    private ProgressBar health, stamina, experiences;

    @FXML
    private Label lab, labDirectionIndicator;

    @FXML
    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, i1, i2, imgDec1, imgDec2, crow1, crow2, loadScreen,
            imageInfoBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pers.setLayoutX(DataScriptFloby.layoutStartXPers);
        pers.setLayoutY(DataScriptFloby.layoutStartYPers);

        allGround1.addAll(List.of(ground1, ground2, ground3, ground4, ground5,indicatorRegionForCameraTopLeft,
                indicatorRegionForCameraBottomRight));
        graphicView.addAll(List.of(i1, i2, imgDec1, imgDec2, crow1, crow2));
        portal.add(portal1);

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

        for (Rectangle rectangle : allGround1){
            rectangle.setOpacity(0);
        }


        mainAnc.setLayoutX(mainAnc.getLayoutX() * deltaMain);
        mainAnc.setLayoutY(mainAnc.getLayoutY() * deltaMain);

        mainAnc.setPrefWidth(mainAnc.getPrefWidth() * deltaMain);
        mainAnc.setPrefHeight(mainAnc.getPrefHeight() * deltaMain);

        loadScreen.setFitWidth(loadScreen.getFitWidth() * deltaMain);
        loadScreen.setFitHeight(loadScreen.getFitHeight() * deltaMain);

        try {
            loadScreen.setImage(new Image(new FileInputStream("src/main/img/loadScreen/loadScreen1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*Установка декораций*/
        imgDec1.setImage(DataGraphics.slowSnow.getImage());
        imgDec2.setImage(DataGraphics.slowSnow.getImage());

        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/animation/decoration/crow/start.png")));
            crow1.setImage(imageView.getImage());
            crow1.setScaleX(1);
            crow2.setImage(imageView.getImage());
            crow2.setScaleX(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (pastLocation == 1){
            pers.setLayoutY(570);
        }

        startScript(this.pers, this.allGround1, this.blockForNPS1, this.graphicView, this.portal, this.campfire, this.chest, this.ladder,
                this.indicatorRegionForCameraTopLeft, this.indicatorRegionForCameraBottomRight, this.health, this.stamina,
                this.experiences, this.persAnimation, this.imgIconForImageFreezingEffect, this.backgroundImage, this.imageInfoBar);


        if (pastLocation == 1){
            ScriptMain.stopAnimation = true;
            pers.setLayoutX(26);
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

            ImageView imageView = null;
            {
                try {
                    imageView = new ImageView(new Image(new FileInputStream("src/main/animation/decoration/crow/fly.gif")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            boolean b1 = false, block1 = false;
            boolean b2 = false, block2 = false;


            @Override
            public void handle(long l) {

                /*Активация ворон*/
                if (!block1) {
                    if (!b1) {
                        double distance = Math.sqrt(Math.pow((crow1.getLayoutX() + crow1.getFitWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2), 2) +
                                Math.pow((crow1.getLayoutY() + crow1.getFitHeight() / 2 - pers.getLayoutY() - pers.getHeight() / 2), 2));
                        if (distance <= 400 * deltaMain) {
                            b1 = true;
                            crow1.setImage(imageView.getImage());
                        }
                    } else {
                        crow1.setLayoutX(crow1.getLayoutX() + 3 * deltaMain);
                        crow1.setLayoutY(crow1.getLayoutY() - 4 * deltaMain);

                        if (crow1.getLayoutY() + crow1.getFitHeight() < 0) block1 = true;

                    }
                }

                if (!block2) {
                    if (!b2) {
                        double distance = Math.sqrt(Math.pow((crow2.getLayoutX() + crow2.getFitWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2), 2) +
                                Math.pow((crow2.getLayoutY() + crow2.getFitHeight() / 2 - pers.getLayoutY() - pers.getHeight() / 2), 2));
                        if (distance <= 400 * deltaMain) {
                            b2 = true;
                            crow2.setImage(imageView.getImage());
                        }
                    } else {
                        crow2.setLayoutX(crow2.getLayoutX() - 3 * deltaMain);
                        crow2.setLayoutY(crow2.getLayoutY() - 4 * deltaMain);

                        if (crow2.getLayoutY() + crow2.getFitHeight() < 0) block2 = true;
                    }
                }

                /*Активация порталов*/
                if (portal1.getBoundsInParent().intersects(pers.getBoundsInParent())){
                    Platform.runLater(() -> {

                        DataScriptFloby.layoutStartXPers = 1920;
                        DataScriptFloby.layoutStartYPers = 570;
                        DataScriptFloby.thisLocation = 1;
                        ControllerLVL1.pastLocation = 0;

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

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lvl1.fxml"));

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
