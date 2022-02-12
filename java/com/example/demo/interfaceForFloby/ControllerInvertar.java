package com.example.demo.interfaceForFloby;

import com.example.demo.scripts.DataScriptFloby;
import com.example.demo.scripts.ScriptMain;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ControllerInvertar implements Initializable {

    AnimationTimer mainAnimationTimer;

    ArrayList<KeyCode> pressedBut = new ArrayList<>();

    ArrayList<String> myItems = new ArrayList<>(DataScriptFloby.myItems);
    ArrayList<String> filteredItems = new ArrayList<>(DataScriptFloby.myItems);
    ArrayList<String> saveItems = new ArrayList<>();
    ArrayList<AnchorPane> myPartPage = new ArrayList<>();

    ArrayList<Node> mainPart = new ArrayList<>();
    ArrayList<AnchorPane> mainPartForPlayer = new ArrayList<>();

    Color colorDefaultForNoChooseBlock = Color.web("#ffffed");
    Color colorDefaultForChooseBlock = Color.web("#fff8bf");
    Color colorDefaultNoChoseBackground = Color.web("#929292");
    Color colorDefaultChoseBackground = Color.web("#d6d6d6");

    int deltaH = -1, deltaW = 0;
    int numItems = 0;
    int myPage = 0;
    int nameFilter = 1;

    double weight = 0, damage = 0, frozen = 0, armor = 0;

    boolean isActive = true;

    private MediaPlayer mediaPlayer;

    @FXML
    private AnchorPane ancher, shoes, cloak, jacket, pants, sword, bookmark;

    @FXML
    private GridPane grid, gridCollection;

    @FXML
    private Button butLeft, butRight;

    @FXML
    private Label labWeight, labFrozen, labArmor, labDamage;

    @FXML
    private ProgressBar progressWaitENTER, progressWaitQ;

    @FXML
    private ImageView imagePers, imgBackground, backgroundGridMainPart, backgroundPersMainPart;

    void chooseLabel(Label label) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(label.layoutXProperty(), 15 * DataScriptFloby.deltaScreenWindowGame - 3),
                new KeyValue(label.layoutYProperty(), 15 * DataScriptFloby.deltaScreenWindowGame - 3),
                new KeyValue(((ImageView)label.getGraphic()).fitWidthProperty(), 50 * DataScriptFloby.deltaScreenWindowGame + 6),
                new KeyValue(((ImageView)label.getGraphic()).fitHeightProperty(), 50 * DataScriptFloby.deltaScreenWindowGame + 6),
                new KeyValue(((Rectangle)((AnchorPane) label.getParent()).getChildren().get(0)).fillProperty(), colorDefaultForChooseBlock)));
        timeline.play();
    }

    void noChooseLabel(Label label) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(label.layoutXProperty(), 15 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(label.layoutYProperty(), 15 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitWidthProperty(), 50 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((ImageView)label.getGraphic()).fitHeightProperty(), 50 * DataScriptFloby.deltaScreenWindowGame),
                new KeyValue(((Rectangle)((AnchorPane) label.getParent()).getChildren().get(0)).fillProperty(), colorDefaultForNoChooseBlock)));
        timeline.play();
    }

    void chooseItems(int index) {

        int name = Integer.parseInt(((Label) ((AnchorPane) grid.getChildren().get(index)).getChildren().get(2)).getText());
        String info = DataForInterface.settingsItems[name];

        String appointment = ((String[]) ((String[]) info.split(" +"))[2].split(":"))[1];

        int nowItems = -1;
        AnchorPane anchorPane = new AnchorPane();

        switch (appointment) {
            case "shoes" -> {
                nowItems = Integer.parseInt(((Label) (shoes).getChildren().get(2)).getText());
                anchorPane = shoes;
            }
            case "cloak" -> {
                nowItems = Integer.parseInt(((Label) (cloak).getChildren().get(2)).getText());
                anchorPane = cloak;
            }
            case "jacket" -> {
                nowItems = Integer.parseInt(((Label) (jacket).getChildren().get(2)).getText());
                anchorPane = jacket;
            }
            case "pants" -> {
                nowItems = Integer.parseInt(((Label) (pants).getChildren().get(2)).getText());
                anchorPane = pants;
            }
            case "sword" -> {
                nowItems = Integer.parseInt(((Label) (sword).getChildren().get(2)).getText());
                anchorPane = sword;
            }
        }

        if (nowItems != -1) {
            Label labelOld = (Label) ((AnchorPane) grid.getChildren().get(index)).getChildren().get(1);
            Label labelNew = (Label) anchorPane.getChildren().get(1);

            String nameOld = labelOld.getText();
            labelOld.setText(labelNew.getText());
            labelNew.setText(nameOld);

            try {
                ((ImageView) labelNew.getGraphic()).setImage(new Image(new FileInputStream("src/main/123/" + labelNew.getText() + ".png")));
                ((ImageView) labelOld.getGraphic()).setImage(new Image(new FileInputStream("src/main/123/" + labelOld.getText() + ".png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            myItems.remove(labelNew.getText());
            myItems.add(labelOld.getText());
        }
    }

    void choosePoison(int index) {

        int name = Integer.parseInt(((Label) ((AnchorPane) grid.getChildren().get(index)).getChildren().get(2)).getText());
        String info = DataForInterface.settingsItems[name];

        String skill = ((String[]) ((String[]) info.split(" +"))[3].split(":"))[1];

        String nameSkill = ((String[]) skill.split("-"))[0];
        int numSkill =Integer.parseInt(((String[]) skill.split("-"))[1]);

        switch (skill) {
            case "heal-10" -> {
                if (DataScriptFloby.myHealthNow + 10 <= DataScriptFloby.myHealthMax) DataScriptFloby.myHealthNow += 10;
                else DataScriptFloby.myHealthNow = DataScriptFloby.myHealthMax;
                try {
                    mediaPlayer = new MediaPlayer(new Media((new File("src/main/sounds/inventar/healPoison.mp3")).toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }


        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.play();
        }
        ScriptMain.mustCorrect = true;

        int saveDeltaH = deltaH, saveDeltaW = deltaW;
        myItems.remove(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)).getText());
        correctPersInfo();
        correctPartOfPage();
        deltaH = saveDeltaH;
        deltaW = saveDeltaW;

        if (deltaH * 5 + deltaW >= myItems.size()) {
            if (deltaW != 0) {
                deltaW--;
            } else if (deltaH != 0) {
                deltaH--;
                deltaW = 4;
            }
        }

        if (!myItems.isEmpty()) chooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
    }

    @FXML
    void right(ActionEvent event) {
        myPage++;

        correctPartOfPage();

        butLeft.setVisible(true);
        if (myItems.size() <= 25 * (myPage + 1)) butRight.setVisible(false);

        correctPersInfo();
    }

    @FXML
    void left(ActionEvent event) {
        myPage--;

        correctPartOfPage();

        butRight.setVisible(true);
        if (myPage == 0) butLeft.setVisible(false);

        correctPersInfo();
    }

    @FXML
    void clickIconFilter(MouseEvent event){
        isActive=false;

        nameFilter = Integer.parseInt(((Label)event.getSource()).getText());
        for (int i=0;i<5;i++){
            if (i+1 == nameFilter)
               ((AnchorPane)gridCollection.getChildren().get(i)).setOpacity(0);
            else
                ((AnchorPane)gridCollection.getChildren().get(i)).setOpacity(1);
        }
        bookmark.setLayoutX((102 + 80 * (nameFilter - 1)) * DataScriptFloby.deltaScreenWindowGame);
        bookmark.setLayoutY(170 * DataScriptFloby.deltaScreenWindowGame);
        try {
            ((ImageView)bookmark.getChildren().get(1)).setImage(new Image(new FileInputStream("src/main/img/inventar/iconCollection/"+nameFilter+".png")));
            ((ImageView)bookmark.getChildren().get(1)).setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
            ((ImageView)bookmark.getChildren().get(1)).setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        correctFilter();
    }

    void correctPartOfPage() {
        numItems = 0;

        isActive = false;

        deltaH = -1;
        deltaW = 0;

        grid.getChildren().removeAll(myPartPage);

        myPartPage = new ArrayList<>();

        int i = 0, j = 0;

        for (int r = 25 * myPage; r < 25 * myPage + 25; r++) {

            if (myItems.size() <= r) break;

            String items = myItems.get(r);

            String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(items)].split(" +"))[2].split(":"))[1];

            if (!(type.equals("resources") && items.equals((r>0) ? myItems.get(r - 1) : "-1"))) {
                try {
                    numItems++;

                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(70 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setHeight(70 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setFill(colorDefaultForNoChooseBlock);
                    /*rectangle.setArcHeight(50 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setArcWidth(50 * DataScriptFloby.deltaScreenWindowGame);*/
                    rectangle.setLayoutY(5 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setLayoutX(5 * DataScriptFloby.deltaScreenWindowGame);

                    ImageView imageViewMain = new ImageView(new Image(new FileInputStream("src/main/img/inventar/system/imgBackSq1.png")));
                    imageViewMain.setFitWidth(80 * DataScriptFloby.deltaScreenWindowGame);
                    imageViewMain.setFitHeight(80 * DataScriptFloby.deltaScreenWindowGame);
                    imageViewMain.setLayoutY(0);
                    imageViewMain.setLayoutX(0);

                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/img/inventar/itemsGraphic/" + items + ".png")));
                    imageView.setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
                    imageView.setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);

                    Label labelCount = new Label();
                    if (type.equals("resources")) {
                        labelCount.setText("" + Collections.frequency(myItems, items));
                        labelCount.setTextFill(Color.rgb(0, 0, 0, 1));
                        labelCount.setId("labelBackGroundCountInventarItems");
                        labelCount.setLayoutX(57 * DataScriptFloby.deltaScreenWindowGame);
                        labelCount.setLayoutY(-10 * DataScriptFloby.deltaScreenWindowGame);
                    }

                    Label label = new Label();
                    label.setText("" + items);
                    label.setTextFill(Color.rgb(0, 0, 0, 0));
                    label.setGraphic(imageView);
                    label.setLayoutX(15 * DataScriptFloby.deltaScreenWindowGame);
                    label.setLayoutY(15 * DataScriptFloby.deltaScreenWindowGame);

                    AnchorPane anchorPane = new AnchorPane();
                    anchorPane.getChildren().addAll(rectangle, imageViewMain, label, labelCount);
                    myPartPage.add(anchorPane);

                    grid.add(anchorPane, i, j);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (i == 4) {
                    i = 0;
                    j++;
                } else {
                    i++;
                }

                if (j == 5) break;
            }
        }
        isActive = true;
    }

    void correctPersInfo() {

        Collections.sort(myItems);

        weight = 0;
        damage = 0;
        frozen = 0;
        armor = 0;

        for (String nameItem : myItems) {
            double thisWeight = Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(nameItem)].split(" +"))[1].split(":"))[1]);
            weight += thisWeight;
        }

        weight += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.namePants)].split(" +"))[1].split(":"))[1]);
        weight += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameShoes)].split(" +"))[1].split(":"))[1]);
        weight += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameSword)].split(" +"))[1].split(":"))[1]);
        weight += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameCloak)].split(" +"))[1].split(":"))[1]);
        weight += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameJacket)].split(" +"))[1].split(":"))[1]);

        damage += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameSword)].split(" +"))[3].split(":"))[1]);

        armor += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameJacket)].split(" +"))[3].split(":"))[1]);
        armor += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameShoes)].split(" +"))[3].split(":"))[1]);
        armor += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.namePants)].split(" +"))[3].split(":"))[1]);
        armor += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameCloak)].split(" +"))[3].split(":"))[1]);

        frozen += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameJacket)].split(" +"))[4].split(":"))[1]);
        frozen += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameShoes)].split(" +"))[4].split(":"))[1]);
        frozen += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.namePants)].split(" +"))[4].split(":"))[1]);
        frozen += Double.parseDouble(((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(DataScriptFloby.nameCloak)].split(" +"))[4].split(":"))[1]);

        String stringWeight = String.format("%.2f", weight);
        String stringArmor = String.format("%.2f", armor);
        String stringDamage = String.format("%.2f", damage);
        String stringFrozen = String.format("%.2f", frozen);

        labWeight.setText("Weight: " + stringWeight);
        labArmor.setText("Armor: " + stringArmor);
        labDamage.setText("Damage: " + stringDamage);
        labFrozen.setText("Frozen: " + stringFrozen);
    }

    void correctFilter(){
        saveItems.addAll(myItems);
        filteredItems = new ArrayList<>();
        switch (nameFilter){
            case (1) -> filteredItems.addAll(saveItems);
            case (2) -> {
                for (String item : saveItems){

                    String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(item)].split(" +"))[2].split(":"))[1];

                    if (type.equals("resources") || type.equals("craft"))
                        filteredItems.add(item);
                }
            }
            case (3) -> {
                for (String item : saveItems){

                    String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(item)].split(" +"))[2].split(":"))[1];

                    if (type.equals("pants") || type.equals("sword") || type.equals("jacket") || type.equals("shoes") || type.equals("cloak"))
                        filteredItems.add(item);
                }
            }
            case (4) -> {
                for (String item : saveItems){

                    String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(item)].split(" +"))[2].split(":"))[1];

                    if (type.equals("story")) filteredItems.add(item);
                }
            }
            case (5) -> {
                for (String item : saveItems){

                    String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(item)].split(" +"))[2].split(":"))[1];

                    if (type.equals("special")) filteredItems.add(item);
                }
            }
        }
        saveItems.removeAll(filteredItems);
        myItems = filteredItems;
        Collections.sort(myItems);
        Collections.sort(saveItems);
        deltaH=-1;
        deltaW=0;
        correctPartOfPage();

        isActive=true;
    }

    void close() {
        mainAnimationTimer.stop();

        DataScriptFloby.nameSword = ((Label) sword.getChildren().get(2)).getText();
        DataScriptFloby.nameShoes = ((Label) shoes.getChildren().get(2)).getText();
        DataScriptFloby.nameCloak = ((Label) cloak.getChildren().get(2)).getText();
        DataScriptFloby.namePants = ((Label) pants.getChildren().get(2)).getText();
        DataScriptFloby.nameJacket = ((Label) jacket.getChildren().get(2)).getText();

        myItems.addAll(saveItems);
        DataScriptFloby.myItems = this.myItems;

        DataScriptFloby.myWeightNow = weight;
        DataScriptFloby.myArmor = armor;
        DataScriptFloby.myDamage = damage;
        DataScriptFloby.defFreezeArmor = frozen;

        ScriptMain.mustCorrect = true;

        ((Stage) ancher.getScene().getWindow()).close();
    }

    /*@FXML
    void enteredBlockGrid(MouseEvent event){
        Label label = (Label) event.getSource();
        chooseLabel(label);
    }

    @FXML
    void exitedBlockGrid(MouseEvent event){
        Label label = (Label) event.getSource();
        noChooseLabel(label);
    }

    @FXML
    void clickedBlockGrid(MouseEvent event) {
        Label label = (Label) event.getSource();
        System.out.println(label.getText());
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            Scene scene = ancher.getScene();
            scene.setCursor(Cursor.DEFAULT);
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    close();
                } else {
                    if (!pressedBut.contains(e.getCode())) {
                        pressedBut.add(e.getCode());
                    }
                }
            });
            scene.setOnKeyReleased(e -> pressedBut.remove(e.getCode()));
        });

        mainPart.add(grid);
        mainPart.add(butRight);
        mainPart.add(butLeft);
        mainPart.add(shoes);
        mainPart.add(cloak);
        mainPart.add(jacket);
        mainPart.add(pants);
        mainPart.add(sword);
        mainPart.add(labWeight);
        mainPart.add(labFrozen);
        mainPart.add(labArmor);
        mainPart.add(labDamage);
        mainPart.add(backgroundGridMainPart);
        mainPart.add(gridCollection);
        mainPart.add(backgroundPersMainPart);
        mainPart.add(progressWaitENTER);
        mainPart.add(progressWaitQ);
        mainPart.add(imagePers);
        mainPart.add(imgBackground);

        mainPartForPlayer.add(shoes);
        mainPartForPlayer.add(cloak);
        mainPartForPlayer.add(jacket);
        mainPartForPlayer.add(pants);
        mainPartForPlayer.add(sword);

        for (Node node : mainPart) {
            node.setLayoutX(node.getLayoutX() * DataScriptFloby.deltaScreenWindowGame);
            node.setLayoutY(node.getLayoutY() * DataScriptFloby.deltaScreenWindowGame);
        }

        grid.setPrefHeight(grid.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        grid.setPrefWidth(grid.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        gridCollection.setPrefHeight(gridCollection.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        gridCollection.setPrefWidth(gridCollection.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);
        backgroundGridMainPart.setFitHeight(backgroundGridMainPart.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        backgroundGridMainPart.setFitWidth(backgroundGridMainPart.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        backgroundPersMainPart.setFitHeight(backgroundPersMainPart.getFitHeight() * DataScriptFloby.deltaScreenWindowGame);
        backgroundPersMainPart.setFitWidth(backgroundPersMainPart.getFitWidth() * DataScriptFloby.deltaScreenWindowGame);
        bookmark.setPrefHeight(bookmark.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
        bookmark.setPrefWidth(bookmark.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);

        try {
            ((ImageView)bookmark.getChildren().get(0)).setImage(new Image(new FileInputStream("src/main/img/inventar/system/bookmarkForInventar.png")));
            ((ImageView)bookmark.getChildren().get(0)).setFitWidth(74 * DataScriptFloby.deltaScreenWindowGame);
            ((ImageView)bookmark.getChildren().get(0)).setFitHeight(90 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        butRight.setFocusTraversable(false);
        butLeft.setFocusTraversable(false);

        try {
            backgroundGridMainPart.setImage(new Image(new FileInputStream("src/main/img/inventar/system/backgroundGridMainPart.jpg")));
            backgroundPersMainPart.setImage(new Image(new FileInputStream("src/main/img/inventar/system/backgroundGridPersPart.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (AnchorPane anchorPane : mainPartForPlayer) {

            String items = "";

            if (shoes.equals(anchorPane)) {
                items = DataScriptFloby.nameShoes;
            } else if (cloak.equals(anchorPane)) {
                items = DataScriptFloby.nameCloak;
            } else if (jacket.equals(anchorPane)) {
                items = DataScriptFloby.nameJacket;
            } else if (pants.equals(anchorPane)) {
                items = DataScriptFloby.namePants;
            } else if (sword.equals(anchorPane)) {
                items = DataScriptFloby.nameSword;
            }

            try {

                anchorPane.setPrefHeight(anchorPane.getPrefHeight() * DataScriptFloby.deltaScreenWindowGame);
                anchorPane.setPrefWidth(anchorPane.getPrefWidth() * DataScriptFloby.deltaScreenWindowGame);

                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(90 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setHeight(90 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setFill(colorDefaultForNoChooseBlock);
                rectangle.setLayoutY(5 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setLayoutX(5 * DataScriptFloby.deltaScreenWindowGame);

                ImageView imageViewMain = new ImageView(new Image(new FileInputStream("src/main/img/inventar/system/imgBackSq2.png")));
                imageViewMain.setFitHeight(100 * DataScriptFloby.deltaScreenWindowGame);
                imageViewMain.setFitWidth(100 * DataScriptFloby.deltaScreenWindowGame);
                imageViewMain.setLayoutY(0);
                imageViewMain.setLayoutX(0);

                ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/img/inventar/itemsGraphic/" + items + ".png")));
                imageView.setFitWidth(80 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setFitHeight(80 * DataScriptFloby.deltaScreenWindowGame);

                Label label = new Label();
                label.setText("" + items);
                label.setTextFill(Color.rgb(0, 0, 0, 0));
                label.setGraphic(imageView);
                label.setLayoutX(10 * DataScriptFloby.deltaScreenWindowGame);
                label.setLayoutY(10 * DataScriptFloby.deltaScreenWindowGame);

                anchorPane.getChildren().setAll(rectangle, imageViewMain, label);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            imagePers.setImage(new Image(new FileInputStream("src/main/animation/mainHero/Floby/simple.png")));
            imagePers.setScaleX(-1);
            imagePers.setFitHeight(300 * DataScriptFloby.deltaScreenWindowGame);
            imagePers.setFitWidth(300 * DataScriptFloby.deltaScreenWindowGame);
            imagePers.setLayoutX(770 * DataScriptFloby.deltaScreenWindowGame);
            imagePers.setLayoutY(160 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            imgBackground.setImage(new Image(new FileInputStream("src/main/img/inventar/backgrounds/1.png")));
            imgBackground.setLayoutX(0);
            imgBackground.setLayoutY(0);
            imgBackground.setFitWidth(1280 * DataScriptFloby.deltaScreenWindowGame);
            imgBackground.setFitHeight(720 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Collections.sort(myItems);

        butLeft.setVisible(false);
        if (myItems.size() <= 25) butRight.setVisible(false);

        int i = 0, j = 0;

        for (int r=0;r<25;r++) {

            if (r>=myItems.size()) break;

            String items =  myItems.get(r);

            String type =((String[]) ((String[]) DataForInterface.settingsItems[Integer.parseInt(items)].split(" +"))[2].split(":"))[1];

            if (!(type.equals("resources") && items.equals((r>0) ? myItems.get(r - 1) : "-1"))) {
                try {
                    numItems++;

                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(70 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setHeight(70 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setFill(colorDefaultForNoChooseBlock);
                    /*rectangle.setArcHeight(50 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setArcWidth(50 * DataScriptFloby.deltaScreenWindowGame);*/
                    rectangle.setLayoutY(5 * DataScriptFloby.deltaScreenWindowGame);
                    rectangle.setLayoutX(5 * DataScriptFloby.deltaScreenWindowGame);

                    ImageView imageViewMain = new ImageView(new Image(new FileInputStream("src/main/img/inventar/system/imgBackSq1.png")));
                    imageViewMain.setFitWidth(80 * DataScriptFloby.deltaScreenWindowGame);
                    imageViewMain.setFitHeight(80 * DataScriptFloby.deltaScreenWindowGame);
                    imageViewMain.setLayoutY(0);
                    imageViewMain.setLayoutX(0);

                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/img/inventar/itemsGraphic/" + items + ".png")));
                    imageView.setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
                    imageView.setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);

                    Label labelCount = new Label();
                    if (type.equals("resources")) {
                        labelCount.setText("" + Collections.frequency(myItems, items));
                        labelCount.setTextFill(Color.rgb(0, 0, 0, 1));
                        labelCount.setId("labelBackGroundCountInventarItems");
                        labelCount.setLayoutX(57 * DataScriptFloby.deltaScreenWindowGame);
                        labelCount.setLayoutY(-10 * DataScriptFloby.deltaScreenWindowGame);
                    }

                    Label label = new Label();
                    label.setText("" + items);
                    label.setTextFill(Color.rgb(0, 0, 0, 0));
                    label.setGraphic(imageView);
                    label.setLayoutX(15 * DataScriptFloby.deltaScreenWindowGame);
                    label.setLayoutY(15 * DataScriptFloby.deltaScreenWindowGame);

                    AnchorPane anchorPane = new AnchorPane();
                    anchorPane.getChildren().addAll(rectangle, imageViewMain, label, labelCount);
                    myPartPage.add(anchorPane);

                    grid.add(anchorPane, i, j);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (i == 4) {
                    i = 0;
                    j++;
                } else {
                    i++;
                }

                if (j == 5) break;
            }
        }

        for (int r=0;r<5;r++){

            try {

                /*Rectangle rectangle = new Rectangle();
                rectangle.setWidth(74 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setHeight(145 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setLayoutY(3 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setLayoutX(3 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setArcWidth(25 * DataScriptFloby.deltaScreenWindowGame);
                rectangle.setArcHeight(25 * DataScriptFloby.deltaScreenWindowGame);
                if (r == 0) rectangle.setFill(colorDefaultChoseBackground);
                else rectangle.setFill(colorDefaultNoChoseBackground);*/

                ImageView imageViewMain = new ImageView(new Image(new FileInputStream("src/main/img/inventar/system/bookmarkForInventar.png")));
                imageViewMain.setPreserveRatio(false);
                imageViewMain.setFitWidth(74 * DataScriptFloby.deltaScreenWindowGame);
                imageViewMain.setFitHeight(75 * DataScriptFloby.deltaScreenWindowGame);
                imageViewMain.setLayoutX(0);
                imageViewMain.setLayoutY(0);

                ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/img/inventar/iconCollection/"+(r+1)+".png")));
                imageView.setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
                imageView.setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);

                Label label = new Label();
                label.setGraphic(imageView);
                label.setLayoutX(13 * DataScriptFloby.deltaScreenWindowGame);
                label.setLayoutY(15 * DataScriptFloby.deltaScreenWindowGame);
                label.setText(""+(r+1));
                label.setTextFill(Color.rgb(0,0,0,0));

                label.setOnMouseClicked(this::clickIconFilter);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().addAll(imageViewMain, label);
                if (r==0) anchorPane.setOpacity(0);

                gridCollection.add(anchorPane, r, 0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        bookmark.setLayoutX(102 * DataScriptFloby.deltaScreenWindowGame);
        bookmark.setLayoutY(170 * DataScriptFloby.deltaScreenWindowGame);
        try {
            ((ImageView)bookmark.getChildren().get(1)).setImage(new Image(new FileInputStream("src/main/img/inventar/iconCollection/1.png")));
            ((ImageView)bookmark.getChildren().get(1)).setFitWidth(50 * DataScriptFloby.deltaScreenWindowGame);
            ((ImageView)bookmark.getChildren().get(1)).setFitHeight(50 * DataScriptFloby.deltaScreenWindowGame);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mainAnimation();
        correctPersInfo();

    }

    void mainAnimation() {

        mainAnimationTimer = new AnimationTimer() {

            int waitEnter = 0;
            int waitQ = 0;

            boolean isAnimation = false;
            boolean removeSomething = false;
            boolean startTimer = false;

            @Override
            public void handle(long l) {
                if (!myItems.isEmpty()) {
                    if (isActive) {

                        if (!isAnimation) {

                            if (deltaH == -1) {
                                isAnimation = true;
                                startTimer = true;
                                deltaH = 0;
                                deltaW = 0;
                            } else {

                                if (pressedBut.contains(KeyCode.ENTER)) {
                                    String item = DataForInterface.settingsItems[Integer.parseInt(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)).getText())];
                                    int type = Integer.parseInt(((String[]) ((String[]) item.split(" +"))[0].split(":"))[1]);
                                    if (type == 1) {
                                        if (waitEnter != 20) {
                                            waitEnter++;

                                            if (waitQ != 0) {
                                                waitQ--;
                                            }
                                        } else {
                                            isAnimation = true;
                                            startTimer = true;
                                            chooseItems(deltaH * 5 + deltaW);
                                            waitEnter = 0;
                                        }
                                    }
                                    String appointment =  ((String[]) ((String[]) item.split(" +"))[2].split(":"))[1];
                                    if (appointment.equals("poison")){
                                        if (waitEnter != 20) {
                                            waitEnter++;

                                            if (waitQ != 0) {
                                                waitQ--;
                                            }
                                        } else {
                                            isAnimation = true;
                                            startTimer = true;
                                            choosePoison(deltaH * 5 + deltaW);
                                            waitEnter = 0;
                                        }
                                    }
                                } else {
                                    if (waitEnter != 0) {
                                        waitEnter--;
                                    }

                                    if (pressedBut.contains(KeyCode.Q)) {
                                        String item = DataForInterface.settingsItems[Integer.parseInt(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)).getText())];
                                        int type = Integer.parseInt(((String[]) ((String[]) item.split(" +"))[0].split(":"))[1]);
                                        if (type != 0) {
                                            if (waitQ != 20) {
                                                waitQ++;
                                            } else {
                                                isAnimation = true;
                                                removeSomething = true;
                                                int saveDeltaH = deltaH, saveDeltaW = deltaW;
                                                myItems.remove(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)).getText());
                                                correctPersInfo();
                                                correctPartOfPage();
                                                deltaH = saveDeltaH;
                                                deltaW = saveDeltaW;

                                                if (deltaH * 5 + deltaW >= myItems.size()) {
                                                    if (deltaW != 0) {
                                                        deltaW--;
                                                    } else if (deltaH != 0) {
                                                        deltaH--;
                                                        deltaW = 4;
                                                    }
                                                }

                                                if (!myItems.isEmpty()) chooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));

                                                startTimer = true;

                                                waitQ = 0;
                                            }
                                        }
                                    } else {

                                        if (waitQ != 0) {
                                            waitQ--;
                                        }

                                        if (true) {

                                            if (pressedBut.contains(KeyCode.S)) {
                                                isAnimation = true;
                                                noChooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
                                                do {
                                                    if (deltaH < 4) deltaH++;
                                                    else deltaH = 0;
                                                } while (deltaH * 5 + deltaW >= numItems);
                                                startTimer = true;
                                            }

                                            if (pressedBut.contains(KeyCode.W)) {
                                                isAnimation = true;

                                                noChooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
                                                do {
                                                    if (deltaH > 0) deltaH--;
                                                    else deltaH = 4;
                                                } while (deltaH * 5 + deltaW >= numItems);
                                                startTimer = true;
                                            }

                                            if (pressedBut.contains(KeyCode.D)) {
                                                isAnimation = true;
                                                noChooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
                                                do {
                                                    if (deltaW < 4) deltaW++;
                                                    else deltaW = 0;
                                                } while (deltaH * 5 + deltaW >= numItems);
                                                startTimer = true;
                                            }

                                            if (pressedBut.contains(KeyCode.A)) {
                                                isAnimation = true;
                                                noChooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
                                                do {
                                                    if (deltaW > 0) deltaW--;
                                                    else deltaW = 4;
                                                } while (deltaH * 5 + deltaW >= numItems);
                                                startTimer = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (startTimer) {
                            if (isAnimation && !removeSomething) {
                                startTimer = false;
                                if (deltaH != -1)
                                    chooseLabel(((Label) ((AnchorPane) grid.getChildren().get(deltaH * 5 + deltaW)).getChildren().get(2)));
                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j == 1) {
                                            isAnimation = false;
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 150);
                            } else if (removeSomething) {
                                startTimer = false;
                                Timer timer = new Timer();

                                timer.schedule(new TimerTask() {
                                    int j = 0;

                                    @Override
                                    public void run() {
                                        if (j == 1) {
                                            isAnimation = false;
                                            removeSomething = false;
                                            timer.cancel();
                                            return;
                                        }
                                        j++;
                                    }
                                }, 0, 150);
                            }
                        }

                        progressWaitENTER.setProgress(waitEnter / 20.0);
                        progressWaitQ.setProgress(waitQ / 20.0);
                    }
                }
            }
        };
        mainAnimationTimer.start();
    }
}
