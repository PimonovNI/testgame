package com.example.demo.scripts;

import com.example.demo.Main;
import com.example.demo.interfaceForFloby.ControllerCraft;
import com.example.demo.interfaceForFloby.ControllerInvertar;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScriptMain {

    public static boolean mustCorrect = false;
    public static boolean stopAnimation = false;
    public static boolean loadScene = false;

    ArrayList<KeyCode> pressedBut = new ArrayList<>();
    ArrayList<MouseButton> pressedMouse = new ArrayList<>();

    String nowAction = "stand", pastAction = "stand", attackNPS = "left";
    ArrayList <String> itemsInChest = new ArrayList<>();

    boolean isDoubleJump = true, isAnimationToPlay = true;

    boolean isUseDoubleJump = true, isInAir = false, isEndJump = false;

    double heightFallDown = 0;
    double debafForFreeze = 1;

    boolean isAttack = false;
    boolean isWasteOfStamina = false;
    boolean isPressedAttack = false;
    boolean inMenu = false;

    boolean isJump = false;
    int heightJump = 0;
    int cameraWait = 0;

    String directionIndicator = "right";
    double xAttack, yAttack, deltaXAttack, deltaYAttack;

    double deltaXPers = 0, deltaYPers = 0;

    double isUpSpeed = 1;

    double myHealth = DataScriptFloby.myHealthNow, allMyHealth = DataScriptFloby.myHealthMax;
    double myStaminaNow = DataScriptFloby.myStaminaNow, myStaminaMax = DataScriptFloby.myStaminaMax,
            myStaminaAdded = DataScriptFloby.myStaminaAdded, myWeightNow = DataScriptFloby.myWeightNow,
            myWeightMax = DataScriptFloby.myWeightMax, myEXPNow = DataScriptFloby.myEXPNow,
            numEXPForNextLVL = DataScriptFloby.numEXPForNextLVL[DataScriptFloby.myLVL],
            myFreezeHeals = DataScriptFloby.myFreezeHeals, defFreezeArmor = DataScriptFloby.defFreezeArmor,
            isCoolInThisLocation = DataScriptFloby.isCoolInThisLocation[DataScriptFloby.indexLocation],
            deltaMainScene = DataScriptFloby.deltaScreenWindowGame, defaultSpeed = DataScriptFloby.defaultSpeed * deltaMainScene;

    AnimationTimer persRules;
    ArrayList<AnimationTimer> npsRules = new ArrayList<>();

    ArrayList<Rectangle> allGround = new ArrayList<>();
    ArrayList<AnchorPane> blockForNPS = new ArrayList<>();
    ArrayList<ImageView> graphicView = new ArrayList<>();
    ArrayList<Rectangle> portal = new ArrayList<>();
    ArrayList<AnchorPane> campfire = new ArrayList<>();
    ArrayList<AnchorPane> chest = new ArrayList<>();
    ArrayList <AnchorPane> ladder = new ArrayList<>();

    Timeline forAnimationSnowflake1;
    Timeline forAnimationSnowflake2;
    Timeline forChestSphere;

    Image[] imageViewsForChestSphere;
    int indexForChestSphere = 0;
    boolean upForChestSphere = true;
    boolean isOpenedChest = true;

    int typeCamera = 0;

    private Rectangle indicatorRegionForCameraTopLeft, indicatorRegionForCameraBottomRight, indicatorRegionForCameraTopLeft1,
            indicatorRegionForCameraBottomRight1;

    private AnchorPane pers;

    private ProgressBar health, stamina, experiences;

    private ImageView persAnimation, imgIconForImageFreezingEffect, backgroundImage, imageInfoBar;

    private MediaPlayer mediaPlayerForPers;
    private MediaPlayer mediaPlayerFire;

    AnchorPane menu = new AnchorPane();

    public void startScript(AnchorPane pers, ArrayList<Rectangle> allGround, ArrayList<AnchorPane> blockForNPS, ArrayList <ImageView> graphicView,
                            ArrayList<Rectangle> portal, ArrayList<AnchorPane> campfire, ArrayList<AnchorPane> chest, ArrayList <AnchorPane> ladder,
                            Rectangle indicatorRegionForCameraTopLeft,
                            Rectangle indicatorRegionForCameraBottomRight, ProgressBar health, ProgressBar stamina,
                            ProgressBar experiences, ImageView persAnimation, ImageView imgIconForImageFreezingEffect, ImageView backgroundImage,
                            ImageView imageInfoBar) {

        this.pers = pers;
        this.allGround = allGround;
        this.blockForNPS = blockForNPS;
        this.graphicView = graphicView;
        this.portal = portal;
        this.campfire = campfire;
        this.chest = chest;
        this.ladder = ladder;
        this.indicatorRegionForCameraTopLeft = indicatorRegionForCameraTopLeft;
        this.indicatorRegionForCameraBottomRight = indicatorRegionForCameraBottomRight;
        this.health = health;
        this.stamina = stamina;
        this.experiences = experiences;
        this.persAnimation = persAnimation;
        this.imgIconForImageFreezingEffect = imgIconForImageFreezingEffect;
        this.backgroundImage = backgroundImage;
        this.imageInfoBar = imageInfoBar;

        try {
            mediaPlayerFire = (campfire.isEmpty()) ? null : new MediaPlayer(new Media((new File("src/main/sounds/object/fire/fire.mp3")).toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            Scene scene = pers.getScene();
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    toMenu();
                } else if (e.getCode() == KeyCode.I) {
                    toInventar();
                }
                else if (e.getCode() == KeyCode.U){
                    toCraft();
                } else if (!pressedBut.contains(e.getCode())) {
                    pressedBut.add(e.getCode());
                }
            });
            scene.setOnKeyReleased(e -> pressedBut.remove(e.getCode()));
            scene.setOnMousePressed(e -> {
                if (!pressedMouse.contains(e.getButton())) {
                    pressedMouse.add(e.getButton());
                }
            });
            scene.setOnMouseReleased(e -> pressedMouse.remove(e.getButton()));
            scene.setCursor(Cursor.NONE);
        });

        if (!chest.isEmpty()){
            String s = DataScriptFloby.chest[DataScriptFloby.thisLocation];
            if (((String[])s.split(" +"))[1].equals("1")){
                String[] items = ((String[])s.split(" +"))[0].split(",");
                itemsInChest.addAll(List.of(items));
                activatedChest(0);
                isOpenedChest=false;
            }
        }

        startScriptCamera();
        persRulesPlay();

        int npsIndex = 0;

        for (AnchorPane nps : blockForNPS) {
            switch (Integer.parseInt(((Label) nps.getChildren().get(0)).getText())) {
                case (1) -> {
                    wolfRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (2) -> {

                }
                case (3) -> {
                    birdRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (4) -> {
                    boarRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (5) -> {
                    bearRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
            }
            npsIndex++;
        }
    }

    public void startScript(AnchorPane pers, ArrayList<Rectangle> allGround, ArrayList<AnchorPane> blockForNPS, ArrayList <ImageView> graphicView,
                            ArrayList<Rectangle> portal, ArrayList<AnchorPane> campfire, ArrayList<AnchorPane> chest, ArrayList <AnchorPane> ladder,
                            Rectangle indicatorRegionForCameraTopLeft,
                            Rectangle indicatorRegionForCameraBottomRight,  Rectangle indicatorRegionForCameraTopLeft1,
                            Rectangle indicatorRegionForCameraBottomRight1, ProgressBar health, ProgressBar stamina,
                            ProgressBar experiences, ImageView persAnimation, ImageView imgIconForImageFreezingEffect, ImageView backgroundImage,
                            ImageView imageInfoBar) {

        this.pers = pers;
        this.allGround = allGround;
        this.blockForNPS = blockForNPS;
        this.graphicView = graphicView;
        this.portal = portal;
        this.campfire = campfire;
        this.chest = chest;
        this.ladder = ladder;
        this.indicatorRegionForCameraTopLeft = indicatorRegionForCameraTopLeft;
        this.indicatorRegionForCameraTopLeft1 = indicatorRegionForCameraTopLeft1;
        this.indicatorRegionForCameraBottomRight = indicatorRegionForCameraBottomRight;
        this.indicatorRegionForCameraBottomRight1 = indicatorRegionForCameraBottomRight1;
        this.health = health;
        this.stamina = stamina;
        this.experiences = experiences;
        this.persAnimation = persAnimation;
        this.imgIconForImageFreezingEffect = imgIconForImageFreezingEffect;
        this.backgroundImage = backgroundImage;
        this.imageInfoBar = imageInfoBar;

        try {
            mediaPlayerFire = (campfire.isEmpty()) ? null : new MediaPlayer(new Media((new File("src/main/sounds/object/fire/fire.mp3")).toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            Scene scene = pers.getScene();
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    toMenu();
                } else if (e.getCode() == KeyCode.I) {
                    toInventar();
                }
                else if (e.getCode() == KeyCode.U){
                    toCraft();
                } else if (!pressedBut.contains(e.getCode())) {
                    pressedBut.add(e.getCode());
                }
            });
            scene.setOnKeyReleased(e -> pressedBut.remove(e.getCode()));
            scene.setOnMousePressed(e -> {
                if (!pressedMouse.contains(e.getButton())) {
                    pressedMouse.add(e.getButton());
                }
            });
            scene.setOnMouseReleased(e -> pressedMouse.remove(e.getButton()));
            scene.setCursor(Cursor.NONE);
        });

        if (!chest.isEmpty()){
            String s = DataScriptFloby.chest[DataScriptFloby.thisLocation];
            if (((String[])s.split(" +"))[1].equals("1")){
                String[] items = ((String[])s.split(" +"))[0].split(",");
                itemsInChest.addAll(List.of(items));
                activatedChest(0);
                isOpenedChest=false;
            }
        }

        startScriptCamera();
        persRulesPlay();

        int npsIndex = 0;

        for (AnchorPane nps : blockForNPS) {
            switch (Integer.parseInt(((Label) nps.getChildren().get(0)).getText())) {
                case (1) -> {
                    wolfRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (2) -> {

                }
                case (3) -> {
                    birdRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (4) -> {
                    boarRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
                case (5) -> {
                    bearRulesPlay(nps);
                    ((Label) nps.getChildren().get(1)).setText("" + npsIndex);
                }
            }
            npsIndex++;
        }
    }

    public void endScript() {
        DataScriptFloby.myFreezeHeals = this.myFreezeHeals;
        DataScriptFloby.myHealthNow = this.myHealth;
        DataScriptFloby.myStaminaNow = this.myStaminaNow;
        stopAllAnimation();
    }

    void startScriptCamera() {

        if (!DataMusic.isMusic){
            DataMusic.typeMusic = 1;
            DataMusic.isMusic = true;
            DataMusic.startMusic(1);
        }

        for (AnchorPane anchorPane : blockForNPS){
            for (int i=0;i<2;i++){
                ((Label)anchorPane.getChildren().get(i)).setTextFill(Color.rgb(0,0,0,0));
            }
        }

        try {
            persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imgIconForImageFreezingEffect.setOpacity(0);

        backgroundImage.setFitWidth(1280 * DataScriptFloby.deltaScreenWindowGame);
        backgroundImage.setFitHeight(720 * DataScriptFloby.deltaScreenWindowGame);

        for (Rectangle rectangle : allGround) {
            rectangle.setLayoutX((rectangle.getLayoutX() + deltaXPers) * deltaMainScene);
            rectangle.setLayoutY((rectangle.getLayoutY() + deltaYPers) * deltaMainScene);

            rectangle.setWidth(rectangle.getWidth() * deltaMainScene);
            rectangle.setHeight(rectangle.getHeight() * deltaMainScene);
        }

        for (Rectangle rectangle : portal) {
            rectangle.setLayoutX((rectangle.getLayoutX() + deltaXPers) * deltaMainScene);
            rectangle.setLayoutY((rectangle.getLayoutY() + deltaYPers) * deltaMainScene);

            rectangle.setWidth(rectangle.getWidth() * deltaMainScene);
            rectangle.setHeight(rectangle.getHeight() * deltaMainScene);
        }

        for (AnchorPane anchorPane : blockForNPS) {
            anchorPane.setLayoutX((anchorPane.getLayoutX() + deltaXPers) * deltaMainScene);
            anchorPane.setLayoutY((anchorPane.getLayoutY() + deltaYPers) * deltaMainScene);

            anchorPane.setPrefWidth(anchorPane.getPrefWidth() * deltaMainScene);
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() * deltaMainScene);
        }

        for (AnchorPane anchorPane : campfire) {
            anchorPane.setLayoutX((anchorPane.getLayoutX() + deltaXPers) * deltaMainScene);
            anchorPane.setLayoutY((anchorPane.getLayoutY() + deltaYPers) * deltaMainScene);

            anchorPane.setPrefWidth(anchorPane.getPrefWidth() * deltaMainScene);
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() * deltaMainScene);

            anchorPane.getChildren().get(0).setLayoutX((anchorPane.getChildren().get(0).getLayoutX()) * deltaMainScene);
            anchorPane.getChildren().get(0).setLayoutY((anchorPane.getChildren().get(0).getLayoutY()) * deltaMainScene);

            ((ImageView)anchorPane.getChildren().get(0)).setFitWidth(((ImageView)anchorPane.getChildren().get(0)).getFitWidth() * deltaMainScene);
            ((ImageView)anchorPane.getChildren().get(0)).setFitHeight(((ImageView)anchorPane.getChildren().get(0)).getFitHeight() * deltaMainScene);

            try {
                ((ImageView)anchorPane.getChildren().get(0)).setImage(new Image(new FileInputStream("src/main/animation/object/campfire/campfire.gif")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (AnchorPane anchorPane : chest) {
            anchorPane.setLayoutX((anchorPane.getLayoutX() + deltaXPers) * deltaMainScene);
            anchorPane.setLayoutY((anchorPane.getLayoutY() + deltaYPers) * deltaMainScene);

            anchorPane.setPrefWidth(anchorPane.getPrefWidth() * deltaMainScene);
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() * deltaMainScene);
        }

        for (AnchorPane anchorPane : ladder) {
            anchorPane.setLayoutX((anchorPane.getLayoutX() + deltaXPers) * deltaMainScene);
            anchorPane.setLayoutY((anchorPane.getLayoutY() + deltaYPers) * deltaMainScene);

            anchorPane.setPrefWidth(anchorPane.getPrefWidth() * deltaMainScene);
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() * deltaMainScene);
        }

        for (ImageView imageView : graphicView) {
            imageView.setLayoutX((imageView.getLayoutX() + deltaXPers) * deltaMainScene);
            imageView.setLayoutY((imageView.getLayoutY() + deltaYPers) * deltaMainScene);

            imageView.setFitWidth(imageView.getFitWidth() * deltaMainScene);
            imageView.setFitHeight(imageView.getFitHeight() * deltaMainScene);
        }

        pers.setLayoutX((pers.getLayoutX() + deltaXPers) * deltaMainScene);
        pers.setLayoutY((pers.getLayoutY() + deltaYPers) * deltaMainScene);

        pers.setPrefWidth(pers.getPrefWidth() * deltaMainScene);
        pers.setPrefHeight(pers.getPrefHeight() * deltaMainScene);

        persAnimation.setFitWidth(pers.getPrefWidth());
        persAnimation.setFitHeight(pers.getPrefHeight());

        imageInfoBar.setLayoutX(imageInfoBar.getLayoutX() * deltaMainScene);
        imageInfoBar.setLayoutY(imageInfoBar.getLayoutY() * deltaMainScene);

        imageInfoBar.setFitWidth(imageInfoBar.getFitWidth() * deltaMainScene);
        imageInfoBar.setFitHeight(imageInfoBar.getFitHeight() * deltaMainScene);

        health.setLayoutX(health.getLayoutX() * deltaMainScene);
        health.setLayoutY(health.getLayoutY() * deltaMainScene);

        health.setPrefWidth(health.getPrefWidth() * deltaMainScene);
        health.setPrefHeight(health.getPrefHeight() * deltaMainScene);

        stamina.setLayoutX(stamina.getLayoutX() * deltaMainScene);
        stamina.setLayoutY(stamina.getLayoutY() * deltaMainScene);

        stamina.setPrefWidth(stamina.getPrefWidth() * deltaMainScene);
        stamina.setPrefHeight(stamina.getPrefHeight() * deltaMainScene);

        if (pers.getLayoutX() <= (DataScriptFloby.widthScreenWindowGame / 2) - indicatorRegionForCameraTopLeft.getLayoutX()) {
            deltaXPers = -indicatorRegionForCameraTopLeft.getLayoutX() - 40;
            pers.setLayoutX(pers.getLayoutX() + deltaXPers);
        } else if (indicatorRegionForCameraBottomRight.getLayoutX() + indicatorRegionForCameraBottomRight.getWidth() - pers.getLayoutX() <= DataScriptFloby.widthScreenWindowGame / 2) {
            deltaXPers = DataScriptFloby.widthScreenWindowGame - (indicatorRegionForCameraBottomRight.getLayoutX() + indicatorRegionForCameraBottomRight.getWidth());
            pers.setLayoutX(pers.getLayoutX() + deltaXPers);
        } else {
            deltaXPers = (DataScriptFloby.widthScreenWindowGame / 2 - 25) - pers.getLayoutX();
            pers.setLayoutX((DataScriptFloby.widthScreenWindowGame / 2 - 25));
        }

        if (indicatorRegionForCameraBottomRight.getLayoutY() + indicatorRegionForCameraBottomRight.getHeight() - pers.getLayoutY() <= DataScriptFloby.heightScreenWindowGame / 2 + 20) {
            deltaYPers = DataScriptFloby.heightScreenWindowGame - (indicatorRegionForCameraBottomRight.getLayoutY() + indicatorRegionForCameraBottomRight.getHeight());
            pers.setLayoutY(pers.getLayoutY() + deltaYPers);
        } else if (pers.getLayoutY() <= indicatorRegionForCameraTopLeft.getLayoutY() + DataScriptFloby.heightScreenWindowGame / 2) {
            deltaYPers = -indicatorRegionForCameraTopLeft.getLayoutY();
            pers.setLayoutY(pers.getLayoutY() + deltaYPers);
        } else {
            deltaYPers = (DataScriptFloby.heightScreenWindowGame / 2 - 25) - pers.getLayoutY();
            pers.setLayoutY((DataScriptFloby.heightScreenWindowGame / 2 - 25));
        }


        for (Rectangle rectangle : allGround) {
            rectangle.setLayoutX(rectangle.getLayoutX() + deltaXPers);
            rectangle.setLayoutY(rectangle.getLayoutY() + deltaYPers);
        }

        for (Rectangle rectangle : portal) {
            rectangle.setLayoutX(rectangle.getLayoutX() + deltaXPers);
            rectangle.setLayoutY(rectangle.getLayoutY() + deltaYPers);
        }

        for (AnchorPane anchorPane : blockForNPS) {
            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
        }

        for (AnchorPane anchorPane : campfire) {
            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
        }

        for (AnchorPane anchorPane : chest) {
            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
        }

        for (AnchorPane anchorPane : ladder) {
            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
        }

        for (ImageView imageView : graphicView) {
            imageView.setLayoutX(imageView.getLayoutX() + deltaXPers);
            imageView.setLayoutY(imageView.getLayoutY() + deltaYPers);
        }

        createMenu();

    }

    void createMenu(){

        try {
            menu.setLayoutX(0);
            menu.setLayoutY(0);
            menu.setPrefWidth(1280 * deltaMainScene);
            menu.setPrefHeight(720 * deltaMainScene);

            ImageView backGround = new ImageView(new Image(new FileInputStream("src/main/img/menu/gameMenu.png")));
            backGround.setLayoutX(0);
            backGround.setLayoutY(0);
            backGround.setFitWidth(1280 * deltaMainScene);
            backGround.setFitHeight(720 * deltaMainScene);

            ImageView animationWalk = new ImageView(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk1.gif")));
            animationWalk.setLayoutX(740 * deltaMainScene);
            animationWalk.setLayoutY(237 * deltaMainScene);
            animationWalk.setFitWidth(100 * deltaMainScene);
            animationWalk.setFitHeight(100 * deltaMainScene);

            ImageView animationRun = new ImageView(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk2.gif")));
            animationRun.setLayoutX(740 * deltaMainScene);
            animationRun.setLayoutY(435 * deltaMainScene);
            animationRun.setFitWidth(100 * deltaMainScene);
            animationRun.setFitHeight(100 * deltaMainScene);

            ImageView animationAttack = new ImageView(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleSwordAttack.gif")));
            animationAttack.setLayoutX(975 * deltaMainScene);
            animationAttack.setLayoutY(237 * deltaMainScene);
            animationAttack.setFitWidth(131 * deltaMainScene);
            animationAttack.setFitHeight(91 * deltaMainScene);

            ImageView animationJump = new ImageView(new Image(new FileInputStream("src/main/animation/mainHero/Floby/jump.gif")));
            animationJump.setLayoutX(1000 * deltaMainScene);
            animationJump.setLayoutY(435 * deltaMainScene);
            animationJump.setFitWidth(100 * deltaMainScene);
            animationJump.setFitHeight(100 * deltaMainScene);

            Button buttonContinue = new Button();
            buttonContinue.setLayoutX(113 * deltaMainScene);
            buttonContinue.setLayoutY(180 * deltaMainScene);
            buttonContinue.setPrefWidth(265 * deltaMainScene);
            buttonContinue.setPrefHeight(45 * deltaMainScene);
            buttonContinue.setOpacity(0);
            buttonContinue.setOnAction(this::menuContinue);

            Button buttonLoad = new Button();
            buttonLoad.setLayoutX(113 * deltaMainScene);
            buttonLoad.setLayoutY(255 * deltaMainScene);
            buttonLoad.setPrefWidth(265 * deltaMainScene);
            buttonLoad.setPrefHeight(45 * deltaMainScene);
            buttonLoad.setOpacity(0);
            buttonLoad.setOnAction(this::menuLoad);

            Button buttonToMenu = new Button();
            buttonToMenu.setLayoutX(113 * deltaMainScene);
            buttonToMenu.setLayoutY(457 * deltaMainScene);
            buttonToMenu.setPrefWidth(265 * deltaMainScene);
            buttonToMenu.setPrefHeight(45 * deltaMainScene);
            buttonToMenu.setOpacity(0);
            buttonToMenu.setOnAction(this::menuToMenu);

            Button buttonExited = new Button();
            buttonExited.setLayoutX(113 * deltaMainScene);
            buttonExited.setLayoutY(520 * deltaMainScene);
            buttonExited.setPrefWidth(265 * deltaMainScene);
            buttonExited.setPrefHeight(45 * deltaMainScene);
            buttonExited.setOpacity(0);
            buttonExited.setOnAction(this::menuExited);

            menu.setVisible(false);
            menu.setDisable(true);
            menu.setOpacity(0);

            menu.getChildren().addAll(backGround, animationWalk, animationRun, animationAttack, animationJump, buttonContinue, buttonLoad,
                    buttonToMenu, buttonExited);
            ((AnchorPane) pers.getParent()).getChildren().add(menu);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void menuContinue (ActionEvent event){
        menu.setVisible(false);
        menu.setDisable(true);
        menu.setOpacity(0);
        persRules.start();
        Platform.runLater(() -> {
            ((Scene) pers.getScene()).setCursor(Cursor.NONE);
        });
        inMenu = false;
    }

    void menuLoad (ActionEvent actionEvent){
        loadSavedScene();
    }

    void menuToMenu (ActionEvent event){
        stopAllAnimation();
        DataMusic.stopMusic();
        DataMusic.isMusic = false;
        Stage stage = (Stage) pers.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));

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

    void menuExited (ActionEvent event) {
        stopAllAnimation();
        DataMusic.stopMusic();
        DataMusic.isMusic = false;
        ((Stage) pers.getScene().getWindow()).close();
    }

    void activatedChest(int indexChest){

        ImageView corpse = ((ImageView) chest.get(indexChest).getChildren().get(0));
        ImageView sphere = ((ImageView) chest.get(indexChest).getChildren().get(1));

        try {
            imageViewsForChestSphere = new Image[]{
                    new Image(new FileInputStream("src/main/animation/object/chest/1.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/2.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/3.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/4.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/5.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/6.png")),
                    new Image(new FileInputStream("src/main/animation/object/chest/7.png"))
            };

            corpse.setImage(new Image(new FileInputStream("src/main/animation/object/chest/corpse.png")));
            corpse.setFitWidth(50 * deltaMainScene);
            corpse.setFitHeight(22 * deltaMainScene);
            corpse.setLayoutX(0);
            corpse.setLayoutY(35 * deltaMainScene);

            sphere.setImage(new Image(new FileInputStream("src/main/animation/object/chest/1.png")));
            sphere.setRotate(0);
            sphere.setFitWidth(30 * deltaMainScene);
            sphere.setFitHeight(30 * deltaMainScene);
            sphere.setLayoutX(10 * deltaMainScene);
            sphere.setLayoutY(0);


            forChestSphere = new Timeline(new KeyFrame(Duration.millis(500),
                    new KeyValue(sphere.rotateProperty(), 360 )));
            forChestSphere.setOnFinished(e -> {
                sphere.setRotate(0);
                sphere.setImage(imageViewsForChestSphere[indexForChestSphere]);
                if (upForChestSphere && indexForChestSphere==3) upForChestSphere=false;
                else if (!upForChestSphere && indexForChestSphere==0) upForChestSphere=true;
                if (upForChestSphere) indexForChestSphere++;
                else indexForChestSphere--;
                sphere.setRotate(0);
                forChestSphere.play();
            });
            forChestSphere.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void disactivatedChest(int indexChest){
        forChestSphere.stop();

        ImageView sphere = ((ImageView) chest.get(indexChest).getChildren().get(1));
        try {
            sphere.setImage(new Image(new FileInputStream("src/main/animation/object/chest/lightWithoutLoop.gif")));
            sphere.setFitWidth(30 * deltaMainScene);
            sphere.setFitHeight(30 * deltaMainScene);
            sphere.setLayoutX(10 * deltaMainScene);
            sphere.setLayoutY(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void persDead(){
        stopAllAnimation();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int j=0;
            @Override
            public void run() {
                if (j==1){
                    Platform.runLater(() -> {
                        DataScriptFloby.loadDataGame();
                        DataScriptFloby.loadSavedScene((Stage) pers.getScene().getWindow());
                    });
                }
                j++;
            }
        },0,2000);
    }

    void persRulesPlay() {
        persRules = new AnimationTimer() {

            String nowActionForSound = "stand";
            String pastActionForSound = "stand";
            boolean stopSounds = false;

            boolean wasInAir = false;

            boolean wasNearCampfire = false;

            @Override
            public void handle(long l) {

                if (mustCorrect) {
                    correctDataScript();
                }

                if (loadScene) {
                    loadSavedScene();
                }

                if (myHealth <= 0) {
                    myHealth = 0;

                    try {
                        persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/dead.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    persAnimation.setScaleX(1);
                    persAnimation.setFitWidth(65 * deltaMainScene);
                    persAnimation.setLayoutX(0);
                    persAnimation.setLayoutY(5 * deltaMainScene);

                    persDead();
                } else {

                    boolean fallDown, onLadder = false;
                    isWasteOfStamina = false;

                    /*Движение по леснице, лианам, корням*/
                    for (AnchorPane anchorPane : ladder) {
                        if (pers.getBoundsInParent().intersects(anchorPane.getBoundsInParent())) {
                            onLadder = true;
                            break;
                        }
                    }

                    if (onLadder) {
                        if (pressedBut.contains(KeyCode.W) && !pressedBut.contains(KeyCode.S)) {
                            pers.setLayoutY(pers.getLayoutY() - 3 * deltaMainScene);
                        }
                        if (pressedBut.contains(KeyCode.S) && !pressedBut.contains(KeyCode.W)) {
                            pers.setLayoutY(pers.getLayoutY() + 5 * deltaMainScene);
                        }
                    }

                    /* Падение персонажа */
                    fallDown = true;

                    if (!isJump && !onLadder) {
                        isInAir = false;
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                                    (rectangle.getLayoutY() + 7 > pers.getHeight() + pers.getLayoutY())) {
                                fallDown = false;
                                if (heightFallDown / 2 >= 250 * deltaMainScene) {
                                    myHealth -= (heightFallDown / 2 - 250 * deltaMainScene) * 0.1 * (1 + myWeightNow / myWeightMax);
                                }
                                heightFallDown = 0;
                                break;
                            }
                        }
                        if (fallDown) {
                            pers.setLayoutY(pers.getLayoutY() + 8 * deltaMainScene);
                            heightFallDown += 8 * deltaMainScene;
                            isWasteOfStamina = true;
                            isInAir = true;
                            wasInAir = true;
                        }
                    } else if (!isAnimationToPlay && !onLadder) {
                        fallDown = true;
                        isInAir = false;
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                                    (rectangle.getLayoutY() + 25 * deltaMainScene > pers.getHeight() + pers.getLayoutY())) {
                                fallDown = false;
                                break;
                            }
                        }
                        if (fallDown) {
                            pers.setLayoutY(pers.getLayoutY() + 8 * deltaMainScene);
                            isWasteOfStamina = true;
                            isInAir = true;
                        }
                    }

                    if (wasInAir && !fallDown){
                        wasInAir = false;
                        nowActionForSound = "jump";
                    }
                    /*----------------------------------------------------*/

                    if (pressedBut.contains(KeyCode.CONTROL) && myStaminaNow - 2 >= 0 && !isInAir) {
                        //isUpSpeed = 1.5 * debafForFreeze;
                        isUpSpeed = debafForFreeze + 0.5;

                        if (pressedBut.contains(KeyCode.A) || pressedBut.contains(KeyCode.D)) {
                            isWasteOfStamina = true;
                            myStaminaNow--;
                        }
                    } else {
                        isUpSpeed = 1;
                    }

                    if (pressedBut.contains(KeyCode.CONTROL)) {
                        isWasteOfStamina = true;
                    }

                    /*Движение влево*/

                    if (pressedBut.contains(KeyCode.A) && isAnimationToPlay) {
                        pers.setLayoutX(pers.getLayoutX() - (int) (defaultSpeed * isUpSpeed * ((myWeightMax - myWeightNow) / myWeightMax) * debafForFreeze));
                        if (!isInAir) {
                            nowAction = "walkLeft";
                            if (pressedBut.contains(KeyCode.CONTROL)) nowActionForSound = "run";
                            else nowActionForSound = "walk";
                        }
                    }

                    /*----------------------------------------------------*/



                    /*Движение вправо*/

                    if (pressedBut.contains(KeyCode.D) && isAnimationToPlay) {
                        pers.setLayoutX(pers.getLayoutX() + (int) (defaultSpeed * isUpSpeed * ((myWeightMax - myWeightNow) / myWeightMax)) * debafForFreeze);
                        if (!isInAir) {
                            nowAction = "walkRight";
                            if (pressedBut.contains(KeyCode.CONTROL)) nowActionForSound = "run";
                            else nowActionForSound = "walk";
                        }
                    }

                    /*----------------------------------------------------*/


                    if (pressedBut.contains(KeyCode.D) && pressedBut.contains(KeyCode.A) && isAnimationToPlay) {
                        nowAction = "stand";
                    }


                    /*Прижок*/

                    if (!isJump) {
                        if (myStaminaNow - 101.0 * (myWeightNow / myWeightMax) >= 0) {
                            for (Rectangle rectangle : allGround) {
                                if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent())
                                        && (rectangle.getLayoutY() + 7 > pers.getHeight() + pers.getLayoutY())) {
                                    if (pressedBut.contains(KeyCode.SPACE)) {
                                        isEndJump = false;
                                        heightJump = 0;
                                        isJump = true;
                                        pers.setLayoutY(pers.getLayoutY() - 7);
                                        isWasteOfStamina = true;
                                        myStaminaNow -= 100.0 * (myWeightNow / myWeightMax);
                                        isInAir = true;
                                        wasInAir = true;
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        if (pressedBut.contains(KeyCode.SPACE) && heightJump <= 100 * deltaMainScene) {
                            pers.setLayoutY(pers.getLayoutY() - 7);
                            heightJump += 5;
                            isWasteOfStamina = true;
                        } else {
                            heightJump = 0;
                            isWasteOfStamina = true;
                            isJump = false;
                            isEndJump = true;
                            isInAir = false;
                        }
                    }


                /*if (!pressedBut.contains(KeyCode.SPACE)&&isEndJump) {
                    isInAir = true;
                }*/

                /*if (isDoubleJump&&isInAir) {
                    if (isUseDoubleJump){
                        if (pressedBut.contains(KeyCode.SPACE)) {
                            heightJump = 0;
                            isUseDoubleJump=false;
                            pers.setLayoutY(pers.getLayoutY() - 14);
                        }
                    } else {
                        if (pressedBut.contains(KeyCode.SPACE) && heightJump <= 100) {
                            pers.setLayoutY(pers.getLayoutY() - 14);
                            heightJump += 5;
                        } else {
                            heightJump = 0;
                            isInAir = false;
                            isUseDoubleJump=true;
                        }
                    }
                }*/


                    /*----------------------------------------------------*/



                    /*Колизия*/

                    if (pressedBut.contains(KeyCode.SPACE) || onLadder) {
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                                if (pers.getLayoutY() >= rectangle.getLayoutY() + rectangle.getHeight() - 10 * deltaMainScene) {
                                    pers.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
                                    isJump = false;
                                    isInAir = false;
                                    heightJump = 0;
                                    break;
                                }
                            }
                        }
                    }

                    if (pressedBut.contains(KeyCode.A)) {
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                                if ((pers.getLayoutX() >= rectangle.getLayoutX() + rectangle.getWidth() - 7 * deltaMainScene) &&
                                        (pers.getLayoutY() + pers.getHeight() - 5 * deltaMainScene >= rectangle.getLayoutY())) {
                                    pers.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth());
                                }
                            }
                        }
                    }

                    if (pressedBut.contains(KeyCode.D)) {
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                                if ((rectangle.getLayoutX() + 8 * deltaMainScene >= pers.getLayoutX() + pers.getWidth()) &&
                                        (pers.getLayoutY() + pers.getHeight() - 8 * deltaMainScene >= rectangle.getLayoutY())) {
                                    pers.setLayoutX(rectangle.getLayoutX() - pers.getWidth());
                                }
                            }
                        }
                    }

                    /*----------------------------------------------------*/



                    /*Коректирировка положения на блоке*/
                    if (isAnimationToPlay) {
                        for (Rectangle rectangle : allGround) {
                            if (pers.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                                if (rectangle.getLayoutY() + 8 * deltaMainScene > pers.getHeight() + pers.getLayoutY()) {
                                    isInAir = false;
                                    pers.setLayoutY(rectangle.getLayoutY() - pers.getHeight());
                                    break;
                                }
                            }
                        }
                    }

                    /*----------------------------------------------------*/



                    /*Привязка камеры*/

                    if (cameraWait == 0) {

                        switch (typeCamera) {
                            case (0) -> {
                                if ((pers.getLayoutX() <= indicatorRegionForCameraTopLeft.getLayoutX() + DataScriptFloby.widthScreenWindowGame / 2) ||
                                        (pers.getLayoutX() >= indicatorRegionForCameraBottomRight.getLayoutX() + indicatorRegionForCameraBottomRight.getWidth() -
                                                (DataScriptFloby.widthScreenWindowGame / 2 + 30))) {
                                    deltaXPers = 0;
                                } else {
                                    deltaXPers = (DataScriptFloby.widthScreenWindowGame / 2 - 25) - pers.getLayoutX();
                                    pers.setLayoutX((DataScriptFloby.widthScreenWindowGame / 2 - 25));
                                }

                                if ((indicatorRegionForCameraBottomRight.getLayoutY() + indicatorRegionForCameraBottomRight.getHeight() - pers.getLayoutY() <= (DataScriptFloby.heightScreenWindowGame / 2 + 30)) ||
                                        (pers.getLayoutY() <= indicatorRegionForCameraTopLeft.getLayoutY() + DataScriptFloby.heightScreenWindowGame / 2)) {
                                    deltaYPers = 0;
                                } else {
                                    deltaYPers = (DataScriptFloby.heightScreenWindowGame / 2 - 25) - pers.getLayoutY();
                                    pers.setLayoutY(DataScriptFloby.heightScreenWindowGame / 2 - 25);
                                }
                            }
                            case (1) -> {
                                if ((pers.getLayoutX() <= indicatorRegionForCameraTopLeft1.getLayoutX() + DataScriptFloby.widthScreenWindowGame / 2) ||
                                        (pers.getLayoutX() >= indicatorRegionForCameraBottomRight1.getLayoutX() + indicatorRegionForCameraBottomRight1.getWidth() - (DataScriptFloby.widthScreenWindowGame / 2 + 30))) {
                                    deltaXPers = 0;
                                } else {
                                    deltaXPers = (DataScriptFloby.widthScreenWindowGame / 2 - 25) - pers.getLayoutX();
                                    pers.setLayoutX((DataScriptFloby.widthScreenWindowGame / 2 - 25));
                                }

                                if ((indicatorRegionForCameraBottomRight1.getLayoutY() + indicatorRegionForCameraBottomRight1.getHeight() - pers.getLayoutY() <= (DataScriptFloby.heightScreenWindowGame / 2 + 30)) ||
                                        (pers.getLayoutY() <= indicatorRegionForCameraTopLeft1.getLayoutY() + DataScriptFloby.heightScreenWindowGame / 2)) {
                                    deltaYPers = 0;
                                } else {
                                    deltaYPers = (DataScriptFloby.heightScreenWindowGame / 2 - 25) - pers.getLayoutY();
                                    pers.setLayoutY(DataScriptFloby.heightScreenWindowGame / 2 - 25);
                                }
                            }
                        }

                        for (Rectangle rectangle : allGround) {
                            rectangle.setLayoutX(rectangle.getLayoutX() + deltaXPers);
                            rectangle.setLayoutY(rectangle.getLayoutY() + deltaYPers);
                        }

                        for (Rectangle rectangle : portal) {
                            rectangle.setLayoutX(rectangle.getLayoutX() + deltaXPers);
                            rectangle.setLayoutY(rectangle.getLayoutY() + deltaYPers);
                        }

                        for (AnchorPane anchorPane : blockForNPS) {
                            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
                            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
                        }

                        for (AnchorPane anchorPane : campfire) {
                            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
                            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
                        }

                        for (AnchorPane anchorPane : chest) {
                            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
                            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
                        }

                        for (AnchorPane anchorPane : ladder) {
                            anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaXPers);
                            anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaYPers);
                        }

                        for (ImageView imageView : graphicView) {
                            imageView.setLayoutX(imageView.getLayoutX() + deltaXPers);
                            imageView.setLayoutY(imageView.getLayoutY() + deltaYPers);
                        }
                    } else {
                        cameraWait--;
                    }

                    /*----------------------------------------------------*/


                    /*Коректировка згляда*/

                    if (pressedBut.contains(KeyCode.D) && !pressedBut.contains(KeyCode.A)) {
                        directionIndicator = "right";
                    } else if (pressedBut.contains(KeyCode.A) && !pressedBut.contains(KeyCode.D)) {
                        directionIndicator = "left";
                    }

                    /*----------------------------------------------------*/


                    /*Атака*/

                    if (!isPressedAttack) {
                        if (pressedMouse.contains(MouseButton.PRIMARY) && myStaminaNow - 21 >= 0) {

                            nowActionForSound = "swordAttack";

                            isPressedAttack = true;

                            myStaminaNow -= 20;
                            isWasteOfStamina = true;

                            if (pressedBut.contains(KeyCode.W)) {
                                nowAction = "simpleUpSwordAttack";
                                attackNPS = "top";
                            } else if (pressedBut.contains(KeyCode.S)) {
                                nowAction = "simpleDownSwordAttack";
                                attackNPS = "down";
                            } else {
                                nowAction = "simpleSwordAttack";
                                attackNPS = directionIndicator;
                            }

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                int j = 0;

                                @Override
                                public void run() {
                                    if (j == 1) {
                                        isAttack = true;
                                    }
                                    if (j == 2) {
                                        isAttack = false;
                                        isPressedAttack = false;
                                        timer.cancel();
                                        return;
                                    }
                                    j++;
                                }
                            }, 0, 150);

                            switch (attackNPS) {
                                case "right" -> {
                                    xAttack = pers.getLayoutX() + 50 * deltaMainScene;
                                    yAttack = pers.getLayoutY() + 20 * deltaMainScene;
                                    deltaXAttack = 60 * deltaMainScene;
                                    deltaYAttack = 10 * deltaMainScene;
                                }
                                case "left" -> {
                                    xAttack = pers.getLayoutX();
                                    yAttack = pers.getLayoutY() + 20 * deltaMainScene;
                                    deltaXAttack = -60 * deltaMainScene;
                                    deltaYAttack = 10 * deltaMainScene;
                                }
                                case "top" -> {
                                    xAttack = pers.getLayoutX() + 10 * deltaMainScene;
                                    yAttack = pers.getLayoutY();
                                    deltaXAttack = 30 * deltaMainScene;
                                    deltaYAttack = -60 * deltaMainScene;
                                }
                                case "down" -> {
                                    xAttack = pers.getLayoutX();
                                    yAttack = pers.getLayoutY() + 50 * deltaMainScene;
                                    deltaXAttack = 50 * deltaMainScene;
                                    deltaYAttack = 100 * deltaMainScene;
                                }
                            }
                        }
                    } else {
                        isWasteOfStamina = true;
                    }

                    /*----------------------------------------------------*/

                    /*Взаимодействие с сундуком*/

                    if (!isOpenedChest) {
                        if (pressedBut.contains(KeyCode.W)) {
                            for (AnchorPane anchorPane : chest) {
                                if (anchorPane.getBoundsInParent().intersects(pers.getBoundsInParent())) {
                                    isOpenedChest = true;
                                    for (String item : itemsInChest) {
                                        for (int i = 0; i < Integer.parseInt(((String[]) item.split(":"))[1]); i++) {
                                            DataScriptFloby.myItems.add(((String[]) item.split(":"))[0]);
                                        }
                                    }
                                    disactivatedChest(0);
                                    String s = ((String[]) DataScriptFloby.chest[DataScriptFloby.thisLocation].split(" "))[0] + " 0";
                                    DataScriptFloby.chest[DataScriptFloby.thisLocation] = s;
                                }
                            }
                        }
                    }

                    /*----------------------------------------------------*/

                    /*Установка шкалы здоровья*/

                    if (myHealth > 0) {
                        health.setProgress(myHealth / allMyHealth);
                    }

                    /*----------------------------------------------------*/

                    /*Установка шкалы выносливости*/

                    if (isWasteOfStamina) {
                        if (myStaminaNow > 0) {
                            stamina.setProgress(myStaminaNow / myStaminaMax);
                        } else {
                            stamina.setProgress(0.0);
                        }
                    } else {
                        if (myStaminaNow <= myStaminaMax) {
                            if (myStaminaNow + myStaminaAdded >= myStaminaMax) {
                                myStaminaNow = myStaminaMax;
                            } else {
                                myStaminaNow += myStaminaAdded;
                            }
                        }
                        stamina.setProgress(myStaminaNow / myStaminaMax);
                    }

                    /*----------------------------------------------------*/

                    /*Установка шкалы опыта*/

                    if (myEXPNow < numEXPForNextLVL) {
                        experiences.setProgress(myEXPNow / numEXPForNextLVL);
                    } else {
                        experiences.setProgress(0.0);
                        DataScriptFloby.myLVL++;
                        myEXPNow = 0;
                        numEXPForNextLVL = DataScriptFloby.numEXPForNextLVL[DataScriptFloby.myLVL];
                    }

                    /*----------------------------------------------------*/

                    /*Установка замерзания*/

                    boolean nearCampfire = false;

                    for (AnchorPane anchorPane : campfire) {
                        if (anchorPane.getBoundsInParent().intersects(pers.getBoundsInParent())) {
                            nearCampfire = true;
                            break;
                        }
                    }

                    if (!wasNearCampfire && nearCampfire) {
                        wasNearCampfire = true;
                        mediaPlayerFire.play();
                    }

                    if (wasNearCampfire && !nearCampfire){
                        wasNearCampfire = false;
                        mediaPlayerFire.stop();
                    }

                    if (nearCampfire) {
                        if (myFreezeHeals <= 0) {
                            myFreezeHeals = 0;
                        } else {
                            myFreezeHeals -= 0.5;
                            if ((int) myFreezeHeals == 20) {
                                myFreezeHeals--;
                                debafForFreeze = 1;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0))).play();
                            } else if ((int) myFreezeHeals == 40) {
                                myFreezeHeals--;
                                debafForFreeze = 0.9;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.2))).play();
                            } else if ((int) myFreezeHeals == 60) {
                                myFreezeHeals--;
                                debafForFreeze = 0.7;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.5),
                                        new KeyValue(imgIconForImageFreezingEffect.rotateProperty(), 0))).play();
                                forAnimationSnowflake1.stop();
                            } else if ((int) myFreezeHeals == 100) {
                                myFreezeHeals--;
                                new Timeline(new KeyFrame(Duration.millis(50),
                                        new KeyValue(imgIconForImageFreezingEffect.layoutYProperty(), 0))).play();
                                forAnimationSnowflake2.stop();
                            }
                        }
                    } else {
                        if (myFreezeHeals >= 20 && imgIconForImageFreezingEffect.getOpacity() == 0) {
                            if (myFreezeHeals >= 500) {
                                imgIconForImageFreezingEffect.setOpacity(0);
                                DataScriptFloby.myFreezeHeals = 0;
                                myHealth = 0;
                            } else if (myFreezeHeals >= 100) {
                                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(50),
                                        new KeyValue(imgIconForImageFreezingEffect.layoutYProperty(), 2)));
                                timeline1.setAutoReverse(true);
                                timeline1.setCycleCount(Timeline.INDEFINITE);
                                timeline1.play();
                            } else if (myFreezeHeals >= 60) {
                                myFreezeHeals++;
                                debafForFreeze = 0.5;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 1))).play();
                                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
                                        new KeyValue(imgIconForImageFreezingEffect.rotateProperty(), 180)));
                                timeline.setAutoReverse(true);
                                timeline.setCycleCount(Timeline.INDEFINITE);
                                timeline.play();
                            } else if (myFreezeHeals >= 40) {
                                debafForFreeze = 0.7;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.5))).play();
                            } else {
                                debafForFreeze = 0.9;
                                new Timeline(new KeyFrame(Duration.millis(100),
                                        new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.2))).play();
                            }
                        }

                        myFreezeHeals += (100 - defFreezeArmor) * isCoolInThisLocation * 0.001;
                        if ((int) myFreezeHeals == 20) {
                            myFreezeHeals++;
                            debafForFreeze = 0.9;
                            new Timeline(new KeyFrame(Duration.millis(100),
                                    new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.2))).play();
                        } else if ((int) myFreezeHeals == 40) {
                            myFreezeHeals++;
                            debafForFreeze = 0.7;
                            new Timeline(new KeyFrame(Duration.millis(100),
                                    new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 0.5))).play();
                        } else if ((int) myFreezeHeals == 60) {
                            myFreezeHeals++;
                            debafForFreeze = 0.5;
                            new Timeline(new KeyFrame(Duration.millis(100),
                                    new KeyValue(imgIconForImageFreezingEffect.opacityProperty(), 1))).play();
                            forAnimationSnowflake1 = new Timeline(new KeyFrame(Duration.millis(500),
                                    new KeyValue(imgIconForImageFreezingEffect.rotateProperty(), 180)));
                            forAnimationSnowflake1.setAutoReverse(true);
                            forAnimationSnowflake1.setCycleCount(Timeline.INDEFINITE);
                            forAnimationSnowflake1.play();
                        } else if ((int) myFreezeHeals == 100) {
                            myFreezeHeals++;
                            forAnimationSnowflake2 = new Timeline(new KeyFrame(Duration.millis(50),
                                    new KeyValue(imgIconForImageFreezingEffect.layoutYProperty(), 2)));
                            forAnimationSnowflake2.setAutoReverse(true);
                            forAnimationSnowflake2.setCycleCount(Timeline.INDEFINITE);
                            forAnimationSnowflake2.play();
                        } else if ((int) myFreezeHeals >= 100 && Math.random() <= 0.1) {
                            myHealth -= 0.05;
                        } else if ((int) myFreezeHeals >= 500) {
                            DataScriptFloby.myFreezeHeals = 0;
                            imgIconForImageFreezingEffect.setOpacity(0);
                            myHealth = 0;
                        }
                    }

                    /*----------------------------------------------------*/

                    /*Установка анимации*/

                    if (isAnimationToPlay && !stopAnimation) {
                        if (onLadder) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/back.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(1);
                            persAnimation.setFitWidth(50 * deltaMainScene);
                            persAnimation.setFitHeight(50 * deltaMainScene);
                            persAnimation.setLayoutX(0);
                            persAnimation.setLayoutY(0);
                        } else if (directionIndicator.equals("right") && nowAction.equals("simpleSwordAttack")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(8 * deltaMainScene);
                                persAnimation.setLayoutY(4 * deltaMainScene);
                                persAnimation.setScaleX(1);
                                persAnimation.setFitWidth(65 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);
                                                persAnimation.setFitWidth(0);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 500);
                            }
                        } else if (directionIndicator.equals("left") && nowAction.equals("simpleSwordAttack")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(-23 * deltaMainScene);
                                persAnimation.setLayoutY(4 * deltaMainScene);
                                persAnimation.setScaleX(-1);
                                persAnimation.setFitWidth(65 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);
                                                persAnimation.setFitWidth(0);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 500);
                            }
                        } else if (isInAir && nowAction.equals("simpleDownSwordAttack") && directionIndicator.equals("right")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/downSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(8 * deltaMainScene);
                                persAnimation.setLayoutY(0);
                                persAnimation.setScaleX(1);
                                persAnimation.setFitWidth(68 * deltaMainScene);
                                persAnimation.setFitHeight(68 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setFitHeight(50 * deltaMainScene);
                                                persAnimation.setFitWidth(50 * deltaMainScene);
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 300);
                            }
                        } else if (isInAir && nowAction.equals("simpleDownSwordAttack") && directionIndicator.equals("left")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/downSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(-23 * deltaMainScene);
                                persAnimation.setLayoutY(0);
                                persAnimation.setScaleX(-1);
                                persAnimation.setFitWidth(68 * deltaMainScene);
                                persAnimation.setFitHeight(68 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);
                                                persAnimation.setFitWidth(50 * deltaMainScene);
                                                persAnimation.setFitHeight(50 * deltaMainScene);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 300);
                            }
                        } else if (nowAction.equals("simpleUpSwordAttack") && directionIndicator.equals("right")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/upSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(8 * deltaMainScene);
                                persAnimation.setLayoutY(-18 * deltaMainScene);
                                persAnimation.setScaleX(1);
                                persAnimation.setFitWidth(68 * deltaMainScene);
                                persAnimation.setFitHeight(68 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setFitHeight(50 * deltaMainScene);
                                                persAnimation.setFitWidth(50 * deltaMainScene);
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 600);
                            }
                        } else if (nowAction.equals("simpleUpSwordAttack") && directionIndicator.equals("left")) {
                            if (!pastAction.equals("simpleSwordAttack") && !pastAction.equals("simpleUpSwordAttack") && !pastAction.equals("simpleDownSwordAttack")) {
                                try {
                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/upSwordAttack.gif")));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                persAnimation.setLayoutX(-23 * deltaMainScene);
                                persAnimation.setLayoutY(-18 * deltaMainScene);
                                persAnimation.setScaleX(-1);
                                persAnimation.setFitWidth(68 * deltaMainScene);
                                persAnimation.setFitHeight(68 * deltaMainScene);

                                isAnimationToPlay = false;

                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j >= 1) {
                                            Platform.runLater(() -> {
                                                isAnimationToPlay = true;
                                                persAnimation.setLayoutX(0);
                                                persAnimation.setLayoutY(0);
                                                persAnimation.setFitWidth(50 * deltaMainScene);
                                                persAnimation.setFitHeight(50 * deltaMainScene);

                                                try {
                                                    persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 600);
                            }
                        } else if (isInAir && directionIndicator.equals("right")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/flyImage.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(1);
                        } else if (isInAir && directionIndicator.equals("left")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/flyImage.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(-1);
                        } else if (directionIndicator.equals("right") && !pastAction.equals("walkRight") && nowAction.equals("walkRight")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(1);
                        } else if (directionIndicator.equals("left") && !pastAction.equals("walkLeft") && nowAction.equals("walkLeft")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simpleWalk.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(-1);
                        } else if (nowAction.equals("stand") && directionIndicator.equals("right")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(1);
                        } else if (nowAction.equals("stand") && directionIndicator.equals("left")) {
                            try {
                                persAnimation.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            persAnimation.setScaleX(-1);
                        }
                    }


                    pastAction = nowAction;
                    nowAction = "stand";

                    /*----------------------------------------------------*/

                    /*Установка звуков*/
                    if (!stopSounds) {
                        if (nowActionForSound.equals("walk") && !pastActionForSound.equals("walk")) {
                            try {
                                if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
                                mediaPlayerForPers = new MediaPlayer(new Media((new File("src/main/sounds/Floby/walk/walk.mp3")).toURI().toURL().toString()));
                                mediaPlayerForPers.setVolume(1);
                                mediaPlayerForPers.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } else if (nowActionForSound.equals("run") && !pastActionForSound.equals("run")) {
                            try {
                                if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
                                mediaPlayerForPers = new MediaPlayer(new Media((new File("src/main/sounds/Floby/walk/run.mp3")).toURI().toURL().toString()));
                                mediaPlayerForPers.setVolume(0.5);
                                mediaPlayerForPers.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } else if (nowActionForSound.equals("swordAttack") && !pastActionForSound.equals("swordAttack")) {
                            try {
                                if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
                                mediaPlayerForPers = new MediaPlayer(new Media((new File("src/main/sounds/Floby/attack/sword/" + ((int) (Math.random() * 2) + 1) + ".mp3")).toURI().toURL().toString()));
                                mediaPlayerForPers.setVolume(1);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,300);
                                mediaPlayerForPers.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (nowActionForSound.equals("jump") && !pastActionForSound.equals("jump")) {
                            try {
                                if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
                                mediaPlayerForPers = new MediaPlayer(new Media((new File("src/main/sounds/Floby/jump/fallDown.mp3")).toURI().toURL().toString()));
                                mediaPlayerForPers.setVolume(0.7);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,170);
                                mediaPlayerForPers.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (nowActionForSound.equals("stand")) {
                            if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
                        }
                    }

                    pastActionForSound = nowActionForSound;
                    nowActionForSound = "stand";
                    /*----------------------------------------------------*/

                }
            }
        };
        persRules.start();
    }

    void wolfRulesPlay(AnchorPane nps) {


        AnimationTimer animationTimerWolf = new AnimationTimer() {
            boolean isFounded = false;
            boolean newDirection = false;
            boolean isWolfAnimation = true;

            String nowActionWolf = "stand";
            String pastActionWolf = "none";
            String directionWolf = (Math.random() >= 0.5) ? "left" : "right";

            boolean goLeft = directionWolf.equals("left");

            int waitAttack = 0;
            int waitAll = 0;

            int npsHeals = 3;

            final ImageView wolfAnimation = ((ImageView) nps.getChildren().get(2));

            MediaPlayer mediaPlayer;
            String nowActionForSound = "stand";
            String pastActionForSound = "stand";
            boolean stopSounds = false;

            @Override
            public void handle(long l) {

                boolean isCollision = pers.getBoundsInParent().intersects(nps.getBoundsInParent());

                if (isFounded) {
                    if (nps.getLayoutX() >= pers.getLayoutX() && directionWolf.equals("left")) {
                        newDirection = true;
                        directionWolf = "right";
                    } else if (nps.getLayoutX() <= pers.getLayoutX() && directionWolf.equals("right")) {
                        newDirection = true;
                        directionWolf = "left";
                    }
                }

                /*Смерть*/

                if (npsHeals <= 0) {
                    try {
                        wolfAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/wolf/dead.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    wolfAnimation.setLayoutX(0);
                    wolfAnimation.setLayoutY(-14 * deltaMainScene);
                    wolfAnimation.setFitHeight(54 * deltaMainScene);
                    wolfAnimation.setFitWidth(60 * deltaMainScene);

                    try {
                        if (mediaPlayer != null) mediaPlayer.stop();
                        mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/wolf/dead.mp3")).toURI().toURL().toString()));
                        mediaPlayer.setVolume(0.5);
                        stopSounds = true;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            int j=0;
                            @Override
                            public void run() {

                                if (j==1){
                                    stopSounds = false;
                                    timer.cancel();
                                    return;
                                }

                                j++;
                            }
                        },0,1500);
                        mediaPlayer.play();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    if (directionWolf.equals("left")) wolfAnimation.setScaleX(1);
                    else wolfAnimation.setScaleX(-1);

                    deadWolf(nps);
                }

                /*----------------------------------------------------*/

                /*Колизия*/
                Rectangle bottomRectColl = new Rectangle();

                boolean fallDown = true;
                boolean leftColl = false;
                boolean rightColl = false;
                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                            (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY())) {
                        fallDown = false;
                        bottomRectColl = rectangle;
                        break;
                    }
                }
                if (fallDown) {
                    nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY()) {
                            nps.setLayoutY(rectangle.getLayoutY() - nps.getHeight());
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() <= 5 * deltaMainScene &&
                                rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() >= 0) {
                            nps.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth());
                            rightColl = true;
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() <= 5 * deltaMainScene &&
                                nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() >= 0) {
                            nps.setLayoutX(rectangle.getLayoutX() - nps.getWidth());
                            leftColl = true;
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() <= 5 * deltaMainScene &&
                                rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() >= 0) {
                            nps.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
                            break;
                        }
                    }
                }

                /*----------------------------------------------------*/

                /*Дефолтная хотьба*/

                if (!isFounded && !fallDown) {
                    if (bottomRectColl.getWidth() >= 200 * deltaMainScene) {
                        nowActionWolf = "walk";
                        if (goLeft) {
                            nps.setLayoutX(nps.getLayoutX() + 2 * deltaMainScene);
                        } else {
                            nps.setLayoutX(nps.getLayoutX() - 2 * deltaMainScene);
                        }
                        if ((goLeft && bottomRectColl.getLayoutX() + bottomRectColl.getWidth() - nps.getLayoutX() - nps.getWidth() <= 10 * deltaMainScene) ||
                                leftColl) {
                            goLeft = false;
                            directionWolf = "right";
                            newDirection = true;
                        } else if ((!goLeft && nps.getLayoutX() - bottomRectColl.getLayoutX() <= 10 * deltaMainScene) || rightColl) {
                            goLeft = true;
                            directionWolf = "left";
                            newDirection = true;
                        }
                    }
                }

                /*----------------------------------------------------*/

                /*Получение урона*/

                if (waitAll == 0) {
                    if (isAttack) {

                        if (attackNPS.equals("right")) {
                            if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                    (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                if ((xAttack <= nps.getLayoutX() && xAttack + deltaXAttack >= nps.getLayoutX()) ||
                                        (xAttack <= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack >= nps.getLayoutX() + nps.getWidth())) {
                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();

                                }
                            }
                        }

                        if (attackNPS.equals("left")) {
                            if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                    (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                if ((xAttack >= nps.getLayoutX() && xAttack + deltaXAttack <= nps.getLayoutX()) ||
                                        (xAttack >= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack <= nps.getLayoutX() + nps.getWidth())) {
                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();
                                }
                            }
                        }

                        if (attackNPS.equals("top")) {
                            if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                    (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                if ((yAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY()) ||
                                        (yAttack >= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {

                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - 10 * deltaMainScene)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 10 * deltaMainScene)));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 50 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();
                                }
                            }
                        }

                        if (attackNPS.equals("down")) {

                            if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                    (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                if ((yAttack + deltaYAttack <= nps.getLayoutY() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack <= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight())) {

                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();

                                    double maxHeightAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutX() > rectangle.getLayoutX() && nps.getLayoutX() < rectangle.getLayoutX() + rectangle.getWidth()) ||
                                                (nps.getLayoutX() + nps.getWidth() > rectangle.getLayoutX() && nps.getLayoutY() + nps.getWidth() < rectangle.getLayoutX() + rectangle.getWidth())) {
                                            if (Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight())) <= maxHeightAnimation) {
                                                maxHeightAnimation = Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight()));
                                            }
                                        }
                                    }

                                    cameraWait = 30;
                                    Timeline timeline22 = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - maxHeightAnimation)));

                                    timeline22.play();

                                }
                            }
                        }

                    }
                } else {
                    if (waitAll > 0) waitAll--;
                }

                /*Движение*/

                if (/*!isCollision &&*/ waitAll == 0) {

                    double distance = Math.sqrt(Math.pow((nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2), 2) +
                            Math.pow((nps.getLayoutY() + nps.getHeight() / 2 - pers.getLayoutY() - pers.getHeight() / 2), 2));
                    double deltaXDistance = Math.abs(nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2);
                    double a = Math.acos(deltaXDistance / distance);

                        /*if (!fallDown) {
                            if ((pers.getLayoutX() + 20 <= bottomRectColl.getLayoutX() + bottomRectColl.getWidth() && pers.getLayoutX() - 20 >= bottomRectColl.getLayoutX()) ||
                                    (pers.getLayoutX() + pers.getWidth() + 20 <= bottomRectColl.getLayoutX() + bottomRectColl.getWidth() && pers.getLayoutX() + pers.getWidth() - 20 >= bottomRectColl.getLayoutX())) {
                                distance=1000;
                                a=1;
                            }
                        }*/

                    if (a <= Math.PI / 3 && deltaXDistance <= 300 * deltaMainScene && isFounded) {
                        if (Math.abs(pers.getLayoutX() - nps.getLayoutX()) >= 20 * deltaMainScene) {
                            nowActionWolf = "walk";
                            fallDown = true;
                            for (Rectangle rectangle : allGround) {
                                if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {

                                    if (pers.getLayoutX() > nps.getLayoutX()) {
                                        nps.setLayoutX(nps.getLayoutX() + 4 * deltaMainScene);
                                    } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                        nps.setLayoutX(nps.getLayoutX() - 4 * deltaMainScene);
                                    }

                                    fallDown = false;
                                    break;
                                }
                            }
                            if (fallDown) {
                                nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                            }
                        } else {
                            isFounded = false;
                        }
                    } else if ((a <= Math.PI / 6 && deltaXDistance <= 300 * deltaMainScene &&
                            ((directionWolf.equals("left") && nps.getLayoutX() < pers.getLayoutX()) ||
                                    (directionWolf.equals("right") && nps.getLayoutX() > pers.getLayoutX()))) ||
                            distance <= 100 * deltaMainScene) {

                        fallDown = true;
                        for (Rectangle rectangle : allGround) {
                            if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {

                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                    nps.setLayoutX(nps.getLayoutX() + 4 * deltaMainScene);
                                } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                    nps.setLayoutX(nps.getLayoutX() - 4 * deltaMainScene);
                                }

                                if (nps.getLayoutX() >= pers.getLayoutX()) {
                                    newDirection = true;
                                    directionWolf = "right";
                                } else {
                                    newDirection = true;
                                    directionWolf = "left";
                                }

                                isFounded = true;

                                fallDown = false;
                                break;
                            }
                        }
                        if (fallDown) {
                            nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                        }
                    } else {
                        isFounded = false;
                    }
                }

                /*----------------------------------------------------*/

                /*Удар*/

                if (isCollision && waitAttack == 0) {
                    if ((directionWolf.equals("left") && nps.getLayoutX() + nps.getWidth() >= pers.getLayoutX() + 10 * deltaMainScene) ||
                            (directionWolf.equals("right") && nps.getLayoutX() <= pers.getLayoutX() + pers.getWidth() - 10 * deltaMainScene)) {
                        nowActionForSound = "attack";
                        nowActionWolf = "attack";
                        myHealth -= 1;
                        cameraWait = 30;
                        //waitAll = 30;
                        waitAttack = 30;

                        Timer timer = new Timer();

                        timer.schedule(new TimerTask() {
                            int j = 0;

                            @Override
                            public void run() {
                                if (j == 1) {
                                    Platform.runLater(() -> {

                                        if (pers.getLayoutX() > nps.getLayoutX()) {
                                            double maxWidthAnimation = 50 * deltaMainScene;
                                            for (Rectangle rectangle : allGround) {
                                                if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                        (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                    if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                        maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                    }
                                                }
                                            }
                                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                    new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                            timeline1.setOnFinished(e -> timeline2.play());
                                            timeline1.play();
                                            timeline.play();
                                        } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                            double maxWidthAnimation = 50 * deltaMainScene;
                                            for (Rectangle rectangle : allGround) {
                                                if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                        (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                    if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                        maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                    }
                                                }
                                            }
                                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                    new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                            timeline1.setOnFinished(e -> timeline2.play());
                                            timeline1.play();
                                            timeline.play();
                                        }
                                    });

                                    timer.cancel();
                                    return;
                                }
                                j++;
                            }
                        }, 0, 200);
                    }
                }
                else {
                    if (waitAttack>0) waitAttack--;
                }


                /*Анимация*/

                if (isWolfAnimation){
                    if (nowActionWolf.equals("walk") && (!pastActionWolf.equals("walk") || newDirection) && isFounded) {
                        try {
                            wolfAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/wolf/run.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        wolfAnimation.setLayoutX(0);
                        wolfAnimation.setLayoutY(4 * deltaMainScene);
                        wolfAnimation.setFitHeight(36 * deltaMainScene);
                        wolfAnimation.setFitWidth(60 * deltaMainScene);
                        if (directionWolf.equals("left")) wolfAnimation.setScaleX(1);
                        else wolfAnimation.setScaleX(-1);
                    }
                    else if (nowActionWolf.equals("walk") && (!pastActionWolf.equals("walk") || newDirection)){

                        try {
                            wolfAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/wolf/walk.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        wolfAnimation.setLayoutX(0);
                        wolfAnimation.setLayoutY(4 * deltaMainScene);
                        wolfAnimation.setFitHeight(36 * deltaMainScene);
                        wolfAnimation.setFitWidth(60 * deltaMainScene);

                        if (directionWolf.equals("left")) wolfAnimation.setScaleX(1);
                        else wolfAnimation.setScaleX(-1);
                    }
                    else if (nowActionWolf.equals("attack") && (!pastActionWolf.equals("attack") || newDirection)){
                        try {
                            wolfAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/wolf/attack.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        wolfAnimation.setLayoutX(0);
                        wolfAnimation.setLayoutY(-20 * deltaMainScene);
                        wolfAnimation.setFitHeight(60 * deltaMainScene);
                        wolfAnimation.setFitWidth(60 * deltaMainScene);
                        if (directionWolf.equals("left")) wolfAnimation.setScaleX(1);
                        else wolfAnimation.setScaleX(-1);
                        isWolfAnimation = false;

                        Timer timer = new Timer();

                        timer.schedule(new TimerTask() {
                            int j = 0;

                            @Override
                            public void run() {
                                if (j >= 1) {
                                    isWolfAnimation = true;
                                    timer.cancel();
                                    return;
                                }
                                j++;
                            }
                        }, 0, 600);
                    }
                    else if (nowActionWolf.equals("stand") && (!pastActionWolf.equals("stand") || newDirection)){
                        try {
                            wolfAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/wolf/default.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        wolfAnimation.setLayoutX(0);
                        wolfAnimation.setLayoutY(4 * deltaMainScene);
                        wolfAnimation.setFitHeight(36 * deltaMainScene);
                        wolfAnimation.setFitWidth(60 * deltaMainScene);
                        if (directionWolf.equals("left")) wolfAnimation.setScaleX(1);
                        else wolfAnimation.setScaleX(-1);
                    }

                    newDirection = false;
                }

                pastActionWolf = nowActionWolf;
                nowActionWolf = "stand";

                /*Установка звуков*/
                if (!stopSounds) {
                    if (nowActionForSound.equals("attack") && !pastActionForSound.equals("attack")) {
                        try {
                            if (mediaPlayer != null) mediaPlayer.stop();
                            mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/wolf/attack"+((int) (Math.random() * 2) + 1)+".mp3")).toURI().toURL().toString()));
                            mediaPlayer.setVolume(1);
                            stopSounds = true;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                int j=0;
                                @Override
                                public void run() {

                                    if (j==1){
                                        stopSounds = false;
                                        timer.cancel();
                                        return;
                                    }

                                    j++;
                                }
                            },0,1500);
                            mediaPlayer.play();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (nowActionForSound.equals("stand")) {
                        if (mediaPlayer != null) mediaPlayer.stop();
                    }
                }

                pastActionForSound = nowActionForSound;
                nowActionForSound = "stand";

            }
        };

        animationTimerWolf.start();
        npsRules.add(animationTimerWolf);

    }

    void deadWolf(AnchorPane nps) {
        npsRules.get(Integer.parseInt(((Label) nps.getChildren().get(1)).getText())).stop();
        myEXPNow += 5;
    }

    void birdRulesPlay (AnchorPane nps) {

        AnimationTimer bird = new AnimationTimer() {
            boolean isFounded = false;
            boolean newDirection = false;

            String directionBird = (Math.random() >= 0.5) ? "left" : "right";
            String nowActionBird = "stand";
            String pastActionBird = "none";

            int waitAttack = 0;
            int waitAll = 0;

            int npsHeals = 2;

            final ImageView birdAnimation = ((ImageView) nps.getChildren().get(2));

            private MediaPlayer mediaPlayer;
            String nowActionForSound = "fly";
            String pastActionForSound = "fly";
            boolean stopSounds = false;

            @Override
            public void handle(long now) {

                boolean isCollision = nps.getBoundsInParent().intersects(pers.getBoundsInParent());

                /*Колизия*/

                boolean fallDown = true;

                if (npsHeals <= 0) {

                    nowActionBird = "fall";

                    if (!pastActionBird.equals("fall")) {
                        birdAnimation.setLayoutX(0);
                        birdAnimation.setLayoutY(0);
                        birdAnimation.setFitWidth(50 * deltaMainScene);
                        birdAnimation.setFitHeight(50 * deltaMainScene);
                        try {
                            birdAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bird/dead.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (directionBird.equals("left")) {
                            birdAnimation.setScaleX(1);
                        } else {
                            birdAnimation.setScaleX(-1);
                        }
                    }

                    pastActionBird = nowActionBird;

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                                (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY())) {
                            fallDown = false;
                            break;
                        }
                    }

                    if (fallDown) {
                        nps.setLayoutY(nps.getLayoutY() + 5 * deltaMainScene);
                    }
                    else {

                        birdAnimation.setLayoutX(0);
                        birdAnimation.setLayoutY(20 * deltaMainScene);
                        birdAnimation.setFitWidth(50 * deltaMainScene);
                        birdAnimation.setFitHeight(50 * deltaMainScene);
                        try {
                            birdAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bird/dead.png")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (directionBird.equals("left")) {
                            birdAnimation.setScaleX(1);
                        } else {
                            birdAnimation.setScaleX(-1);
                        }

                        deadBird(nps);
                    }

                } else {

                    if (isFounded) {
                        if (nps.getLayoutX() >= pers.getLayoutX() + 10 * deltaMainScene && directionBird.equals("left")) {
                            newDirection = true;
                            directionBird = "right";
                        } else if (nps.getLayoutX() <= pers.getLayoutX() - 10 * deltaMainScene && directionBird.equals("right")) {
                            newDirection = true;
                            directionBird = "left";
                        }
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY()) {
                                nps.setLayoutY(rectangle.getLayoutY() - nps.getHeight());
                                break;
                            }
                        }
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() <= 5 * deltaMainScene &&
                                    rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() >= 0) {
                                nps.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth());
                                break;
                            }
                        }
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() <= 5 * deltaMainScene &&
                                    nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() >= 0) {
                                nps.setLayoutX(rectangle.getLayoutX() - nps.getWidth());
                                break;
                            }
                        }
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() <= 5 * deltaMainScene &&
                                    rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() >= 0) {
                                nps.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
                                break;
                            }
                        }
                    }
                    double distance = 0;
                    /*Обнаружение игрока*/
                    if (!isCollision) {
                        distance = Math.sqrt(Math.pow(pers.getLayoutX() + pers.getWidth() / 2 - nps.getLayoutX() - nps.getWidth() / 2, 2) +
                                Math.pow(pers.getLayoutY() + pers.getHeight() / 2 - nps.getLayoutY() - nps.getHeight() / 2, 2));
                        double deltaXDistance = Math.abs(nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2) ;
                        double a = Math.acos(deltaXDistance / distance);
                        if (distance <= 400 * deltaMainScene && isFounded) {

                            if (pers.getLayoutX() > nps.getLayoutX()) {
                                nps.setLayoutX(nps.getLayoutX() + 3 * deltaMainScene);
                            } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                nps.setLayoutX(nps.getLayoutX() - 3 * deltaMainScene);
                            }
                            if (pers.getLayoutY() > nps.getLayoutY() + 25 * deltaMainScene) {
                                nps.setLayoutY(nps.getLayoutY() + 2 * deltaMainScene);
                            } else if (pers.getLayoutY() < nps.getLayoutY()) {
                                nps.setLayoutY(nps.getLayoutY() - 2 * deltaMainScene);
                            }
                        } else if ((a <= Math.PI / 3 && deltaXDistance <= 300 * deltaMainScene &&
                                ((directionBird.equals("left") && nps.getLayoutX() < pers.getLayoutX()) ||
                                        (directionBird.equals("right") && nps.getLayoutX() > pers.getLayoutX()))) ||
                                distance <= 100 * deltaMainScene) {
                            if (pers.getLayoutX() > nps.getLayoutX()) {
                                nps.setLayoutX(nps.getLayoutX() + 3 * deltaMainScene);
                            } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                nps.setLayoutX(nps.getLayoutX() - 3 * deltaMainScene);
                            }
                            if (pers.getLayoutY() > nps.getLayoutY()) {
                                nps.setLayoutY(nps.getLayoutY() + 2 * deltaMainScene);
                            } else if (pers.getLayoutY() < nps.getLayoutY()) {
                                nps.setLayoutY(nps.getLayoutY() - 2 * deltaMainScene);
                            }
                            if (nps.getLayoutX() >= pers.getLayoutX()) {
                                newDirection = true;
                                directionBird = "right";
                            } else {
                                newDirection = true;
                                directionBird = "left";
                            }
                            isFounded = true;
                        } else {
                            isFounded = false;
                        }
                    }

                    /*Атака врага*/
                    if (isCollision && waitAttack == 0) {
                        waitAttack = 30;
                        myHealth--;
                        cameraWait = 30;
                        nowActionForSound = "attack";

                        Timer timer = new Timer();

                        timer.schedule(new TimerTask() {
                            int j = 0;

                            @Override
                            public void run() {
                                if (j == 1) {
                                    Platform.runLater(() -> {

                                        if (pers.getLayoutX() > nps.getLayoutX()) {
                                            double maxWidthAnimation = 50 * deltaMainScene;
                                            for (Rectangle rectangle : allGround) {
                                                if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                        (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                    if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                        maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                    }
                                                }
                                            }
                                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                    new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                            timeline1.setOnFinished(e -> timeline2.play());
                                            timeline1.play();
                                            timeline.play();
                                        } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                            double maxWidthAnimation = 50 * deltaMainScene;
                                            for (Rectangle rectangle : allGround) {
                                                if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                        (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                    if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                        maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                    }
                                                }
                                            }
                                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                    new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                    new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                            timeline1.setOnFinished(e -> timeline2.play());
                                            timeline1.play();
                                            timeline.play();
                                        }
                                    });

                                    timer.cancel();
                                    return;
                                }
                                j++;
                            }
                        }, 0, 200);
                    } else {
                        if (waitAttack <= 0) waitAttack = 0;
                        else waitAttack--;
                    }

                    /*Получение урона*/
                    if (waitAll == 0) {
                        if (isAttack) {
                            if (attackNPS.equals("right")) {
                                if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                    if ((xAttack <= nps.getLayoutX() && xAttack + deltaXAttack >= nps.getLayoutX()) ||
                                            (xAttack <= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack >= nps.getLayoutX() + nps.getWidth())) {
                                        npsHeals--;
                                        waitAll = 30;

                                        double maxWidthAnimation = 50 * deltaMainScene;
                                        for (Rectangle rectangle : allGround) {
                                            if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                    (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                    maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                                }
                                            }
                                        }
                                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)),
                                                new KeyFrame(Duration.millis(100),
                                                        new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                        timeline.play();

                                    }
                                }
                            }

                            if (attackNPS.equals("left")) {
                                if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                    if ((xAttack >= nps.getLayoutX() && xAttack + deltaXAttack <= nps.getLayoutX()) ||
                                            (xAttack >= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack <= nps.getLayoutX() + nps.getWidth())) {
                                        npsHeals--;
                                        waitAll = 30;
                                        double maxWidthAnimation = 50 * deltaMainScene;
                                        for (Rectangle rectangle : allGround) {
                                            if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                    (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                    maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                }
                                            }
                                        }
                                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - maxWidthAnimation)),
                                                new KeyFrame(Duration.millis(100),
                                                        new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                        timeline.play();
                                    }
                                }
                            }

                            if (attackNPS.equals("top")) {
                                if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                        (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                    if ((yAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY()) ||
                                            (yAttack >= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {

                                        npsHeals--;
                                        waitAll = 30;
                                        double maxWidthAnimation = 50 * deltaMainScene;
                                        for (Rectangle rectangle : allGround) {
                                            if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                    (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                    maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                }
                                            }
                                        }
                                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - 10 * deltaMainScene)),
                                                new KeyFrame(Duration.millis(100),
                                                        new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 50 * deltaMainScene)));
                                        timeline.play();
                                    }
                                }
                            }

                            if (attackNPS.equals("down")) {

                                if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                        (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                    if ((yAttack + deltaYAttack <= nps.getLayoutY() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight()) ||
                                            (yAttack <= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight())) {

                                        npsHeals--;
                                        waitAll = 30;
                                        double maxWidthAnimation = 50 * deltaMainScene;
                                        for (Rectangle rectangle : allGround) {
                                            if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                    (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                    maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                                }
                                            }
                                        }
                                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)),
                                                new KeyFrame(Duration.millis(100),
                                                        new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                        ;
                                        timeline.play();

                                        double maxHeightAnimation = 50 * deltaMainScene;
                                        for (Rectangle rectangle : allGround) {
                                            if ((nps.getLayoutX() > rectangle.getLayoutX() && nps.getLayoutX() < rectangle.getLayoutX() + rectangle.getWidth()) ||
                                                    (nps.getLayoutX() + nps.getWidth() > rectangle.getLayoutX() && nps.getLayoutY() + nps.getWidth() < rectangle.getLayoutX() + rectangle.getWidth())) {
                                                if (Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight())) <= maxHeightAnimation) {
                                                    maxHeightAnimation = Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight()));
                                                }
                                            }
                                        }

                                        cameraWait = 30;
                                        Timeline timeline22 = new Timeline(new KeyFrame(Duration.millis(200),
                                                new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - maxHeightAnimation)));

                                        timeline22.play();

                                    }
                                }
                            }

                        }
                    } else {
                        if (waitAll <= 0) waitAll = 0;
                        else waitAll--;
                    }

                    /*Анимации*/
                    if (nowActionBird.equals("stand") && (!pastActionBird.equals("stand") || newDirection)){
                        birdAnimation.setLayoutX(0);
                        birdAnimation.setLayoutY(0);
                        birdAnimation.setFitWidth(50 * deltaMainScene);
                        birdAnimation.setFitHeight(50 * deltaMainScene);
                        try {
                            birdAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bird/stand.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (directionBird.equals("left")){
                            birdAnimation.setScaleX(1);
                        }
                        else {
                            birdAnimation.setScaleX(-1);
                        }
                    }

                    pastActionBird = nowActionBird;
                    nowActionBird = "stand";
                    newDirection = false;

                    /*Установка звуков*/
                    if (!stopSounds || (nowActionForSound.equals("attack") && !pastActionForSound.equals("attack"))) {
                        if (nowActionForSound.equals("attack") && !pastActionForSound.equals("attack")) {
                            try {
                                if (mediaPlayer != null) mediaPlayer.stop();
                                mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bird/attack.mp3")).toURI().toURL().toString()));
                                mediaPlayer.setVolume(0.5);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,500);
                                mediaPlayer.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (nowActionForSound.equals("fly")) {
                            if (distance <= 500) {
                                try {
                                    if (mediaPlayer != null) mediaPlayer.stop();
                                    mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bird/fly.mp3")).toURI().toURL().toString()));
                                    mediaPlayer.setVolume(0.5);
                                    stopSounds = true;
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        int j=0;
                                        @Override
                                        public void run() {

                                            if (j==1){
                                                stopSounds = false;
                                                timer.cancel();
                                                return;
                                            }

                                            j++;
                                        }
                                    },0,7000);
                                    mediaPlayer.play();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                if (mediaPlayer != null) mediaPlayer.stop();
                            }
                        }
                    }

                    pastActionForSound = nowActionForSound;
                    nowActionForSound = "fly";
                }

            }
        };

        bird.start();
        npsRules.add(bird);
    }

    void deadBird(AnchorPane nps) {
        npsRules.get(Integer.parseInt(((Label) nps.getChildren().get(1)).getText())).stop();
        myEXPNow += 5;
    }

    void boarRulesPlay(AnchorPane nps){

        AnimationTimer animationTimerBoar = new AnimationTimer() {
            boolean isFounded = false;
            boolean newDirection = false;

            double lengthRun = 500 * deltaMainScene;

            String nowActionBoar = "stand";
            String pastActionBoar = "none";
            String directionBoar = (Math.random() >= 0.5) ? "left" : "right";
            String savedDirection = "none";

            int waitAttack = 0;
            int waitAll = 0;

            int npsHeals = 5;

            boolean goLeft = directionBoar.equals("left");

            final ImageView boarAnimation = ((ImageView) nps.getChildren().get(2));

            MediaPlayer mediaPlayer;
            String nowActionForSound = "stand";
            String pastActionForSound = "stand";
            boolean stopSounds = false;

            @Override
            public void handle(long l) {

                if (isFounded) {
                    if (nps.getLayoutX() >= pers.getLayoutX() && directionBoar.equals("left")) {
                        newDirection = true;
                        directionBoar = "right";
                    } else if (nps.getLayoutX() <= pers.getLayoutX() && directionBoar.equals("right")) {
                        newDirection = true;
                        directionBoar = "left";
                    }
                }

                /*Смерть*/

                if (npsHeals <= 0) {
                    try {
                        boarAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/boar/dead.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    boarAnimation.setFitHeight(30 * deltaMainScene);
                    boarAnimation.setFitWidth(60 * deltaMainScene);
                    boarAnimation.setLayoutX(0);
                    boarAnimation.setLayoutY(8 * deltaMainScene);

                    if (directionBoar.equals("left")){
                        boarAnimation.setScaleX(1);
                    }
                    else {
                        boarAnimation.setScaleX(-1);
                    }

                    deadBoar(nps);
                }

                /*----------------------------------------------------*/

                /*Колизия*/

                Rectangle bottomRectColl = new Rectangle();

                boolean fallDown = true;
                boolean leftColl = false;
                boolean rightColl = false;
                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                            (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY())) {
                        fallDown = false;
                        bottomRectColl = rectangle;
                        break;
                    }
                }
                if (fallDown) {
                    nps.setLayoutY(nps.getLayoutY() + 7);
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY()) {
                            nps.setLayoutY(rectangle.getLayoutY() - nps.getHeight());
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() <= 8 * deltaMainScene &&
                                rectangle.getLayoutX() + rectangle.getWidth() - nps.getLayoutX() >= 0) {
                            nps.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth());
                            rightColl = true;
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() <= 8 * deltaMainScene &&
                                nps.getLayoutX() + nps.getWidth() - rectangle.getLayoutX() >= 0) {
                            nps.setLayoutX(rectangle.getLayoutX() - nps.getWidth());
                            leftColl = true;
                            break;
                        }
                    }
                }

                for (Rectangle rectangle : allGround) {
                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        if (rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() <= 5 * deltaMainScene &&
                                rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() >= 0) {
                            nps.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
                            break;
                        }
                    }
                }

                /*----------------------------------------------------*/

                /*Дефолтная хотьба*/

                if (!isFounded && !fallDown) {
                    if (bottomRectColl.getWidth() >= 200 * deltaMainScene) {
                        nowActionBoar = "walk";
                        if (goLeft) {
                            nps.setLayoutX(nps.getLayoutX() + 2 * deltaMainScene);
                        } else {
                            nps.setLayoutX(nps.getLayoutX() - 2 * deltaMainScene);
                        }
                        if ((goLeft && bottomRectColl.getLayoutX() + bottomRectColl.getWidth() - nps.getLayoutX() - nps.getWidth() <= 10 * deltaMainScene) ||
                                leftColl) {
                            goLeft = false;
                            directionBoar = "right";
                            newDirection = true;
                        } else if ((!goLeft && nps.getLayoutX() - bottomRectColl.getLayoutX() <= 10 * deltaMainScene) || rightColl) {
                            goLeft = true;
                            directionBoar = "left";
                            newDirection = true;
                        }
                    }
                }

                /*----------------------------------------------------*/

                /*Получение урона*/

                if (waitAll == 0) {
                    if (isAttack) {

                        if (attackNPS.equals("right")) {
                            if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                    (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                if ((xAttack <= nps.getLayoutX() && xAttack + deltaXAttack >= nps.getLayoutX()) ||
                                        (xAttack <= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack >= nps.getLayoutX() + nps.getWidth())) {
                                    npsHeals--;
                                    waitAll = 30;
                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() +
                                                        rectangle.getHeight())) {
                                            if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();

                                }
                            }
                        }

                        if (attackNPS.equals("left")) {
                            if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                    (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                if ((xAttack >= nps.getLayoutX() && xAttack + deltaXAttack <= nps.getLayoutX()) ||
                                        (xAttack >= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack <= nps.getLayoutX() + nps.getWidth())) {
                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();
                                }
                            }
                        }

                        if (attackNPS.equals("top")) {
                            if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                    (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                if ((yAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY()) ||
                                        (yAttack >= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {

                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(nps.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() - 10 * deltaMainScene)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 10 * deltaMainScene)));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 50 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();
                                }
                            }
                        }

                        if (attackNPS.equals("down")) {

                            if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                    (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                if ((yAttack + deltaYAttack <= nps.getLayoutY() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack <= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight())) {

                                    npsHeals--;
                                    waitAll = 30;

                                    double maxWidthAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutY() > rectangle.getLayoutY() && nps.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                (nps.getLayoutY() + nps.getHeight() > rectangle.getLayoutY() && nps.getLayoutY() + nps.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                            if (Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth())) <= maxWidthAnimation) {
                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (nps.getLayoutX() + nps.getWidth()));
                                            }
                                        }
                                    }
                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(nps.layoutXProperty(), nps.getLayoutX() + maxWidthAnimation)));
                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY())));
                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                            new KeyValue(nps.layoutYProperty(), nps.getLayoutY() - 15 * deltaMainScene)));
                                    timeline1.setOnFinished(e -> timeline2.play());
                                    timeline1.play();
                                    timeline.play();

                                    double maxHeightAnimation = 50 * deltaMainScene;
                                    for (Rectangle rectangle : allGround) {
                                        if ((nps.getLayoutX() > rectangle.getLayoutX() && nps.getLayoutX() < rectangle.getLayoutX() + rectangle.getWidth()) ||
                                                (nps.getLayoutX() + nps.getWidth() > rectangle.getLayoutX() && nps.getLayoutY() + nps.getWidth() < rectangle.getLayoutX() + rectangle.getWidth())) {
                                            if (Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight())) <= maxHeightAnimation) {
                                                maxHeightAnimation = Math.abs(nps.getLayoutY() - (rectangle.getLayoutY() + rectangle.getHeight()));
                                            }
                                        }
                                    }

                                    cameraWait = 30;
                                    Timeline timeline22 = new Timeline(new KeyFrame(Duration.millis(200),
                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - maxHeightAnimation)));

                                    timeline22.play();

                                }
                            }
                        }

                    }
                } else {
                    waitAll--;
                }

                /*Движение*/
                    double distance = Math.sqrt(Math.pow((nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2), 2) +
                            Math.pow((nps.getLayoutY() + nps.getHeight() / 2 - pers.getLayoutY() - pers.getHeight() / 2), 2));
                    double deltaXDistance = Math.abs(nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2);
                    double a = Math.acos(deltaXDistance / distance);


                    if (isFounded) {
                        nowActionBoar = "attack";
                        nowActionForSound = "attack";
                        fallDown = true;
                        for (Rectangle rectangle : allGround) {
                            if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                                fallDown = false;
                                break;
                            }
                        }
                        if (fallDown) {
                            nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                        } else {
                            if (savedDirection.equals("left")) {
                                nps.setLayoutX(nps.getLayoutX() + 5 * deltaMainScene);
                            } else {
                                nps.setLayoutX(nps.getLayoutX() - 5 * deltaMainScene);
                            }

                            lengthRun -= 5;
                        }

                        if (lengthRun <= 0 || leftColl || rightColl) {
                            lengthRun = 500 * deltaMainScene;
                            savedDirection = "none";
                            isFounded = false;
                        }
                    } else if ((a <= Math.PI / 6 && deltaXDistance <= 300 * deltaMainScene &&
                            ((directionBoar.equals("left") && nps.getLayoutX() < pers.getLayoutX()) ||
                                    (directionBoar.equals("right") && nps.getLayoutX() > pers.getLayoutX()))) ||
                            distance <= 100 * deltaMainScene) {

                        fallDown = true;
                        for (Rectangle rectangle : allGround) {
                            if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {

                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                    nps.setLayoutX(nps.getLayoutX() + 5 * deltaMainScene);
                                } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                    nps.setLayoutX(nps.getLayoutX() - 5 * deltaMainScene);
                                }

                                if (nps.getLayoutX() >= pers.getLayoutX()) {
                                    newDirection = true;
                                    directionBoar = "right";
                                } else {
                                    newDirection = true;
                                    directionBoar = "left";
                                }

                                isFounded = true;

                                savedDirection = directionBoar;

                                fallDown = false;
                                break;
                            }
                        }
                        if (fallDown) {
                            nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                        }
                    }

                /*Удар*/
                if (waitAttack == 0) {
                    if (pers.getBoundsInParent().intersects(nps.getBoundsInParent())) {
                        if ((directionBoar.equals("left") && nps.getLayoutX() + nps.getWidth() >= pers.getLayoutX() + 10 * deltaMainScene) ||
                                (directionBoar.equals("right") && nps.getLayoutX() <= pers.getLayoutX() + pers.getWidth() - 10 * deltaMainScene)) {
                            nowActionBoar = "attack";
                            myHealth -= 1;
                            cameraWait = 30;
                            waitAttack = 30;
                            waitAll = 30;

                            Timer timer = new Timer();

                            timer.schedule(new TimerTask() {
                                int j = 0;

                                @Override
                                public void run() {
                                    if (j == 1) {
                                        Platform.runLater(() -> {

                                            if (pers.getLayoutX() > nps.getLayoutX()) {
                                                double maxWidthAnimation = 50 * deltaMainScene;
                                                for (Rectangle rectangle : allGround) {
                                                    if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                            (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                        if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                            maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                        }
                                                    }
                                                }
                                                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                        new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                                Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                        new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                        new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                timeline1.setOnFinished(e -> timeline2.play());
                                                timeline1.play();
                                                timeline.play();
                                            } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                                double maxWidthAnimation = 50 * deltaMainScene;
                                                for (Rectangle rectangle : allGround) {
                                                    if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                            (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                        if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                            maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                        }
                                                    }
                                                }
                                                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                        new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                                Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                        new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                        new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                timeline1.setOnFinished(e -> timeline2.play());
                                                timeline1.play();
                                                timeline.play();
                                            }
                                        });

                                        timer.cancel();
                                        return;
                                    }
                                    j++;
                                }
                            }, 0, 200);
                        }
                    }
                } else {
                    waitAttack--;
                }


                /*Анимация*/

                if (nowActionBoar.equals("walk") && (!pastActionBoar.equals("walk") || newDirection)){

                    try {
                        boarAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/boar/walk.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    boarAnimation.setFitHeight(34 * deltaMainScene);
                    boarAnimation.setFitWidth(60 * deltaMainScene);
                    boarAnimation.setLayoutX(0);
                    boarAnimation.setLayoutY(2 * deltaMainScene);

                    if (directionBoar.equals("left")){
                        boarAnimation.setScaleX(1);
                    }
                    else {
                        boarAnimation.setScaleX(-1);
                    }
                }
                else if (nowActionBoar.equals("attack") && (!pastActionBoar.equals("attack") || newDirection)) {
                    try {
                        boarAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/boar/attack.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    boarAnimation.setFitHeight(34 * deltaMainScene);
                    boarAnimation.setFitWidth(60 * deltaMainScene);
                    boarAnimation.setLayoutX(0);
                    boarAnimation.setLayoutY(2 * deltaMainScene);

                    if (directionBoar.equals("left")){
                        boarAnimation.setScaleX(1);
                    }
                    else {
                        boarAnimation.setScaleX(-1);
                    }
                }
                else if (nowActionBoar.equals("stand") && (!pastActionBoar.equals("stand") || newDirection)){
                    try {
                        boarAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/boar/stand.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    boarAnimation.setFitHeight(34 * deltaMainScene);
                    boarAnimation.setFitWidth(60 * deltaMainScene);
                    boarAnimation.setLayoutX(0);
                    boarAnimation.setLayoutY(2 * deltaMainScene);

                    if (directionBoar.equals("left")){
                        boarAnimation.setScaleX(1);
                    }
                    else {
                        boarAnimation.setScaleX(-1);
                    }
                }

                pastActionBoar = nowActionBoar;
                nowActionBoar = "stand";
                if (newDirection) newDirection = false;

                /*Установка звуков*/
                if (!stopSounds || (nowActionForSound.equals("attack") && !pastActionForSound.equals("attack"))) {
                    if (nowActionForSound.equals("attack") && !pastActionForSound.equals("attack")) {
                        try {
                            if (mediaPlayer != null) mediaPlayer.stop();
                            mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/boar/attack.mp3")).toURI().toURL().toString()));
                            mediaPlayer.setVolume(0.5);
                            stopSounds = true;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                int j=0;
                                @Override
                                public void run() {

                                    if (j==1){
                                        stopSounds = false;
                                        timer.cancel();
                                        return;
                                    }

                                    j++;
                                }
                            },0,3000);
                            mediaPlayer.play();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (nowActionForSound.equals("stand")) {
                        if (mediaPlayer != null) mediaPlayer.stop();
                    }
                }

                pastActionForSound = nowActionForSound;
                nowActionForSound = "stand";

            }
        };

        animationTimerBoar.start();
        npsRules.add(animationTimerBoar);

    }

    void deadBoar(AnchorPane nps){
        npsRules.get(Integer.parseInt(((Label) nps.getChildren().get(1)).getText())).stop();
        myEXPNow += 5;
    }

    void bearRulesPlay(AnchorPane nps){
        AnimationTimer animationTimerBear = new AnimationTimer() {
            boolean isFounded = false;
            boolean newDirection = false;
            boolean isBearAnimation = true;

            String nowActionBear = "stand";
            String pastActionBear = "none";
            String directionBear = "right";

            int waitAttack = 0;
            int waitAll = 0;

            int npsHeals = 10;
            final double maxHeals = npsHeals;

            final ImageView bearAnimation = ((ImageView) nps.getChildren().get(2));

            int typeRound = 1;

            int numAttack = 1;

            boolean blockChooseCamera = false;
            boolean finalRound = false;
            boolean isFinalRound = false;
            boolean isCreateNewGround = false;

            Rectangle rectangle;

            MediaPlayer mediaPlayer;
            String nowActionForSound = "stand";
            String pastActionForSound = "stand";
            boolean stopSounds = false;

            @Override
            public void handle(long l) {

                boolean isCollision = pers.getBoundsInParent().intersects(nps.getBoundsInParent());

                if (!isCreateNewGround){

                    isCreateNewGround=true;

                    rectangle = new Rectangle();
                    rectangle.setOpacity(0);
                    rectangle.setLayoutY(indicatorRegionForCameraBottomRight1.getLayoutY() - 308 * deltaMainScene);
                    rectangle.setLayoutX(indicatorRegionForCameraBottomRight1.getLayoutX());
                    rectangle.setWidth(20 * deltaMainScene);
                    rectangle.setHeight(200 * deltaMainScene);

                    allGround.add(rectangle);

                }

                if (npsHeals / maxHeals >= 0.8) typeRound = 1;
                else if (npsHeals / maxHeals >= 0.5) typeRound = 2;
                else if (npsHeals / maxHeals >= 0.2) typeRound = 3;

                if (isFounded) {
                    if (nps.getLayoutX() >= pers.getLayoutX() && directionBear.equals("left")) {
                        newDirection = true;
                        directionBear = "right";
                    } else if (nps.getLayoutX() <= pers.getLayoutX() && directionBear.equals("right")) {
                        newDirection = true;
                        directionBear = "left";
                    }
                }

                if (npsHeals == 1 && !isFinalRound){
                    typeCamera = 0;
                    blockChooseCamera=true;

                    try {
                        if (mediaPlayer != null) mediaPlayer.stop();
                        mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bear/run.mp3")).toURI().toURL().toString()));
                        mediaPlayer.setVolume(1);
                        stopSounds = true;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            int j=0;
                            @Override
                            public void run() {

                                if (j==1){
                                    stopSounds = false;
                                    timer.cancel();
                                    return;
                                }

                                j++;
                            }
                        },0,2000);
                        mediaPlayer.play();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    rectangle.setLayoutY(0);
                    rectangle.setLayoutX(0);
                    rectangle.setWidth(0);
                    rectangle.setHeight(0);

                    finalRound = true;
                    isFinalRound = true;

                    try {
                        bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/walk.gif")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bearAnimation.setLayoutX(0);
                    bearAnimation.setLayoutY(0);
                    bearAnimation.setFitHeight(160 * deltaMainScene);
                    bearAnimation.setFitWidth(240 * deltaMainScene);
                    bearAnimation.setScaleX(1);

                }

                if (finalRound){

                    if (nps.getLayoutX() <= ladder.get(0).getLayoutX() - 300 * deltaMainScene){

                        nps.setLayoutX(nps.getLayoutX() + 8 * deltaMainScene);

                    }
                    else {
                        finalRound = false;
                        directionBear = "right";
                        newDirection = true;
                        npsHeals = 1;
                        typeRound = 1;
                    }

                }
                else {

                    /*Смерть*/
                    if (npsHeals <= 0) {
                        try {
                            bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/dead.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        typeCamera = 0;

                        bearAnimation.setLayoutX(0);
                        bearAnimation.setLayoutY(20 * deltaMainScene);
                        bearAnimation.setFitHeight(160 * deltaMainScene);
                        bearAnimation.setFitWidth(240 * deltaMainScene);
                        if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                        else bearAnimation.setScaleX(-1);

                        try {
                            if (mediaPlayer != null) mediaPlayer.stop();
                            mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bear/attack3.mp3")).toURI().toURL().toString()));
                            mediaPlayer.setVolume(1);
                            stopSounds = true;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                int j=0;
                                @Override
                                public void run() {

                                    if (j==1){
                                        stopSounds = false;
                                        timer.cancel();
                                        return;
                                    }

                                    j++;
                                }
                            },0,1000);
                            mediaPlayer.play();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        deadBear(nps);
                    }

                    /*----------------------------------------------------*/

                    /*Колизия*/

                    boolean fallDown = true;
                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent()) &&
                                (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY())) {
                            fallDown = false;
                            break;
                        }
                    }
                    if (fallDown) {
                        nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (rectangle.getLayoutY() + 7 * deltaMainScene > nps.getHeight() + nps.getLayoutY()) {
                                nps.setLayoutY(rectangle.getLayoutY() - nps.getHeight());
                                break;
                            }
                        }
                    }

                    for (Rectangle rectangle : allGround) {
                        if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                            if (rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() <= 5 * deltaMainScene &&
                                    rectangle.getLayoutY() + rectangle.getHeight() - nps.getLayoutY() >= 0) {
                                nps.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
                                break;
                            }
                        }
                    }

                    /*----------------------------------------------------*/

                    /*Получение урона*/

                    if (waitAll == 0) {
                        if (isAttack) {

                            if (attackNPS.equals("right")) {
                                if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                    if ((xAttack <= nps.getLayoutX() && xAttack + deltaXAttack >= nps.getLayoutX()) ||
                                            (xAttack <= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack >= nps.getLayoutX() + nps.getWidth())) {
                                        npsHeals--;
                                        waitAll = 30;

                                    }
                                }
                            }

                            if (attackNPS.equals("left")) {
                                if ((yAttack >= nps.getLayoutY() && yAttack <= nps.getLayoutY() + nps.getHeight()) ||
                                        (yAttack + deltaYAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {
                                    if ((xAttack >= nps.getLayoutX() && xAttack + deltaXAttack <= nps.getLayoutX()) ||
                                            (xAttack >= nps.getLayoutX() + nps.getWidth() && xAttack + deltaXAttack <= nps.getLayoutX() + nps.getWidth())) {
                                        npsHeals--;
                                        waitAll = 30;

                                    }
                                }
                            }

                            if (attackNPS.equals("top")) {
                                if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                        (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                    if ((yAttack >= nps.getLayoutY() && yAttack + deltaYAttack <= nps.getLayoutY()) ||
                                            (yAttack >= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack <= nps.getLayoutY() + nps.getHeight())) {

                                        npsHeals--;
                                        waitAll = 30;

                                    }
                                }
                            }

                            if (attackNPS.equals("down")) {

                                if ((nps.getLayoutX() <= xAttack && nps.getLayoutX() + nps.getWidth() >= xAttack) ||
                                        (nps.getLayoutX() <= xAttack + deltaXAttack && nps.getLayoutX() + nps.getWidth() >= xAttack + deltaXAttack)) {
                                    if ((yAttack + deltaYAttack <= nps.getLayoutY() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight()) ||
                                            (yAttack <= nps.getLayoutY() + nps.getHeight() && yAttack + deltaYAttack >= nps.getLayoutY() + nps.getHeight())) {

                                        npsHeals--;
                                        waitAll = 30;

                                    }
                                }
                            }

                        }
                    } else {
                        if (waitAll > 0) waitAll--;
                    }

                    /*Движение*/

                    if (/*!isCollision &&*/ waitAll == 0) {

                        double distance = Math.sqrt(Math.pow((nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2), 2) +
                                Math.pow((nps.getLayoutY() + nps.getHeight() / 2 - pers.getLayoutY() - pers.getHeight() / 2), 2));
                        double deltaXDistance = Math.abs(nps.getLayoutX() + nps.getWidth() / 2 - pers.getLayoutX() - pers.getWidth() / 2);
                        double a = Math.acos(deltaXDistance / distance);

                        if (a <= Math.PI / 6 && deltaXDistance <= 600 * deltaMainScene && isFounded) {
                            if (Math.abs(pers.getLayoutX() - nps.getLayoutX()) >= 20 * deltaMainScene) {
                                nowActionBear = "walk";
                                fallDown = true;
                                for (Rectangle rectangle : allGround) {
                                    if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {

                                        if (pers.getLayoutX() > nps.getLayoutX()) {
                                            nps.setLayoutX(nps.getLayoutX() + 4 * deltaMainScene);
                                        } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                            nps.setLayoutX(nps.getLayoutX() - 4 * deltaMainScene);
                                        }

                                        fallDown = false;
                                        break;
                                    }
                                }
                                if (fallDown) {
                                    nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                                }
                            } else {
                                isFounded = false;
                            }
                        } else if ((a <= Math.PI / 8 && deltaXDistance <= 500 * deltaMainScene) ||
                                distance <= 100 * deltaMainScene) {

                            if (!blockChooseCamera) typeCamera = 1;

                            if (DataMusic.typeMusic != 2){
                                DataMusic.typeMusic = 2;
                                DataMusic.startMusic(2);
                            }

                            fallDown = true;
                            for (Rectangle rectangle : allGround) {
                                if (nps.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {

                                    if (pers.getLayoutX() > nps.getLayoutX()) {
                                        nps.setLayoutX(nps.getLayoutX() + 4 * deltaMainScene);
                                    } else if (pers.getLayoutX() < nps.getLayoutX()) {
                                        nps.setLayoutX(nps.getLayoutX() - 4 * deltaMainScene);
                                    }

                                    if (nps.getLayoutX() >= pers.getLayoutX()) {
                                        newDirection = true;
                                        directionBear = "right";
                                    } else {
                                        newDirection = true;
                                        directionBear = "left";
                                    }

                                    isFounded = true;

                                    fallDown = false;
                                    break;
                                }
                            }
                            if (fallDown) {
                                nps.setLayoutY(nps.getLayoutY() + 7 * deltaMainScene);
                            }
                        } else {
                            isFounded = false;
                        }
                    }

                    /*----------------------------------------------------*/

                    /*Удар*/

                    if (isCollision && waitAttack == 0) {

                        switch (typeRound) {
                            case (1) -> {
                                numAttack = 1;
                            }
                            case (2) -> {
                                numAttack = (int) (Math.random() * 2) + 1;
                            }
                            case (3) -> {
                                numAttack = (int) (Math.random() * 2) + 2;
                            }
                        }

                        waitAttack = 30;

                        Timer timerMain = new Timer();

                        timerMain.schedule(new TimerTask() {

                            final int deltaBearAttack = 30;
                            int j = (isAttack) ? 1 : 0;

                            @Override
                            public void run() {
                                if (j == 1) {
                                    switch (numAttack) {
                                        case (1) -> {
                                            nowActionForSound = "attack1";
                                            if ((directionBear.equals("left") && nps.getLayoutX() + nps.getWidth() >= pers.getLayoutX() + deltaBearAttack * deltaMainScene) ||
                                                    (directionBear.equals("right") && nps.getLayoutX() <= pers.getLayoutX() + pers.getWidth() - deltaBearAttack * deltaMainScene)) {
                                                nowActionBear = "attack1";
                                                myHealth -= 1;
                                                cameraWait = 30;
                                                waitAttack = 50;

                                                Timer timer = new Timer();

                                                timer.schedule(new TimerTask() {
                                                    int j = 0;

                                                    @Override
                                                    public void run() {
                                                        if (j == 1) {
                                                            Platform.runLater(() -> {

                                                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                }
                                                            });

                                                            timer.cancel();
                                                            return;
                                                        }
                                                        j++;
                                                    }
                                                }, 0, 200);
                                            }
                                        }
                                        case (2) -> {
                                            nowActionForSound = "attack2";
                                            if ((directionBear.equals("left") && nps.getLayoutX() + nps.getWidth() >= pers.getLayoutX() + deltaBearAttack * deltaMainScene) ||
                                                    (directionBear.equals("right") && nps.getLayoutX() <= pers.getLayoutX() + pers.getWidth() - deltaBearAttack * deltaMainScene)) {
                                                nowActionBear = "attack2";
                                                myHealth -= 1;
                                                cameraWait = 40;
                                                waitAttack = 60;

                                                Timer timer = new Timer();

                                                timer.schedule(new TimerTask() {
                                                    int j = 0;

                                                    @Override
                                                    public void run() {
                                                        if (j == 1) {
                                                            Platform.runLater(() -> {

                                                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                }
                                                            });

                                                        }
                                                        if (j == 2) {
                                                            Platform.runLater(() -> {
                                                                myHealth -= 1;

                                                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                }
                                                            });

                                                            timer.cancel();
                                                            return;
                                                        }
                                                        j++;
                                                    }
                                                }, 0, 200);
                                            }
                                        }
                                        case (3) -> {
                                            nowActionForSound = "attack2";
                                            if ((directionBear.equals("left") && nps.getLayoutX() + nps.getWidth() >= pers.getLayoutX() + deltaBearAttack * deltaMainScene) ||
                                                    (directionBear.equals("right") && nps.getLayoutX() <= pers.getLayoutX() + pers.getWidth() - deltaBearAttack * deltaMainScene)) {
                                                nowActionBear = "attack3";
                                                //myHealth -= 3;
                                                cameraWait = 40;
                                                waitAttack = 60;

                                                Timer timer = new Timer();

                                                timer.schedule(new TimerTask() {
                                                    int j = 0;

                                                    @Override
                                                    public void run() {
                                                        if (j == 7 || j == 8 || j == 9) {
                                                            Platform.runLater(() -> {

                                                                myHealth--;

                                                                if (pers.getLayoutX() > nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(rectangle.getLayoutX() - (pers.getLayoutX() + pers.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() + maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                } else if (pers.getLayoutX() <= nps.getLayoutX()) {
                                                                    double maxWidthAnimation = 50 * deltaMainScene;
                                                                    for (Rectangle rectangle : allGround) {
                                                                        if ((pers.getLayoutY() > rectangle.getLayoutY() && pers.getLayoutY() < rectangle.getLayoutY() + rectangle.getHeight()) ||
                                                                                (pers.getLayoutY() + pers.getHeight() > rectangle.getLayoutY() && pers.getLayoutY() + pers.getHeight() < rectangle.getLayoutY() + rectangle.getHeight())) {
                                                                            if (Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth())) <= maxWidthAnimation) {
                                                                                maxWidthAnimation = Math.abs(pers.getLayoutX() - (rectangle.getLayoutX() + rectangle.getWidth()));
                                                                            }
                                                                        }
                                                                    }
                                                                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                                                                            new KeyValue(pers.layoutXProperty(), pers.getLayoutX() - maxWidthAnimation)));
                                                                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY())));
                                                                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100),
                                                                            new KeyValue(pers.layoutYProperty(), pers.getLayoutY() - 15 * deltaMainScene)));
                                                                    timeline1.setOnFinished(e -> timeline2.play());
                                                                    timeline1.play();
                                                                    timeline.play();
                                                                }
                                                            });

                                                        }
                                                        if (j == 10) {
                                                            timer.cancel();
                                                            return;
                                                        }
                                                        j++;
                                                    }
                                                }, 0, 50);
                                            }
                                        }
                                    }

                                    timerMain.cancel();
                                    return;
                                }
                                j++;
                            }
                        }, 0, 200);

                    } else {
                        if (waitAttack > 0) waitAttack--;
                    }


                    /*Анимация*/

                    if (isBearAnimation) {
                        if (nowActionBear.equals("walk") && (!pastActionBear.equals("walk") || newDirection)) {
                            try {
                                bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/walk.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bearAnimation.setLayoutX(0);
                            bearAnimation.setLayoutY(0);
                            bearAnimation.setFitHeight(160 * deltaMainScene);
                            bearAnimation.setFitWidth(240 * deltaMainScene);
                            if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                            else bearAnimation.setScaleX(-1);
                        } else if (nowActionBear.equals("attack1") && (!pastActionBear.equals("attack1") || newDirection)) {
                            try {
                                bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/attack1.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            bearAnimation.setLayoutX(0);
                            bearAnimation.setLayoutY(0);
                            bearAnimation.setFitHeight(160 * deltaMainScene);
                            bearAnimation.setFitWidth(240 * deltaMainScene);
                            if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                            else bearAnimation.setScaleX(-1);
                            isBearAnimation = false;

                            Timer timer = new Timer();

                            timer.schedule(new TimerTask() {
                                int j = 0;

                                @Override
                                public void run() {
                                    if (j >= 1) {
                                        isBearAnimation = true;
                                        timer.cancel();
                                        return;
                                    }
                                    j++;
                                }
                            }, 0, 700);
                        } else if (nowActionBear.equals("attack2") && (!pastActionBear.equals("attack2") || newDirection)) {
                            try {
                                bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/attack2.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            bearAnimation.setLayoutX(0);
                            bearAnimation.setLayoutY(-54 * deltaMainScene);
                            bearAnimation.setFitHeight(214 * deltaMainScene);
                            bearAnimation.setFitWidth(240 * deltaMainScene);
                            if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                            else bearAnimation.setScaleX(-1);
                            isBearAnimation = false;

                            Timer timer = new Timer();

                            timer.schedule(new TimerTask() {
                                int j = 0;

                                @Override
                                public void run() {
                                    if (j >= 1) {
                                        isBearAnimation = true;
                                        timer.cancel();
                                        return;
                                    }
                                    j++;
                                }
                            }, 0, 840);
                        } else if (nowActionBear.equals("attack3") && (!pastActionBear.equals("attack3") || newDirection)) {
                            try {
                                bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/attack3.gif")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            bearAnimation.setLayoutX(0);
                            bearAnimation.setLayoutY(0);
                            bearAnimation.setFitHeight(160 * deltaMainScene);
                            bearAnimation.setFitWidth(240 * deltaMainScene);
                            if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                            else bearAnimation.setScaleX(-1);
                            isBearAnimation = false;

                            Timer timer = new Timer();

                            timer.schedule(new TimerTask() {
                                int j = 0;

                                @Override
                                public void run() {
                                    if (j >= 1) {
                                        isBearAnimation = true;
                                        timer.cancel();
                                        return;
                                    }
                                    j++;
                                }
                            }, 0, 800);
                        } else if (nowActionBear.equals("stand") && (!pastActionBear.equals("stand") || newDirection)) {
                            try {
                                bearAnimation.setImage(new Image(new FileInputStream("src/main/animation/nps/bear/simple.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bearAnimation.setLayoutX(13 * deltaMainScene);
                            bearAnimation.setLayoutY(29 * deltaMainScene);
                            bearAnimation.setFitHeight(137 * deltaMainScene);
                            bearAnimation.setFitWidth(205 * deltaMainScene);
                            if (directionBear.equals("left")) bearAnimation.setScaleX(1);
                            else bearAnimation.setScaleX(-1);
                        }

                        newDirection = false;
                    }

                    pastActionBear = nowActionBear;
                    nowActionBear = "stand";

                    /*Установка звуков*/
                    if (!stopSounds) {
                        if (nowActionForSound.equals("attack1") && !pastActionForSound.equals("attack1")) {
                            try {
                                if (mediaPlayer != null) mediaPlayer.stop();
                                mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bear/attack1.mp3")).toURI().toURL().toString()));
                                mediaPlayer.setVolume(1);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,750);
                                mediaPlayer.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } else if (nowActionForSound.equals("attack2") && !pastActionForSound.equals("attack2")) {
                            try {
                                if (mediaPlayer != null) mediaPlayer.stop();
                                mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bear/attack2.mp3")).toURI().toURL().toString()));
                                mediaPlayer.setVolume(1);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,900);
                                mediaPlayer.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } else if (nowActionForSound.equals("attack3") && !pastActionForSound.equals("attack3")) {
                            try {
                                if (mediaPlayer != null) mediaPlayer.stop();
                                mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/nps/bear/attack3.mp3")).toURI().toURL().toString()));
                                mediaPlayer.setVolume(1);
                                stopSounds = true;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    int j=0;
                                    @Override
                                    public void run() {

                                        if (j==1){
                                            stopSounds = false;
                                            timer.cancel();
                                            return;
                                        }

                                        j++;
                                    }
                                },0,1000);
                                mediaPlayer.play();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (nowActionForSound.equals("stand")) {
                            if (mediaPlayer != null) mediaPlayer.stop();
                        }
                    }

                    pastActionForSound = nowActionForSound;
                    nowActionForSound = "stand";

                }

            }
        };

        animationTimerBear.start();
        npsRules.add(animationTimerBear);
    }

    void deadBear(AnchorPane nps) {
        npsRules.get(Integer.parseInt(((Label) nps.getChildren().get(1)).getText())).stop();
        DataMusic.typeMusic = 1;
        DataMusic.startMusic(1);
        myEXPNow += 5;
    }

    void stopAllAnimation() {
        persRules.stop();
        if (mediaPlayerForPers != null) mediaPlayerForPers.stop();
        for (AnimationTimer animationTimer : npsRules) {
            animationTimer.stop();
        }
    }

    void playAllAnimation() {
        persRules.start();
        for (AnimationTimer animationTimer : npsRules) {
            animationTimer.start();
        }
    }

    void toMenu() {
        if (!inMenu) {
            inMenu = true;

            menu.setVisible(true);
            menu.setDisable(false);
            menu.setOpacity(1);

            Platform.runLater(() -> {
                ((Scene) pers.getScene()).setCursor(Cursor.DEFAULT);
            });

            persRules.stop();

        }

    }

    void toInventar() {
        if (!inMenu) {
            Platform.runLater(() -> {

                FXMLLoader fxmlLoader1 = new FXMLLoader(ControllerInvertar.class.getResource("inventar.fxml"));

                Stage stage1 = new Stage();
                try {
                    fxmlLoader1.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = fxmlLoader1.getRoot();
                Scene scene1 = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);
                stage1.setScene(scene1);
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.initOwner(pers.getScene().getWindow());
                stage1.initStyle(StageStyle.TRANSPARENT);
                stage1.show();

            });
        }
    }

    void toCraft() {
        if (!inMenu) {
            Platform.runLater(() -> {

                FXMLLoader fxmlLoader1 = new FXMLLoader(ControllerCraft.class.getResource("craft.fxml"));

                Stage stage1 = new Stage();
                try {
                    fxmlLoader1.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = fxmlLoader1.getRoot();
                Scene scene1 = new Scene(root, DataScriptFloby.widthScreenWindowGame, DataScriptFloby.heightScreenWindowGame);
                stage1.setScene(scene1);
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.initOwner(pers.getScene().getWindow());
                stage1.initStyle(StageStyle.TRANSPARENT);
                stage1.show();

            });
        }
    }

    void correctDataScript() {

        myHealth = DataScriptFloby.myHealthNow;
        myStaminaNow = DataScriptFloby.myStaminaNow;
        myStaminaMax = DataScriptFloby.myStaminaMax;
        myStaminaAdded = DataScriptFloby.myStaminaAdded;
        myWeightNow = DataScriptFloby.myWeightNow;
        myWeightMax = DataScriptFloby.myWeightMax;
        myEXPNow = DataScriptFloby.myEXPNow;
        numEXPForNextLVL = DataScriptFloby.numEXPForNextLVL[DataScriptFloby.myLVL];
        myFreezeHeals = DataScriptFloby.myFreezeHeals;
        defFreezeArmor = DataScriptFloby.defFreezeArmor;
        isCoolInThisLocation = DataScriptFloby.isCoolInThisLocation[DataScriptFloby.indexLocation];

        mustCorrect = false;

    }

    void loadSavedScene (){
        loadScene = false;
        stopAllAnimation();
        DataScriptFloby.loadDataGame();
        DataScriptFloby.loadSavedScene((Stage) pers.getScene().getWindow());
    }

}

/*
*
*   1 - wolf
*   2 - angryWolf
*   3 - simpleBird
*   4 - boar
*   5 - bear
*
* */


