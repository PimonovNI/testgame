package com.example.demo.interfaceForFloby;

import com.example.demo.scripts.DataScriptFloby;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class ControllerCraft {

    AnimationTimer animationTimer;

    ArrayList <AnchorPane> left = new ArrayList<>();
    ArrayList <AnchorPane> right = new ArrayList<>();
    ArrayList <AnchorPane> top = new ArrayList<>();
    ArrayList <AnchorPane> bottom = new ArrayList<>();

    int indexItem=0;
    int numUpgrade = 0;
    int chooseUpgrade = 1;
    String nameItem;
    ArrayList[] resourceForUpgrade;
    ArrayList[] infoFromUpgrade;

    double[] layoutYIndicatorChoose = {170 *DataScriptFloby.deltaScreenWindowGame, 335 * DataScriptFloby.deltaScreenWindowGame, 500 * DataScriptFloby.deltaScreenWindowGame};

    ArrayList <KeyCode> pressedBut = new ArrayList<>();

    ArrayList <Node> nodeObject = new ArrayList<>();

    Color colorDefaultForNoChooseBlock = Color.web("#ffffed");
    Color colorDefaultForChooseBlock = Color.web("#fff8bf");
    Color colorDefaultNoChoseBackground = Color.web("#929292");
    Color colorDefaultChoseBackground = Color.web("#d6d6d6");
    Color colorDefaultLine = Color.web("#000000");
    Color colorDefaultText = Color.web("#000000");
    String colorProgressBar = "#20b2aa";

    Timeline timelineLeft;
    Timeline timelineLeft1;
    Timeline timelineRight;
    Timeline timelineRight1;
    Timeline timelineTop;
    Timeline timelineTop1;
    Timeline timelineBottom;
    Timeline timelineBottom1;
    Timeline timelineIndicator;
    Timeline timelineIndicator1;
    Timeline timelineCenter;
    Timeline timelineCenter1;

    AnchorPane anchorPaneInfo;

    boolean isOpenMap = true;
    boolean isAllResourse = false;
    boolean isOneChoose = false;

    ProgressBar progressBarE = new ProgressBar();
    ProgressBar progressBarENTER = new ProgressBar();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox chooseUpgradeItemBox, infoOfItemBox;

    @FXML
    private GridPane newFromUpgradeGrid, resourseForUpgradeGrid;

    @FXML
    private AnchorPane chooseItem, mainAnchor, resourseForUpgrade, newFromUpgrade, infoOfItem, chooseUpgradeItem, enterAncher, eAncher;

    @FXML
    private ImageView imgBackground,resourseForUpgradeImage, newFromUpgradeImage, infoOfItemImage, chooseUpgradeItemImage, indicatorChoose;

    @FXML
    private Rectangle blockRect;

    @FXML
    void initialize() {

        Platform.runLater(() -> {
            Scene scene = infoOfItem.getScene();
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE){
                    ((Stage)scene.getWindow()).close();
                }
                else if (!pressedBut.contains(e.getCode())){
                    pressedBut.add(e.getCode());
                }
            });
            scene.setOnKeyReleased(e -> {
                pressedBut.remove(e.getCode());
            });
        });

        nodeObject.add(chooseUpgradeItem);
        nodeObject.add(infoOfItem);
        nodeObject.add(resourseForUpgrade);
        nodeObject.add(newFromUpgrade);
        nodeObject.add(imgBackground);
        nodeObject.add(chooseItem);
        nodeObject.add(eAncher);
        nodeObject.add(enterAncher);

        resourseForUpgradeGrid = ((GridPane) resourseForUpgrade.getChildren().get(1));
        resourseForUpgradeImage = ((ImageView) resourseForUpgrade.getChildren().get(0));

        newFromUpgradeGrid = ((GridPane) newFromUpgrade.getChildren().get(1));
        newFromUpgradeImage = ((ImageView) newFromUpgrade.getChildren().get(0));

        infoOfItemBox = ((VBox) infoOfItem.getChildren().get(1));
        infoOfItemImage = ((ImageView) infoOfItem.getChildren().get(0));

        chooseUpgradeItemBox = ((VBox) chooseUpgradeItem.getChildren().get(1));
        chooseUpgradeItemImage = ((ImageView) chooseUpgradeItem.getChildren().get(0));

        for (Node node : nodeObject){
            node.setLayoutX(node.getLayoutX() * DataScriptFloby.deltaScreenWindowGame);
            node.setLayoutY(node.getLayoutY() * DataScriptFloby.deltaScreenWindowGame);
        }

        chooseUpgradeItem.setPrefHeight(chooseUpgradeItem.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItem.setPrefWidth(chooseUpgradeItem.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItem.setPrefHeight(infoOfItem.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItem.setPrefWidth(infoOfItem.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgrade.setPrefHeight(resourseForUpgrade.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgrade.setPrefWidth(resourseForUpgrade.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgrade.setPrefHeight(newFromUpgrade.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgrade.setPrefWidth(newFromUpgrade.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        imgBackground.setFitHeight(imgBackground.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        imgBackground.setFitWidth(imgBackground.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgradeImage.setFitHeight(resourseForUpgradeImage.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgradeImage.setFitWidth(resourseForUpgradeImage.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        chooseItem.setPrefHeight(chooseItem.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        chooseItem.setPrefWidth(chooseItem.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgradeGrid.setPrefHeight(resourseForUpgradeGrid.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        resourseForUpgradeGrid.setPrefWidth(resourseForUpgradeGrid.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgradeGrid.setPrefHeight(newFromUpgradeGrid.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgradeGrid.setPrefWidth(newFromUpgradeGrid.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgradeImage.setFitHeight(newFromUpgradeImage.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgradeImage.setFitWidth(newFromUpgradeImage.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItemBox.setPrefHeight(infoOfItemBox.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItemBox.setPrefWidth(infoOfItemBox.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItemImage.setFitHeight(infoOfItemImage.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        infoOfItemImage.setFitWidth(infoOfItemImage.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItemBox.setPrefHeight(chooseUpgradeItemBox.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItemBox.setPrefWidth(chooseUpgradeItemBox.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItemImage.setFitHeight(chooseUpgradeItemImage.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItemImage.setFitWidth(chooseUpgradeItemImage.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        blockRect.setHeight(blockRect.getHeight() * DataScriptFloby.deltaScreenWindowGame);
        blockRect.setWidth(blockRect.getWidth() * DataScriptFloby.deltaScreenWindowGame);
        eAncher.setPrefHeight(eAncher.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        eAncher.setPrefWidth(eAncher.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        enterAncher.setPrefHeight(enterAncher.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        enterAncher.setPrefWidth(enterAncher.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);

        try {
            imgBackground.setImage(new Image(new FileInputStream("src/main/img/craft/background/1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        progressBarE.setProgress(0);
        progressBarE.setPrefWidth(150 * DataScriptFloby.deltaScreenWindowGame);
        progressBarE.setPrefHeight(30 * DataScriptFloby.deltaScreenWindowGame);
        eAncher.getChildren().add(progressBarE);

        progressBarENTER.setProgress(0);
        progressBarENTER.setPrefWidth(150 * DataScriptFloby.deltaScreenWindowGame);
        progressBarENTER.setPrefHeight(30 * DataScriptFloby.deltaScreenWindowGame);
        enterAncher.getChildren().add(progressBarENTER);

        correctInfo();

        /*Заполнение выбора улучшения для предмета*/
        correctLeft();

        /*Настройка индикатора*/
        try {
            indicatorChoose.setImage(new Image(new FileInputStream("src/main/img/craft/icons/system/indicator.png")));
            indicatorChoose.setFitHeight(30 * DataScriptFloby.deltaScreenWindowGame);
            indicatorChoose.setFitWidth(30 * DataScriptFloby.deltaScreenWindowGame);
            indicatorChoose.setLayoutX(90 * DataScriptFloby.deltaScreenWindowGame);
            indicatorChoose.setLayoutY(layoutYIndicatorChoose[chooseUpgrade - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*Заполнение информации про ресурсы*/
        correctTop();

        /*Заполнение информации улучшения*/
        correctBottom();

        /*Заполнение дополнительной информации*/
        correctRight();

        /*Создание центальной карты улучшений*/
        correctCenter();

        /*Создание информационного окна для центра*/
        try {
            anchorPaneInfo = new AnchorPane();
            anchorPaneInfo.setPrefWidth(100 * DataScriptFloby.deltaScreenWindowGame);
            anchorPaneInfo.setPrefHeight(100 * DataScriptFloby.deltaScreenWindowGame);
            anchorPaneInfo.setDisable(true);

            ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/img/craft/background/infoBlock.png")));
            imageView.setFitWidth(100 * DataScriptFloby.deltaScreenWindowGame);
            imageView.setFitHeight(100 * DataScriptFloby.deltaScreenWindowGame);
            imageView.setLayoutX(0);
            imageView.setLayoutY(0);

            Label labelName = new Label("Топор");
            labelName.setAlignment(Pos.CENTER);
            labelName.setId("labelInfoCraft");
            labelName.setPrefWidth(100 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setPrefHeight(32 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setLayoutX(0);
            labelName.setLayoutY(10 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setTextFill(colorDefaultText);

            Label labelDescription = new Label("Чертёж найден");
            labelDescription.setAlignment(Pos.CENTER);
            labelDescription.setId("labelInfoCraftD");
            labelDescription.setPrefWidth(100 * DataScriptFloby.deltaScreenWindowGame);
            labelDescription.setPrefHeight(32 * DataScriptFloby.deltaScreenWindowGame);
            labelDescription.setLayoutX(0);
            labelDescription.setLayoutY(50 * DataScriptFloby.deltaScreenWindowGame);
            labelDescription.setTextFill(Color.web(""+ chooseColorProgressBar("complete")));

            anchorPaneInfo.getChildren().addAll(imageView, labelName, labelDescription);

            anchorPaneInfo.setOpacity(0);

            mainAnchor.getChildren().add(anchorPaneInfo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        createTimeline();

        resourseForUpgrade.setLayoutY(-201 * DataScriptFloby.deltaScreenWindowGame);
        newFromUpgrade.setLayoutY(721 * DataScriptFloby.deltaScreenWindowGame);
        infoOfItem.setLayoutX(1281 * DataScriptFloby.deltaScreenWindowGame);
        chooseUpgradeItem.setLayoutX(-326 * DataScriptFloby.deltaScreenWindowGame);
        indicatorChoose.setLayoutX(-326 * DataScriptFloby.deltaScreenWindowGame);

        mainAnimationTimer();

    }

    void correctInfo(){
        nameItem = DataForInterface.infoItemsForUpgrade[indexItem][0];
        resourceForUpgrade = new ArrayList[]{
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][1].split(","))),
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][2].split(","))),
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][3].split(",")))
        };
        infoFromUpgrade = new ArrayList[]{
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][4].split(","))),
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][5].split(","))),
            new ArrayList(List.of(DataForInterface.infoItemsForUpgrade[indexItem][6].split(",")))
        };
    }

    void mainAnimationTimer(){
        animationTimer = new AnimationTimer() {
            boolean isDeltaChoose=true;

            int pressedE = 0;
            int pressedENTER = 0;

            @Override
            public void handle(long now) {

                /*Смена выбора улучшения*/
                if (isDeltaChoose && !isOpenMap){
                    boolean isMoved = false;
                    if (pressedBut.contains(KeyCode.W) && !pressedBut.contains(KeyCode.S)){
                        if (chooseUpgrade == 1) chooseUpgrade = 3;
                        else chooseUpgrade--;
                        isMoved = true;
                    }
                    else if (pressedBut.contains(KeyCode.S) && !pressedBut.contains(KeyCode.W)){
                        if (chooseUpgrade == 3) chooseUpgrade = 1;
                        else chooseUpgrade++;
                        isMoved = true;
                    }
                    if (isMoved){
                        isDeltaChoose=false;
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(600),
                                new KeyValue(indicatorChoose.layoutYProperty(), layoutYIndicatorChoose[chooseUpgrade - 1])));
                        timeline.setOnFinished(e -> isDeltaChoose = true);
                        timeline.play();
                        timelineRight.play();
                        timelineTop.play();
                        timelineBottom.play();
                    }
                }

                if (pressedBut.contains(KeyCode.E)) {
                    if (pressedE >= 15) {
                        if (isOpenMap) {
                            if (!isOneChoose) {
                                isOneChoose=true;
                                newItem("ax");
                            }
                            timelineCenter1.play();
                            isOpenMap = false;
                            new Timeline(new KeyFrame(Duration.millis(300),
                                    new KeyValue(blockRect.opacityProperty(), 0))).play();
                        } else {
                            timelineCenter.play();
                            isOpenMap = true;
                            new Timeline(new KeyFrame(Duration.millis(300),
                                    new KeyValue(blockRect.opacityProperty(), 1))).play();
                        }
                        pressedE=0;
                    }
                    else{
                        pressedE++;
                    }
                }
                else{
                    if (pressedE <= 0 )pressedE=0;
                    else pressedE--;
                }

                if (!isOpenMap && isAllResourse){
                    if (pressedBut.contains(KeyCode.ENTER)){
                        if (pressedENTER >= 25) {
                            DataScriptFloby.myItems.add(DataForInterface.translateItems(nameItem));

                            for (int i = 0; i < resourceForUpgrade[chooseUpgrade - 1].size(); i++) {

                                String name = ((String[]) ((String) resourceForUpgrade[chooseUpgrade - 1].get(i)).split(":"))[0];
                                int count = Integer.parseInt(((String[]) ((String) resourceForUpgrade[chooseUpgrade - 1].get(i)).split(":"))[1]);

                                for (int j = 0;j<count;j++){
                                    DataScriptFloby.myItems.remove(DataForInterface.translateItems(name));
                                }

                            }

                            correctInfo();
                            correctTop();

                            pressedENTER = 0;
                        }
                        else {
                            pressedENTER++;
                        }
                    }
                    else {
                        if (pressedENTER<=0) pressedENTER = 0;
                        else pressedENTER--;
                    }
                }

                progressBarENTER.setProgress((double) pressedENTER / 25.0);
                progressBarE.setProgress((double) pressedE / 15.0);

            }
        };
        animationTimer.start();
    }

    void createTimeline(){

        timelineLeft = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(chooseUpgradeItem.layoutXProperty(), -400 * DataScriptFloby.deltaScreenWindowGame)));
        timelineLeft1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(chooseUpgradeItem.layoutXProperty(), 80 * DataScriptFloby.deltaScreenWindowGame)));
        timelineLeft.setOnFinished(e -> {
            correctLeft();
            timelineLeft1.play();
        });

        timelineIndicator = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(indicatorChoose.layoutXProperty(), -400 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(indicatorChoose.layoutYProperty(), layoutYIndicatorChoose[0])));
        timelineIndicator1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(indicatorChoose.layoutXProperty(), 80 * DataScriptFloby.deltaScreenWindowGame)));
        timelineIndicator.setOnFinished(e -> {
            chooseUpgrade = 1;
            timelineIndicator1.play();
        });

        timelineTop = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(resourseForUpgrade.layoutYProperty(), -300 * DataScriptFloby.deltaScreenWindowGame)));
        timelineTop1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(resourseForUpgrade.layoutYProperty(), 60 * DataScriptFloby.deltaScreenWindowGame)));
        timelineTop.setOnFinished(e -> {
            correctTop();
            timelineTop1.play();
        });

        timelineBottom = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(newFromUpgrade.layoutYProperty(), 721 * DataScriptFloby.deltaScreenWindowGame)));
        timelineBottom1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(newFromUpgrade.layoutYProperty(), 360 * DataScriptFloby.deltaScreenWindowGame)));
        timelineBottom.setOnFinished(e -> {
            correctBottom();
            timelineBottom1.play();
        });

        timelineRight = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(infoOfItem.layoutXProperty(), 1281 * DataScriptFloby.deltaScreenWindowGame)));
        timelineRight1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(infoOfItem.layoutXProperty(), 875 * DataScriptFloby.deltaScreenWindowGame)));
        timelineRight.setOnFinished(e -> {
            correctRight();
            timelineRight1.play();
        });

        timelineCenter = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(chooseItem.layoutXProperty(), mainAnchor.getPrefWidth() / 5),
                new KeyValue(chooseItem.layoutYProperty(), mainAnchor.getPrefHeight() / 5),
                new KeyValue(chooseItem.rotateProperty(),0)));

        timelineCenter1 = new Timeline(new KeyFrame(Duration.millis(300),
                new KeyValue(chooseItem.layoutXProperty(), 1280 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(chooseItem.layoutYProperty(), - ((3 * mainAnchor.getPrefHeight()) / 5)),
                new KeyValue(chooseItem.rotateProperty(),90)));

    }

    void correctLeft(){

        try {
            ImageView imageViewBackGround = ((ImageView) chooseUpgradeItem.getChildren().get(0));
            imageViewBackGround.setImage(new Image(new FileInputStream("src/main/img/craft/system/backgroundLaR.jpg")));
            imageViewBackGround.setPreserveRatio(false);
            imageViewBackGround.setLayoutX(0);
            imageViewBackGround.setLayoutY(0);
            imageViewBackGround.setFitWidth(325 * DataScriptFloby.deltaScreenWindowGame);
            imageViewBackGround.setFitHeight(495 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        chooseUpgradeItemBox.getChildren().removeAll(left);
        left = new ArrayList<>();
        for (int i=0;i<3;i++){
            try {
                Rectangle rectangleBack = new Rectangle();
                rectangleBack.setLayoutX(0);
                rectangleBack.setLayoutY(0);
                rectangleBack.setWidth(325 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setHeight(165 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setArcHeight(25);
                rectangleBack.setArcWidth(25);
                if (i < numUpgrade) rectangleBack.setFill(colorDefaultForChooseBlock);
                else rectangleBack.setFill(colorDefaultForNoChooseBlock);
                rectangleBack.setOpacity(0);

                Rectangle line = new Rectangle();
                line.setLayoutX(20 * DataScriptFloby.deltaScreenWindowGame);
                line.setLayoutY(40 * DataScriptFloby.deltaScreenWindowGame);
                line.setWidth(285 * DataScriptFloby.deltaScreenWindowGame);
                line.setHeight(2 * DataScriptFloby.deltaScreenWindowGame);
                line.setArcHeight(10);
                line.setArcWidth(10);
                line.setFill(colorDefaultLine);

                ImageView lineMain = new ImageView();
                if (i!=2){
                    lineMain.setImage(new Image(new FileInputStream("src/main/img/craft/system/lineL.png")));
                    lineMain.setPreserveRatio(false);
                    lineMain.setLayoutX(5 * DataScriptFloby.deltaScreenWindowGame);
                    lineMain.setLayoutY(160 * DataScriptFloby.deltaScreenWindowGame);
                    lineMain.setFitHeight(11 * DataScriptFloby.deltaScreenWindowGame);
                    lineMain.setFitWidth(315 * DataScriptFloby.deltaScreenWindowGame);
                }

                Label labelName = new Label();
                labelName.setAlignment(Pos.CENTER);
                labelName.setId("labelNameCraft");
                labelName.setLayoutX(20 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setLayoutY(0);
                labelName.setPrefWidth(160 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setPrefHeight(20 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setText(DataForInterface.translate(nameItem));
                labelName.setTextFill(colorDefaultText);

                Label labelLVL = new Label();
                labelLVL.setAlignment(Pos.CENTER);
                labelLVL.setId("labelLVLCraft");
                labelLVL.setLayoutX(200 * DataScriptFloby.deltaScreenWindowGame);
                labelLVL.setLayoutY(0);
                labelLVL.setPrefWidth(85 * DataScriptFloby.deltaScreenWindowGame);
                labelLVL.setPrefHeight(20 * DataScriptFloby.deltaScreenWindowGame);
                labelLVL.setText((i+1)+" ур.");
                labelLVL.setTextFill(colorDefaultText);

                Label labelResourceInfo = new Label();
                labelResourceInfo.setAlignment(Pos.CENTER);
                labelResourceInfo.setId("labelResourceInfo");
                labelResourceInfo.setTextAlignment(TextAlignment.CENTER);
                labelResourceInfo.setLayoutX(120 * DataScriptFloby.deltaScreenWindowGame);
                labelResourceInfo.setLayoutY(60 * DataScriptFloby.deltaScreenWindowGame);
                labelResourceInfo.setPrefWidth(185 * DataScriptFloby.deltaScreenWindowGame);
                labelResourceInfo.setPrefHeight(80 * DataScriptFloby.deltaScreenWindowGame);
                if (i<numUpgrade) labelResourceInfo.setText("Предмет"+'\n'+"получен");
                else if (i==numUpgrade) labelResourceInfo.setText("Доступно"+'\n'+"улучшение");
                else labelResourceInfo.setText("Улучшите прев."+'\n'+"предмет");
                //labelResourceInfo.setText("Недостаточно"+'\n'+"ресурсов");
                labelResourceInfo.setTextFill(colorDefaultText);

                ImageView imageView = new ImageView();
                imageView.setImage(new Image(new FileInputStream("src/main/img/craft/icons/"+nameItem+"/"+(i+1)+".png")));
                imageView.setFitWidth(80 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setFitHeight(80 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutX(20 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutY(65 * DataScriptFloby.deltaScreenWindowGame);

                ImageView imageViewB = new ImageView();
                imageViewB.setImage(new Image(new FileInputStream("src/main/img/craft/system/imgBackSq1.png")));
                imageViewB.setFitWidth(100 * DataScriptFloby.deltaScreenWindowGame);
                imageViewB.setFitHeight(100 * DataScriptFloby.deltaScreenWindowGame);
                imageViewB.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
                imageViewB.setLayoutY(55 * DataScriptFloby.deltaScreenWindowGame);


                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefWidth(325 * DataScriptFloby.deltaScreenWindowGame);
                anchorPane.setPrefHeight(165 * DataScriptFloby.deltaScreenWindowGame);

                anchorPane.getChildren().addAll(rectangleBack, line, lineMain, labelName, labelLVL, imageViewB, imageView, labelResourceInfo);

                left.add(anchorPane);

                chooseUpgradeItemBox.getChildren().add(anchorPane);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void correctRight(){

        try {
            ImageView imageViewBackGround = ((ImageView) infoOfItem.getChildren().get(0));
            imageViewBackGround.setImage(new Image(new FileInputStream("src/main/img/craft/system/backgroundLaR.jpg")));
            imageViewBackGround.setPreserveRatio(false);
            imageViewBackGround.setLayoutX(0);
            imageViewBackGround.setLayoutY(0);
            imageViewBackGround.setFitWidth(325 * DataScriptFloby.deltaScreenWindowGame);
            imageViewBackGround.setFitHeight(600 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        infoOfItemBox.getChildren().removeAll(right);
        right = new ArrayList<>();

        /*Изображение*/

        try {
            Rectangle rectangleBack = new Rectangle();
            rectangleBack.setLayoutX(0);
            rectangleBack.setLayoutY(0);
            rectangleBack.setWidth(325 * DataScriptFloby.deltaScreenWindowGame);
            rectangleBack.setHeight(150 * DataScriptFloby.deltaScreenWindowGame);
            rectangleBack.setArcHeight(25);
            rectangleBack.setArcWidth(25);
            rectangleBack.setFill(colorDefaultForChooseBlock);
            rectangleBack.setOpacity(0);

            ImageView imageViewB = new ImageView();
            imageViewB.setImage(new Image(new FileInputStream("src/main/img/craft/system/imgBackSq2.png")));
            imageViewB.setFitWidth(121 * DataScriptFloby.deltaScreenWindowGame);
            imageViewB.setFitHeight(121 * DataScriptFloby.deltaScreenWindowGame);
            imageViewB.setLayoutX(102 * DataScriptFloby.deltaScreenWindowGame);
            imageViewB.setLayoutY(15 * DataScriptFloby.deltaScreenWindowGame);

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/main/img/craft/icons/"+nameItem+"/"+chooseUpgrade+".png")));
            imageView.setFitWidth(101 * DataScriptFloby.deltaScreenWindowGame);
            imageView.setFitHeight(101 * DataScriptFloby.deltaScreenWindowGame);
            imageView.setLayoutX(112 * DataScriptFloby.deltaScreenWindowGame);
            imageView.setLayoutY(25 * DataScriptFloby.deltaScreenWindowGame);

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(325 * DataScriptFloby.deltaScreenWindowGame);
            anchorPane.setPrefHeight(150 * DataScriptFloby.deltaScreenWindowGame);

            anchorPane.getChildren().addAll(rectangleBack, imageViewB, imageView);

            right.add(anchorPane);

            infoOfItemBox.getChildren().add(anchorPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
        Rectangle rectangleBack = new Rectangle();
        rectangleBack.setLayoutX(0);
        rectangleBack.setLayoutY(0);
        rectangleBack.setWidth(325 * DataScriptFloby.deltaScreenWindowGame);
        rectangleBack.setHeight(450 * DataScriptFloby.deltaScreenWindowGame);
        rectangleBack.setArcHeight(25);
        rectangleBack.setArcWidth(25);
        rectangleBack.setFill(colorDefaultForChooseBlock);
        rectangleBack.setOpacity(0);

       /*Rectangle lineTop = new Rectangle();
        lineTop.setLayoutX(10 *DataScriptFloby.deltaScreenWindowGame);
        lineTop.setLayoutY(0);
        lineTop.setWidth(305 * DataScriptFloby.deltaScreenWindowGame);
        lineTop.setHeight(2 * DataScriptFloby.deltaScreenWindowGame);
        lineTop.setArcHeight(25);
        lineTop.setArcWidth(25);
        lineTop.setFill(colorDefaultText);*/

        ImageView lineTop = new ImageView(new Image(new FileInputStream("src/main/img/craft/system/lineR.png")));
        lineTop.setPreserveRatio(false);
        lineTop.setLayoutX(10 *DataScriptFloby.deltaScreenWindowGame);
        lineTop.setLayoutY(0);
        lineTop.setFitWidth(305 * DataScriptFloby.deltaScreenWindowGame);
        lineTop.setFitHeight(33 * DataScriptFloby.deltaScreenWindowGame);

        ImageView lineBottom = new ImageView(new Image(new FileInputStream("src/main/img/craft/system/lineR.png")));
        lineBottom.setPreserveRatio(false);
        lineBottom.setLayoutX(10 *DataScriptFloby.deltaScreenWindowGame); /*110*/
        lineBottom.setLayoutY(300 * DataScriptFloby.deltaScreenWindowGame);
        lineBottom.setFitWidth(305 * DataScriptFloby.deltaScreenWindowGame); /*205*/
        lineBottom.setFitHeight(33 * DataScriptFloby.deltaScreenWindowGame);

        Label labelDescription = new Label();
        labelDescription.setId("labelDescription");
        labelDescription.setAlignment(Pos.CENTER);
        labelDescription.setTextAlignment(TextAlignment.CENTER);
        labelDescription.setLayoutX(25 * DataScriptFloby.deltaScreenWindowGame);
        labelDescription.setLayoutY(25 * DataScriptFloby.deltaScreenWindowGame);
        labelDescription.setPrefHeight(250 * DataScriptFloby.deltaScreenWindowGame);
        labelDescription.setPrefWidth(285 * DataScriptFloby.deltaScreenWindowGame);
        labelDescription.setText(DataForInterface.translateDescriptionItemFromCraft(nameItem, chooseUpgrade));
        labelDescription.setTextFill(colorDefaultText);

        Label labelArticle = new Label();
        labelArticle.setId("labelArticle");
        labelArticle.setAlignment(Pos.CENTER_RIGHT);
        labelArticle.setTextAlignment(TextAlignment.RIGHT);
        labelArticle.setLayoutX(120 * DataScriptFloby.deltaScreenWindowGame);
        labelArticle.setLayoutY(325 * DataScriptFloby.deltaScreenWindowGame);
        labelArticle.setPrefHeight(100 * DataScriptFloby.deltaScreenWindowGame);
        labelArticle.setPrefWidth(185 * DataScriptFloby.deltaScreenWindowGame);
        labelArticle.setText(DataForInterface.translateArticleItemFromCraft(nameItem, chooseUpgrade));
        labelArticle.setTextFill(colorDefaultText);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(325 * DataScriptFloby.deltaScreenWindowGame);
        anchorPane.setPrefHeight(150 * DataScriptFloby.deltaScreenWindowGame);

        anchorPane.getChildren().addAll(rectangleBack, lineTop, lineBottom, labelDescription, labelArticle);

        right.add(anchorPane);

        infoOfItemBox.getChildren().add(anchorPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void correctTop(){

        try {
            ImageView imageViewBackGround = ((ImageView) resourseForUpgrade.getChildren().get(0));
            imageViewBackGround.setImage(new Image(new FileInputStream("src/main/img/craft/system/backgroundPaD.jpg")));
            imageViewBackGround.setPreserveRatio(false);
            imageViewBackGround.setLayoutX(0);
            imageViewBackGround.setLayoutY(0);
            imageViewBackGround.setFitWidth(324 * DataScriptFloby.deltaScreenWindowGame);
            imageViewBackGround.setFitHeight(200 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        resourseForUpgradeGrid.getChildren().removeAll(top);
        top = new ArrayList<>();
        isAllResourse = true;
        for (int i=0;i<resourceForUpgrade[chooseUpgrade - 1].size();i++){
            try {
                String name = ((String[]) ((String) resourceForUpgrade[chooseUpgrade - 1].get(i)).split(":"))[0];
                int count = Integer.parseInt(((String[]) ((String) resourceForUpgrade[chooseUpgrade - 1].get(i)).split(":"))[1]);
                int myCount = Collections.frequency(DataScriptFloby.myItems, DataForInterface.translateItems(name));

                Rectangle rectangleBack = new Rectangle();
                rectangleBack.setLayoutX(0);
                rectangleBack.setLayoutY(0);
                rectangleBack.setWidth(162 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setHeight(67 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setArcHeight(10);
                rectangleBack.setArcWidth(10);
                rectangleBack.setFill(colorDefaultForNoChooseBlock);
                rectangleBack.setOpacity(0);

                ProgressBar progressBar = new ProgressBar();
                progressBar.setPrefHeight(20 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setPrefWidth(142 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setLayoutY(42 * DataScriptFloby.deltaScreenWindowGame);
                if (myCount >= count) {
                    progressBar.setProgress(1);
                    progressBar.setStyle("-fx-accent: " + chooseColorProgressBar("complete"));
                }
                else {
                    progressBar.setProgress((double) myCount / (double) count);
                    progressBar.setStyle("-fx-accent: " + chooseColorProgressBar("noComplete"));
                    isAllResourse = false;
                }

                Label labelCount = new Label();
                labelCount.setAlignment(Pos.CENTER);
                labelCount.setId("labelCountCraft");
                labelCount.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
                labelCount.setLayoutY(36 * DataScriptFloby.deltaScreenWindowGame);
                labelCount.setPrefWidth(142 * DataScriptFloby.deltaScreenWindowGame);
                labelCount.setPrefHeight(20 * DataScriptFloby.deltaScreenWindowGame);
                if (myCount >= count) labelCount.setText(count + "/" + count);
                else labelCount.setText(myCount + "/" + count);
                labelCount.setTextFill(colorDefaultText);

                Label labelName = new Label();
                labelName.setAlignment(Pos.CENTER);
                labelName.setId("labelNameResCraft");
                labelName.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setLayoutY(5 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setPrefWidth(100 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setPrefHeight(32 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setText(DataForInterface.translate(name));
                labelName.setTextFill(colorDefaultText);

                ImageView imageView = new ImageView();
                imageView.setImage(new Image(new FileInputStream("src/main/img/inventar/itemsGraphic/" + DataForInterface.translateItems(name) + ".png")));
                imageView.setFitWidth(32 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setFitHeight(32 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutX(115 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutY(5 * DataScriptFloby.deltaScreenWindowGame);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefHeight(67 * DataScriptFloby.deltaScreenWindowGame);
                anchorPane.setPrefWidth(162 * DataScriptFloby.deltaScreenWindowGame);

                anchorPane.getChildren().addAll(rectangleBack, progressBar, labelCount, imageView, labelName);
                top.add(anchorPane);
                resourseForUpgradeGrid.add(anchorPane, i % 2, i / 2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void correctBottom(){

        try {
            ImageView imageViewBackGround = ((ImageView) newFromUpgrade.getChildren().get(0));
            imageViewBackGround.setImage(new Image(new FileInputStream("src/main/img/craft/system/backgroundPaD.jpg")));
            imageViewBackGround.setPreserveRatio(false);
            imageViewBackGround.setLayoutX(0);
            imageViewBackGround.setLayoutY(0);
            imageViewBackGround.setFitWidth(324 * DataScriptFloby.deltaScreenWindowGame);
            imageViewBackGround.setFitHeight(300 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        newFromUpgradeGrid.getChildren().removeAll(bottom);
        bottom = new ArrayList<>();
        for (int i=0;i<infoFromUpgrade[chooseUpgrade-1].size();i++){
            try {
                String name = ((String[]) ((String) infoFromUpgrade[chooseUpgrade - 1].get(i)).split(":"))[0];
                int myCount = Integer.parseInt(((String[]) ((String) infoFromUpgrade[chooseUpgrade - 1].get(i)).split(":"))[1]);
                int count = Integer.parseInt(((String[]) ((String) infoFromUpgrade[2].get(i)).split(":"))[1]);

                Rectangle rectangleBack = new Rectangle();
                rectangleBack.setLayoutX(0);
                rectangleBack.setLayoutY(0);
                rectangleBack.setWidth(324 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setHeight(60 * DataScriptFloby.deltaScreenWindowGame);
                rectangleBack.setArcHeight(10);
                rectangleBack.setArcWidth(10);
                rectangleBack.setFill(colorDefaultForNoChooseBlock);
                rectangleBack.setOpacity(0);

                ProgressBar progressBar = new ProgressBar();
                progressBar.setPrefHeight(20 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setPrefWidth(284 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setLayoutX(20 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setLayoutY(35 * DataScriptFloby.deltaScreenWindowGame);
                progressBar.setStyle("-fx-accent: " + chooseColorProgressBar(name));
                if (myCount >= count) progressBar.setProgress(1);
                else progressBar.setProgress((double) myCount / (double) count);

                Label labelName = new Label();
                labelName.setAlignment(Pos.CENTER);
                labelName.setId("labelNameInfo");
                labelName.setLayoutX(20 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setLayoutY(0);
                labelName.setPrefWidth(244 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setPrefHeight(25 * DataScriptFloby.deltaScreenWindowGame);
                labelName.setText(DataForInterface.translate(name));
                labelName.setTextFill(colorDefaultText);

                ImageView imageView = new ImageView();
                imageView.setImage(new Image(new FileInputStream("src/main/img/craft/icons/skills/" + name + ".png")));
                imageView.setFitHeight(30 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setFitWidth(30 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutY(2 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setLayoutX(250 * DataScriptFloby.deltaScreenWindowGame);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefWidth(324 * DataScriptFloby.deltaScreenWindowGame);
                anchorPane.setPrefHeight(60 * DataScriptFloby.deltaScreenWindowGame);

                anchorPane.getChildren().addAll(rectangleBack, progressBar, imageView, labelName);

                bottom.add(anchorPane);

                newFromUpgradeGrid.add(anchorPane, 0, i);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void correctCenter(){

        /*
        Rectangle rectangleBack = new Rectangle();
        rectangleBack.setLayoutX(0);
        rectangleBack.setLayoutY(0);
        rectangleBack.setWidth(chooseItem.getPrefWidth());
        rectangleBack.setHeight(chooseItem.getPrefHeight());
        rectangleBack.setArcHeight(100);
        rectangleBack.setArcWidth(100);
        rectangleBack.setFill(colorDefaultForNoChooseBlock);
        */

        try {
            ImageView imageViewB = new ImageView(new Image(new FileInputStream("src/main/img/craft/system/backgroundPaD.jpg")));
            imageViewB.setLayoutX(0);
            imageViewB.setLayoutY(0);
            imageViewB.setPreserveRatio(false);
            imageViewB.setFitWidth(768 * DataScriptFloby.deltaScreenWindowGame);
            imageViewB.setFitHeight(432 * DataScriptFloby.deltaScreenWindowGame);
            chooseItem.getChildren().add(imageViewB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Rectangle line1 = new Rectangle();
        line1.setLayoutX(chooseItem.getPrefWidth() / 3 - (1 * DataScriptFloby.deltaScreenWindowGame));
        line1.setLayoutY(10 * DataScriptFloby.deltaScreenWindowGame);
        line1.setWidth(2 * DataScriptFloby.deltaScreenWindowGame);
        line1.setHeight(chooseItem.getPrefHeight() - (20 * DataScriptFloby.deltaScreenWindowGame));
        line1.setArcHeight(10);
        line1.setArcWidth(10);
        line1.setFill(colorDefaultText);

        Rectangle line2 = new Rectangle();
        line2.setLayoutX( (2 * chooseItem.getPrefWidth())/ 3 - (1 * DataScriptFloby.deltaScreenWindowGame));
        line2.setLayoutY(10 * DataScriptFloby.deltaScreenWindowGame);
        line2.setWidth(2 * DataScriptFloby.deltaScreenWindowGame);
        line2.setHeight(chooseItem.getPrefHeight() - (20 * DataScriptFloby.deltaScreenWindowGame));
        line2.setArcHeight(10);
        line2.setArcWidth(10);
        line2.setFill(colorDefaultText);

        HBox hBox = new HBox();
        hBox.setLayoutX(0);
        hBox.setLayoutY(0);
        hBox.setPrefHeight(chooseItem.getPrefHeight());
        hBox.setPrefWidth(chooseItem.getPrefWidth());

        for (int i=0;i<3;i++) {
            Label labelName = new Label();
            labelName.setAlignment(Pos.CENTER);
            labelName.setId("labelNameResCraft");
            labelName.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setLayoutY(10 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setPrefWidth(236 * DataScriptFloby.deltaScreenWindowGame);
            labelName.setPrefHeight(32 * DataScriptFloby.deltaScreenWindowGame);
            switch (i){
                case (0) -> labelName.setText("Выживание");
                case (1) -> labelName.setText("Снаряжение");
                case (2) -> labelName.setText("Материалы");
            }
            labelName.setTextFill(colorDefaultText);

            Rectangle lineHor = new Rectangle();
            lineHor.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
            lineHor.setLayoutY(52 * DataScriptFloby.deltaScreenWindowGame);
            lineHor.setWidth(236 * DataScriptFloby.deltaScreenWindowGame);
            lineHor.setHeight(2 * DataScriptFloby.deltaScreenWindowGame);
            lineHor.setArcHeight(10);
            lineHor.setArcWidth(10);
            lineHor.setFill(colorDefaultText);

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(chooseItem.getPrefHeight());
            anchorPane.setPrefWidth(chooseItem.getPrefWidth() / 3);

            anchorPane.getChildren().addAll(lineHor, labelName);

            for (int j=0;j<DataForInterface.infoSectionForUpgrade[i].length;j++){
                try {

                    String name = ((String[]) DataForInterface.infoSectionForUpgrade[i][j].split(" "))[0];

                    Label labelIcon = new Label(DataForInterface.infoSectionForUpgrade[i][j]);
                    labelIcon.setTextFill(Color.rgb(0, 0, 0, 0));
                    ImageView imageView;
                    if (DataScriptFloby.myDrawing.contains(name)) {
                        imageView = new ImageView(new Image(new FileInputStream("src/main/img/craft/icons/choose/icon_" + name + ".png")));
                    }else {
                        imageView = new ImageView(new Image(new FileInputStream("src/main/img/craft/icons/choose/icon_" + name + "C.png")));
                    }
                    imageView.setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);
                    imageView.setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
                    labelIcon.setGraphic(imageView);
                    labelIcon.setLayoutX(Integer.parseInt(((String[]) DataForInterface.infoSectionForUpgrade[i][j].split(" "))[1]) * DataScriptFloby.deltaScreenWindowGame);
                    labelIcon.setLayoutY(Integer.parseInt(((String[]) DataForInterface.infoSectionForUpgrade[i][j].split(" "))[2]) * DataScriptFloby.deltaScreenWindowGame);
                    labelIcon.setStyle("-fx-font-size:1px;");

                    labelIcon.setOnMouseExited(this::exitedIcon);
                    labelIcon.setOnMouseEntered(this::enteredIcon);
                    labelIcon.setOnMouseClicked(this::clickedIcon);

                    anchorPane.getChildren().add(labelIcon);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            hBox.getChildren().add(anchorPane);
        }

        chooseItem.getChildren().addAll( line1, line2, hBox);

    }

    @FXML
    void enteredIcon(MouseEvent event){
        Label label = (Label) event.getSource();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(label.layoutXProperty(), label.getLayoutX() - 5 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(label.layoutYProperty(), label.getLayoutY() - 5 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitHeightProperty(), 60 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitWidthProperty(), 60 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(anchorPaneInfo.opacityProperty(), 1)));
        timeline.play();

        correctInfoChoose(label);
    }

    @FXML
    void exitedIcon(MouseEvent event){
        Label label = (Label) event.getSource();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(label.layoutXProperty(), Integer.parseInt(((String[]) label.getText().split(" "))[1]) * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(label.layoutYProperty(), Integer.parseInt(((String[]) label.getText().split(" "))[2]) * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitHeightProperty(), 50 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitWidthProperty(), 50 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(anchorPaneInfo.opacityProperty(),0)));
        timeline.play();

    }

    @FXML
    void clickedIcon(MouseEvent event){
        Label label = (Label) event.getSource();

        if (!isOneChoose) isOneChoose=true;

        newItem(((String[]) label.getText().split(" "))[0]);

    }

    void newItem(String s){
        if (DataScriptFloby.myDrawing.contains(s)){
            indexItem = Integer.parseInt(DataForInterface.translateItems(s));
            chooseUpgrade = 1;
            correctInfo();
            timelineRight.play();
            timelineLeft.play();
            timelineTop.play();
            timelineBottom.play();
            timelineIndicator.play();
        }
    }

    void correctInfoChoose (Label label){

        String name = ((String[])label.getText().split(" "))[0];

        double deltaX = (Integer.parseInt(((String[]) label.getText().split(" "))[1]) + 40) * DataScriptFloby.deltaScreenWindowGame;
        double deltaY = (Integer.parseInt(((String[]) label.getText().split(" "))[2]) + 40) * DataScriptFloby.deltaScreenWindowGame;

        ((Label) anchorPaneInfo.getChildren().get(1)).setText(DataForInterface.translate(name));
        if (DataScriptFloby.myDrawing.contains(name)) {
            ((Label) anchorPaneInfo.getChildren().get(2)).setText("Чертёж есть");
            ((Label) anchorPaneInfo.getChildren().get(2)).setTextFill(Color.web(chooseColorProgressBar("complete")));
        }
        else{
            ((Label) anchorPaneInfo.getChildren().get(2)).setText("Чертёжа нет");
            ((Label) anchorPaneInfo.getChildren().get(2)).setTextFill(Color.web(chooseColorProgressBar("noComplete")));
        }

        anchorPaneInfo.setLayoutX(chooseItem.getLayoutX() + (DataForInterface.translateSectionForUpgrade(name)-1) * 256 * DataScriptFloby.deltaScreenWindowGame + deltaX);
        anchorPaneInfo.setLayoutY(chooseItem.getLayoutY() + deltaY);

    }

    String chooseColorProgressBar(String s){
        return switch (s){
            case "force" -> "#e649d8";
            case "durability" -> "#724df0";
            case "light" -> "#ffffe0";
            case "smear" -> "#ffa500";
            case "weight" ->  "#222";
            case "capacity" ->  "#78866b";
            case "complete" ->  "#4de665";
            case "noComplete" ->  "#e65e5e";
            default -> "#000";
        };
    }

}
