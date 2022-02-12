package com.example.demo.scripts;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DataGraphics {

    public static ImageView slowSnow;

    public static void setBackGroundAnimation() {
        if (DataScriptFloby.thisLocation <= 7){
            try {
                slowSnow = new ImageView(new Image(new FileInputStream("src/main/animation/background/main/snow/slowSnow.gif")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
