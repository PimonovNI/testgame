package com.example.demo.proceduralGeneration;

import com.example.demo.scripts.DataGraphics;
import com.example.demo.scripts.DataScriptFloby;
import com.example.demo.scripts.ScriptMain;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerMainGeneration extends ScriptMain {

    AnimationTimer animationTimerForPortal;

    ArrayList<Rectangle> allGround1 = new ArrayList<>();
    ArrayList <Rectangle> portal = new ArrayList<>();
    ArrayList <AnchorPane> blockForNPS1 = new ArrayList<>();
    ArrayList <AnchorPane> campfire = new ArrayList<>();
    ArrayList <AnchorPane> chest = new ArrayList<>();
    ArrayList <ImageView> graphicView = new ArrayList<>();
    ArrayList <AnchorPane> ladder = new ArrayList<>();

    AnchorPane pers;

    Rectangle indicatorRegionForCameraTopLeft, indicatorRegionForCameraBottomRight;
    ImageView persAnimation, imgIconForImageFreezingEffect;

    double mainDeltaScreen;

    double mainDeltaX = 0;
    int countBlock = (int) (Math.random() * 3 + 3);

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private ImageView backgroundImage, imageInfoBar;

    @FXML
    private ProgressBar experiences, stamina, health;

    @FXML
    void initialize() {

        mainDeltaScreen = DataScriptFloby.deltaScreenWindowGame;

        mainAnchorPane.setPrefHeight(720 * mainDeltaScreen);
        mainAnchorPane.setPrefWidth(1280 * mainDeltaScreen);
        backgroundImage.setFitWidth(1280 * mainDeltaScreen);
        backgroundImage.setFitHeight(720 * mainDeltaScreen);

        for (int i=0;i<countBlock;i++){
            mainDeltaX = 1000 * i * mainDeltaScreen;
            createBlock();
        }

        createPers();
        createRegion();

        createSnow();

        startScript(this.pers, this.allGround1, this.blockForNPS1, this.graphicView, this.portal, this.campfire, this.chest, this.ladder,
                this.indicatorRegionForCameraTopLeft, this.indicatorRegionForCameraBottomRight, this.health, this.stamina,
                this.experiences, this.persAnimation, this.imgIconForImageFreezingEffect, this.backgroundImage, this.imageInfoBar);
    }

    void createPers(){
        pers = new AnchorPane();
        pers.setPrefWidth(50);
        pers.setPrefHeight(50);
        pers.setLayoutY(570 * mainDeltaScreen);
        pers.setLayoutX((1000 * countBlock - 100) * mainDeltaScreen);
        pers.setId("pers");

        Label label = new Label();
        label.setId("labDirectionIndicator");
        label.setOpacity(0);
        label.setLayoutX(0);
        label.setLayoutY(0);

        persAnimation = new ImageView();
        persAnimation.setId("persAnimation");
        persAnimation.setLayoutX(0);
        persAnimation.setLayoutY(0);
        persAnimation.setFitWidth(50);
        persAnimation.setFitHeight(50);
        persAnimation.setPreserveRatio(true);

        imgIconForImageFreezingEffect = new ImageView();
        imgIconForImageFreezingEffect.setId("imgIconForImageFreezingEffect");
        imgIconForImageFreezingEffect.setLayoutX(0);
        imgIconForImageFreezingEffect.setLayoutY(0);
        imgIconForImageFreezingEffect.setFitWidth(10 * mainDeltaScreen);
        imgIconForImageFreezingEffect.setFitHeight(10 * mainDeltaScreen);
        try {
            imgIconForImageFreezingEffect.setImage(new Image(new FileInputStream("src/main/resources/image/iconForFreezindEfect.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pers.getChildren().addAll(label, persAnimation, imgIconForImageFreezingEffect);
        mainAnchorPane.getChildren().add(pers);
    }

    void createRegion(){
        indicatorRegionForCameraTopLeft = new Rectangle();
        indicatorRegionForCameraTopLeft.setOpacity(0);
        indicatorRegionForCameraTopLeft.setLayoutY(5 * mainDeltaScreen);
        indicatorRegionForCameraTopLeft.setLayoutX(5 * mainDeltaScreen);
        indicatorRegionForCameraTopLeft.setWidth(50  * mainDeltaScreen);
        indicatorRegionForCameraTopLeft.setHeight(50  * mainDeltaScreen);

        indicatorRegionForCameraBottomRight = new Rectangle();
        indicatorRegionForCameraBottomRight.setOpacity(0);
        indicatorRegionForCameraBottomRight.setLayoutY(665 * mainDeltaScreen);
        indicatorRegionForCameraBottomRight.setLayoutX((1000 * countBlock - 55) * mainDeltaScreen);
        indicatorRegionForCameraBottomRight.setWidth(50  * mainDeltaScreen);
        indicatorRegionForCameraBottomRight.setHeight(50  * mainDeltaScreen);

        Rectangle rightBorder = new Rectangle();
        rightBorder.setWidth(5 * mainDeltaScreen);
        rightBorder.setHeight(1000 * mainDeltaScreen);
        rightBorder.setLayoutY(-280 * mainDeltaScreen);
        rightBorder.setLayoutX((1000 * countBlock - 5) * mainDeltaScreen);
        rightBorder.setOpacity(0);

        Rectangle leftBorder = new Rectangle();
        leftBorder.setWidth(5 * mainDeltaScreen);
        leftBorder.setHeight(1000 * mainDeltaScreen);
        leftBorder.setLayoutY(-280 * mainDeltaScreen);
        leftBorder.setLayoutX(0);
        leftBorder.setOpacity(0);

        Rectangle mainPortal = new Rectangle();
        mainPortal.setLayoutY(420 * mainDeltaScreen);
        mainPortal.setLayoutX(10 * mainDeltaScreen);
        mainPortal.setWidth(50 * mainDeltaScreen);
        mainPortal.setHeight(200 * mainDeltaScreen);
        //mainPortal.setOpacity(0);

        correctFindAndCheckPortal(mainPortal);
        animationTimerForPortal.start();

        portal.add(mainPortal);
        allGround1.addAll(List.of(indicatorRegionForCameraTopLeft, indicatorRegionForCameraBottomRight, rightBorder, leftBorder));
        mainAnchorPane.getChildren().addAll(indicatorRegionForCameraBottomRight, indicatorRegionForCameraTopLeft, rightBorder, leftBorder, mainPortal);
    }

    void createSnow() {

        int localDeltaX = 0;
        Image image = DataGraphics.slowSnow.getImage();

        while (localDeltaX <= 1280 * countBlock){

            ImageView imageView = new ImageView(image);
            imageView.setLayoutY(0);
            imageView.setLayoutX(localDeltaX);
            imageView.setFitWidth(1280 * mainDeltaScreen);
            imageView.setFitHeight(720 * mainDeltaScreen);

            graphicView.add(imageView);

            mainAnchorPane.getChildren().add(imageView);
            localDeltaX += 1280 * mainDeltaScreen;

        }

    }

    void correctFindAndCheckPortal(Rectangle portalForUse){

        animationTimerForPortal = new AnimationTimer() {
            final Rectangle portal = portalForUse;
            @Override
            public void handle(long now) {

                if (pers.getBoundsInParent().intersects(portal.getBoundsInParent())){

                    endScript();
                    Platform.runLater(() -> {
                        Stage stage = (Stage) pers.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(ControllerMainGeneration.class.getResource("mainGeneration.fxml"));
                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = fxmlLoader.getRoot();
                        Scene scene = new Scene(root, 1280 * mainDeltaScreen, 720 * mainDeltaScreen);
                        stage.setScene(scene);
                        stage.show();
                    });
                    stop();

                }

            }
        };

    }

    AnchorPane createWolf(double layoutX, double layoutY){
        AnchorPane wolf = new AnchorPane();
        wolf.setLayoutY(layoutY * mainDeltaScreen);
        wolf.setLayoutX(layoutX * mainDeltaScreen + mainDeltaX);
        wolf.setPrefHeight(40);
        wolf.setPrefWidth(60);

        Label label1 = new Label("1");
        label1.setLayoutX(0);
        label1.setLayoutY(0);
        label1.setOpacity(0);

        Label label2 = new Label("-1");
        label2.setLayoutX(0);
        label2.setLayoutY(0);
        label2.setOpacity(0);

        ImageView imageView = new ImageView();
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitHeight(40);
        imageView.setFitWidth(60);

        wolf.getChildren().addAll(label1, label2, imageView);

        return wolf;
    }

    AnchorPane createBird(double layoutX, double layoutY){
        AnchorPane bird = new AnchorPane();
        bird.setLayoutY(layoutY * mainDeltaScreen);
        bird.setLayoutX(layoutX * mainDeltaScreen + mainDeltaX);
        bird.setPrefHeight(50);
        bird.setPrefWidth(50);

        Label label1 = new Label("3");
        label1.setLayoutX(0);
        label1.setLayoutY(0);
        label1.setOpacity(0);

        Label label2 = new Label("-1");
        label2.setLayoutX(0);
        label2.setLayoutY(0);
        label2.setOpacity(0);

        ImageView imageView = new ImageView();
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        bird.getChildren().addAll(label1, label2, imageView);

        return bird;
    }

    AnchorPane createBoar(double layoutX, double layoutY){
        AnchorPane boar = new AnchorPane();
        boar.setLayoutY(layoutY * mainDeltaScreen);
        boar.setLayoutX(layoutX * mainDeltaScreen + mainDeltaX);
        boar.setPrefHeight(40);
        boar.setPrefWidth(60);

        Label label1 = new Label("4");
        label1.setLayoutX(0);
        label1.setLayoutY(0);
        label1.setOpacity(0);

        Label label2 = new Label("-1");
        label2.setLayoutX(0);
        label2.setLayoutY(0);
        label2.setOpacity(0);

        ImageView imageView = new ImageView();
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitHeight(20);
        imageView.setFitWidth(60);

        boar.getChildren().addAll(label1, label2, imageView);

        return boar;
    }

    void createBlock(){

        switch ((int)(Math.random() * 5) + 1){
            case(1) -> createBlock1();
            case(2) -> createBlock2();
            case(3) -> createBlock3();
            case(4) -> createBlock4();
            case(5) -> createBlock5();
        }

    }

    void createBlock1 (){

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutY(620 * mainDeltaScreen);
        rectangle1.setLayoutX(mainDeltaX);
        rectangle1.setHeight(100 * mainDeltaScreen);
        rectangle1.setWidth(1000 * mainDeltaScreen);
        rectangle1.setOpacity(0);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setLayoutY(420 * mainDeltaScreen);
        rectangle2.setLayoutX(300 * mainDeltaScreen + mainDeltaX);
        rectangle2.setHeight(200 * mainDeltaScreen);
        rectangle2.setWidth(100 * mainDeltaScreen);
        rectangle2.setOpacity(0);

        Rectangle rectangle3 = new Rectangle();
        rectangle3.setLayoutY(520 * mainDeltaScreen);
        rectangle3.setLayoutX(400 * mainDeltaScreen + mainDeltaX);
        rectangle3.setHeight(100 * mainDeltaScreen);
        rectangle3.setWidth(100 * mainDeltaScreen);
        rectangle3.setOpacity(0);

        Rectangle rectangle4 = new Rectangle();
        rectangle4.setLayoutY(384 * mainDeltaScreen);
        rectangle4.setLayoutX(500 * mainDeltaScreen + mainDeltaX);
        rectangle4.setHeight(36 * mainDeltaScreen);
        rectangle4.setWidth(200 * mainDeltaScreen);
        rectangle4.setOpacity(0);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("src/main/img/generation/block1.png")));
            imageView.setFitHeight(720 * mainDeltaScreen);
            imageView.setFitWidth(1000 * mainDeltaScreen);
            imageView.setLayoutX(mainDeltaX);
            imageView.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AnchorPane enemy1 = createWolf(640, 344);
        AnchorPane enemy2 = createBird(150, 370);

        allGround1.addAll(List.of(rectangle1, rectangle2, rectangle3, rectangle4));
        graphicView.add(imageView);
        blockForNPS1.addAll(List.of(enemy1, enemy2));
        mainAnchorPane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, enemy1, enemy2, imageView);

    }

    void createBlock2 (){

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutY(620 * mainDeltaScreen);
        rectangle1.setLayoutX(mainDeltaX);
        rectangle1.setHeight(100 * mainDeltaScreen);
        rectangle1.setWidth(1000 * mainDeltaScreen);
        rectangle1.setOpacity(0);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setLayoutY(320 * mainDeltaScreen);
        rectangle2.setLayoutX(500 * mainDeltaScreen + mainDeltaX);
        rectangle2.setHeight(36 * mainDeltaScreen);
        rectangle2.setWidth(300 * mainDeltaScreen);
        rectangle2.setOpacity(0);

        Rectangle rectangle3 = new Rectangle();
        rectangle3.setLayoutY(420 * mainDeltaScreen);
        rectangle3.setLayoutX(200 * mainDeltaScreen + mainDeltaX);
        rectangle3.setHeight(36 * mainDeltaScreen);
        rectangle3.setWidth(300 * mainDeltaScreen);
        rectangle3.setOpacity(0);

        Rectangle rectangle4 = new Rectangle();
        rectangle4.setLayoutY(520 * mainDeltaScreen);
        rectangle4.setLayoutX(500 * mainDeltaScreen + mainDeltaX);
        rectangle4.setHeight(36 * mainDeltaScreen);
        rectangle4.setWidth(300 * mainDeltaScreen);
        rectangle4.setOpacity(0);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("src/main/img/generation/block2.png")));
            imageView.setFitHeight(720 * mainDeltaScreen);
            imageView.setFitWidth(1000 * mainDeltaScreen);
            imageView.setLayoutX(mainDeltaX);
            imageView.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AnchorPane enemy1 = createWolf(250, 380);
        AnchorPane enemy2 = createBird(570, 220);
        AnchorPane enemy3 = createBoar(300,580);

        allGround1.addAll(List.of(rectangle1, rectangle2, rectangle3, rectangle4));
        graphicView.add(imageView);
        blockForNPS1.addAll(List.of(enemy1, enemy2, enemy3));
        mainAnchorPane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, enemy1, enemy2, enemy3, imageView);

    }

    void createBlock3 (){

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutY(620 * mainDeltaScreen);
        rectangle1.setLayoutX(mainDeltaX);
        rectangle1.setHeight(100 * mainDeltaScreen);
        rectangle1.setWidth(1000 * mainDeltaScreen);
        rectangle1.setOpacity(0);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setLayoutY(320 * mainDeltaScreen);
        rectangle2.setLayoutX(200 * mainDeltaScreen + mainDeltaX);
        rectangle2.setHeight(300 * mainDeltaScreen);
        rectangle2.setWidth(100 * mainDeltaScreen);
        rectangle2.setOpacity(0);

        Rectangle rectangle3 = new Rectangle();
        rectangle3.setLayoutY(420 * mainDeltaScreen);
        rectangle3.setLayoutX(400 * mainDeltaScreen + mainDeltaX);
        rectangle3.setHeight(36 * mainDeltaScreen);
        rectangle3.setWidth(200 * mainDeltaScreen);
        rectangle3.setOpacity(0);

        Rectangle rectangle4 = new Rectangle();
        rectangle4.setLayoutY(520 * mainDeltaScreen);
        rectangle4.setLayoutX(700 * mainDeltaScreen + mainDeltaX);
        rectangle4.setHeight(100 * mainDeltaScreen);
        rectangle4.setWidth(100 * mainDeltaScreen);
        rectangle4.setOpacity(0);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("src/main/img/generation/block3.png")));
            imageView.setFitHeight(720 * mainDeltaScreen);
            imageView.setFitWidth(1000 * mainDeltaScreen);
            imageView.setLayoutX(mainDeltaX);
            imageView.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AnchorPane enemy1 = createBoar(500, 580);
        AnchorPane enemy2 = createBird(770, 350);
        AnchorPane enemy3 = createBird(320,210);

        allGround1.addAll(List.of(rectangle1, rectangle2, rectangle3, rectangle4));
        graphicView.add(imageView);
        blockForNPS1.addAll(List.of(enemy1, enemy2, enemy3));
        mainAnchorPane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, enemy1, enemy2, enemy3, imageView);

    }

    void createBlock4 (){

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutY(620 * mainDeltaScreen);
        rectangle1.setLayoutX(mainDeltaX);
        rectangle1.setHeight(100 * mainDeltaScreen);
        rectangle1.setWidth(1000 * mainDeltaScreen);
        rectangle1.setOpacity(0);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setLayoutY(340 * mainDeltaScreen);
        rectangle2.setLayoutX(100 * mainDeltaScreen + mainDeltaX);
        rectangle2.setHeight(36 * mainDeltaScreen);
        rectangle2.setWidth(50 * mainDeltaScreen);
        rectangle2.setOpacity(0);

        Rectangle rectangle3 = new Rectangle();
        rectangle3.setLayoutY(120 * mainDeltaScreen);
        rectangle3.setLayoutX(200 * mainDeltaScreen + mainDeltaX);
        rectangle3.setHeight(500 * mainDeltaScreen);
        rectangle3.setWidth(100 * mainDeltaScreen);
        rectangle3.setOpacity(0);

        Rectangle rectangle4 = new Rectangle();
        rectangle4.setLayoutY(120 * mainDeltaScreen);
        rectangle4.setLayoutX(450 * mainDeltaScreen + mainDeltaX);
        rectangle4.setHeight(36 * mainDeltaScreen);
        rectangle4.setWidth(300 * mainDeltaScreen);
        rectangle4.setOpacity(0);

        Rectangle rectangle5 = new Rectangle();
        rectangle5.setLayoutY(220 * mainDeltaScreen);
        rectangle5.setLayoutX(700 * mainDeltaScreen + mainDeltaX);
        rectangle5.setHeight(36 * mainDeltaScreen);
        rectangle5.setWidth(100 * mainDeltaScreen);
        rectangle5.setOpacity(0);

        Rectangle rectangle6 = new Rectangle();
        rectangle6.setLayoutY(320 * mainDeltaScreen);
        rectangle6.setLayoutX(400 * mainDeltaScreen + mainDeltaX);
        rectangle6.setHeight(36 * mainDeltaScreen);
        rectangle6.setWidth(350 * mainDeltaScreen);
        rectangle6.setOpacity(0);

        Rectangle rectangle7 = new Rectangle();
        rectangle7.setLayoutY(420 * mainDeltaScreen);
        rectangle7.setLayoutX(300 * mainDeltaScreen + mainDeltaX);
        rectangle7.setHeight(36 * mainDeltaScreen);
        rectangle7.setWidth(400 * mainDeltaScreen);
        rectangle7.setOpacity(0);

        Rectangle rectangle8 = new Rectangle();
        rectangle8.setLayoutY(520 * mainDeltaScreen);
        rectangle8.setLayoutX(400 * mainDeltaScreen + mainDeltaX);
        rectangle8.setHeight(36 * mainDeltaScreen);
        rectangle8.setWidth(400 * mainDeltaScreen);
        rectangle8.setOpacity(0);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("src/main/img/generation/block4.png")));
            imageView.setFitHeight(720 * mainDeltaScreen);
            imageView.setFitWidth(1000 * mainDeltaScreen);
            imageView.setLayoutX(mainDeltaX);
            imageView.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AnchorPane enemy1 = createBird(100, 270);
        AnchorPane enemy2 = createBird(750, 150);
        AnchorPane enemy3 = createWolf(350,380);
        AnchorPane enemy4 = createWolf(500, 80);
        AnchorPane enemy5 = createBoar(400,580);

        allGround1.addAll(List.of(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, rectangle7, rectangle8));
        graphicView.add(imageView);
        blockForNPS1.addAll(List.of(enemy1, enemy2, enemy3, enemy4, enemy5));
        mainAnchorPane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, rectangle7, rectangle8,
                enemy1, enemy2, enemy3, enemy4, enemy5, imageView);

    }

    void createBlock5 (){

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutY(620 * mainDeltaScreen);
        rectangle1.setLayoutX(mainDeltaX);
        rectangle1.setHeight(100 * mainDeltaScreen);
        rectangle1.setWidth(1000 * mainDeltaScreen);
        rectangle1.setOpacity(0);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("src/main/img/generation/block5.png")));
            imageView.setFitHeight(720 * mainDeltaScreen);
            imageView.setFitWidth(1000 * mainDeltaScreen);
            imageView.setLayoutX(mainDeltaX);
            imageView.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AnchorPane enemy1 = createBoar(300, 580);
        AnchorPane enemy2 = createBird(770, 400);
        AnchorPane enemy3 = createBird(320,420);

        allGround1.addAll(List.of(rectangle1));
        graphicView.add(imageView);
        blockForNPS1.addAll(List.of(enemy1, enemy2, enemy3));
        mainAnchorPane.getChildren().addAll(rectangle1, enemy1, enemy2, enemy3, imageView);

    }

}