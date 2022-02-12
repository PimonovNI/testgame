package com.example.demo.scripts;

import com.example.demo.flobyLVL.snowYar.ControllerLVL1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class DataScriptFloby {

    public static int indexLocation = 0;
    public static int thisLocation = 0;

    public static double widthScreenWindowGame = 0;
    public static double heightScreenWindowGame = 0;
    public static double deltaScreenWindowGame = 0;

    public static double defaultSpeed = 5;
    public static double myHealthNow = 20;
    public static double myHealthMax = 20;
    public static double myStaminaNow = 200;
    public static double myStaminaMax = 200;
    public static double myStaminaAdded = 2;
    public static double myWeightNow = 10;
    public static double myWeightMax = 100;
    public static double myDamage = 10;
    public static double myArmor = 10;

    public static int myLVL = 0;
    public static double myEXPNow = 0;
    public static double[] numEXPForNextLVL = {10, 100, 200};

    public static boolean isExitedTheGame = false;

    public static int layoutStartXPers = 1800;
    public static int layoutStartYPers = 560;

    public static double myFreezeHeals = 0;
    public static double defFreezeArmor = 10;
    public static double[] isCoolInThisLocation = {0.02};

    public static ArrayList <String> myItems = new ArrayList<>();
    public static String nameSword = "12";
    public static String nameJacket = "14";
    public static String namePants = "15";
    public static String nameCloak = "13";
    public static String nameShoes = "16";

    public static ArrayList <String> myDrawing = new ArrayList<>();

    public static String[] chest = {
            "",
            "1:1,2:1 1",
            ""
    };

    public static int savePoint = 1;

    public static void saveDataGame(){
        DataSave dataSave = new DataSave(indexLocation, thisLocation, defaultSpeed, myHealthNow, myHealthMax, myStaminaNow,
        myStaminaMax, myStaminaAdded, myWeightNow, myWeightMax, myDamage, myArmor,
        myLVL, myEXPNow, myFreezeHeals, defFreezeArmor, nameSword, nameJacket,
                namePants, nameCloak, nameShoes, myItems, myDrawing, savePoint);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("data/data.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(dataSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void loadDataGame(){
        try {
            FileInputStream fileInputStream = new FileInputStream("data/data.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            DataSave saveDataGame = (DataSave) objectInputStream.readObject();


            DataScriptFloby.indexLocation = saveDataGame.indexLocation;
            DataScriptFloby.thisLocation = saveDataGame.thisLocation;
            DataScriptFloby.defaultSpeed = saveDataGame.defaultSpeed;
            DataScriptFloby.myHealthNow = saveDataGame.myHealthNow;
            DataScriptFloby.myHealthMax = saveDataGame.myHealthMax;
            DataScriptFloby.myStaminaNow = saveDataGame.myStaminaNow;
            DataScriptFloby.myStaminaMax = saveDataGame.myStaminaMax;
            DataScriptFloby.myStaminaAdded = saveDataGame.myStaminaAdded;
            DataScriptFloby.myWeightNow = saveDataGame.myWeightNow;
            DataScriptFloby.myWeightMax = saveDataGame.myWeightMax;
            DataScriptFloby.myDamage = saveDataGame.myDamage;
            DataScriptFloby.myArmor = saveDataGame.myArmor;
            DataScriptFloby.myLVL = saveDataGame.myLVL;
            DataScriptFloby.myEXPNow = saveDataGame.myEXPNow;
            DataScriptFloby.myFreezeHeals = saveDataGame.myFreezeHeals;
            DataScriptFloby.defFreezeArmor = saveDataGame.defFreezeArmor;
            DataScriptFloby.nameSword = saveDataGame.nameSword;
            DataScriptFloby.nameJacket = saveDataGame.nameJacket;
            DataScriptFloby.namePants = saveDataGame.namePants;
            DataScriptFloby.nameCloak = saveDataGame.nameCloak;
            DataScriptFloby.nameShoes = saveDataGame.nameShoes;
            DataScriptFloby.myItems = saveDataGame.myItems;
            DataScriptFloby.myDrawing = saveDataGame.myDrawing;
            DataScriptFloby.savePoint = saveDataGame.savePoint;

            ScriptMain.mustCorrect = true;

            fileInputStream.close();
            objectInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static  void loadSavedScene(Stage stage){

        String name = "";

        switch (DataScriptFloby.savePoint){
            case (1) -> {
                name = "lvl0.fxml";
                DataScriptFloby.layoutStartXPers = 1800;
                DataScriptFloby.layoutStartYPers = 560;
            }
            case (2) -> {
                name = "lvl2.fxml";
                DataScriptFloby.layoutStartXPers = 300;
                DataScriptFloby.layoutStartYPers = 800;
            }
            case (3) -> {
                name = "lvl7.fxml";
                DataScriptFloby.layoutStartXPers = 300;
                DataScriptFloby.layoutStartYPers = 1350;
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLVL1.class.getResource(name));

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
