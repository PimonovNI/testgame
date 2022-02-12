package com.example.demo.flobyLVL.snowYar;

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
import java.util.ResourceBundle;

public class ControllerLVL6 extends ScriptMain implements Initializable {

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
    private Rectangle ground1,ground2,ground3,ground4,ground5,ground6,ground7,ground8, ground9 ,indicatorRegionForCameraTopLeft,
            indicatorRegionForCameraBottomRight, portal1;

    @FXML
    private AnchorPane pers, mainAnc;

    @FXML
    private ProgressBar health, stamina, experiences;

    @FXML
    private Label lab, labDirectionIndicator;

    @FXML
    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, i1, i2, imageInfoBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pers.setLayoutX(DataScriptFloby.layoutStartXPers);
        pers.setLayoutY(DataScriptFloby.layoutStartYPers);

        allGround1.add(ground1);
        allGround1.add(ground2);
        allGround1.add(ground3);
        allGround1.add(ground4);
        allGround1.add(ground5);
        allGround1.add(ground6);
        allGround1.add(ground7);
        allGround1.add(ground8);
        allGround1.add(ground9);
        allGround1.add(indicatorRegionForCameraTopLeft);
        allGround1.add(indicatorRegionForCameraBottomRight);
        portal.add(portal1);

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

        for (Rectangle rectangle : allGround1){
           rectangle.setOpacity(0);
        }

        graphicView.add(i1);
        graphicView.add(i2);

        mainAnc.setLayoutX(mainAnc.getLayoutX() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setLayoutY(mainAnc.getLayoutY() * DataScriptFloby.deltaScreenWindowGame);

        mainAnc.setPrefWidth(mainAnc.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        mainAnc.setPrefHeight(mainAnc.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);

        labDirectionIndicator.setTextFill(Color.rgb(0,0,0,0));

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

            }
        };
        animationTimer.start();
    }

    void stopTimer(){
        animationTimer.stop();
    }
}
