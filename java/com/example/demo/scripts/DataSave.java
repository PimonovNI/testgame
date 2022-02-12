package com.example.demo.scripts;

import java.io.Serializable;
import java.util.ArrayList;

public class DataSave implements Serializable {

    private static final long serialVersionUID = 12345L;

    public int indexLocation;
    public int thisLocation;

    public double defaultSpeed;
    public double myHealthNow;
    public double myHealthMax;
    public double myStaminaNow;
    public double myStaminaMax;
    public double myStaminaAdded;
    public double myWeightNow;
    public double myWeightMax;
    public double myDamage;
    public double myArmor;

    public int myLVL;
    public double myEXPNow;

    public double myFreezeHeals;
    public double defFreezeArmor;

    public ArrayList<String> myItems;
    public String nameSword;
    public String nameJacket;
    public String namePants ;
    public String nameCloak;
    public String nameShoes;

    public ArrayList <String> myDrawing;
    public

    int savePoint;

    DataSave (int indexLocation, int thisLocation, double defaultSpeed, double myHealthNow, double myHealthMax, double myStaminaNow,
              double myStaminaMax, double myStaminaAdded, double myWeightNow, double myWeightMax, double myDamage, double myArmor,
              int myLVL, double myEXPNow, double myFreezeHeals, double defFreezeArmor, String nameSword, String nameJacket,
              String namePants, String nameCloak, String nameShoes, ArrayList<String> myItems, ArrayList<String> myDrawing, int savePoint){

        this.indexLocation = indexLocation;
        this.thisLocation = thisLocation;
        this.defaultSpeed = defaultSpeed;
        this.myHealthNow = myHealthNow;
        this.myHealthMax = myHealthMax;
        this.myStaminaNow = myStaminaNow;
        this.myStaminaMax = myStaminaMax;
        this.myStaminaAdded = myStaminaAdded;
        this.myWeightNow = myWeightNow;
        this.myWeightMax = myWeightMax;
        this.myDamage = myDamage;
        this.myArmor = myArmor;
        this.myLVL = myLVL;
        this.myEXPNow = myEXPNow;
        this.myFreezeHeals = myFreezeHeals;
        this.defFreezeArmor = defFreezeArmor;
        this.nameSword = nameSword;
        this.nameJacket = nameJacket;
        this.namePants = namePants;
        this.nameCloak = nameCloak;
        this.nameShoes = nameShoes;
        this.myItems = myItems;
        this.myDrawing = myDrawing;
        this.savePoint = savePoint;

    }

}
