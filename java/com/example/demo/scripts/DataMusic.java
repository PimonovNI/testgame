package com.example.demo.scripts;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

public class DataMusic {

    private static MediaPlayer mediaPlayer;
    private static MediaPlayer mediaPlayerMenu;

    public static int typeMusic = 1;
    public static boolean isMusic = false;

    public static boolean isMenuMusic = false;

    public static void startMenuMusic(){
        if (!isMusic){

            playRestingGrounds();

        }
    }

    public static void startMusic(int type){
        if (type == typeMusic) {
            switch (typeMusic) {
                case (1) -> {
                    switch ((int) (Math.random() * 3) + 1) {
                        case (1) -> playFungalWastes();
                        case (2) -> playGreenpath();
                        case (3) -> playWhitePalace();
                    }
                }
                case (2) -> playHornet();
            }
        }
    }

    public static void stopMusic(){
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    public static void stopMenuMusic(){
        if (mediaPlayerMenu != null) mediaPlayerMenu.stop();
    }


    public static void playRestingGrounds() {
        stopMenuMusic();
        try {
            mediaPlayerMenu = new MediaPlayer(new Media((new File("src/main/music/menu/1.mp3").toURI().toURL().toString())));
            mediaPlayerMenu.setVolume(0.5);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int j = 0;

                @Override
                public void run() {

                    if (j == 1) {
                        startMenuMusic();
                        timer.cancel();
                        return;
                    }

                    j++;
                }
            }, 0, 132000);
            mediaPlayerMenu.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void playFungalWastes(){
        stopMusic();
        try {
            mediaPlayer = new MediaPlayer(new Media((new File("src/main/music/simple/1.mp3").toURI().toURL().toString())));
            mediaPlayer.setVolume(0.5);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int j = 0;
                @Override
                public void run() {

                    if (j == 1){
                        startMusic(1);
                        timer.cancel();
                        return;
                    }

                    j++;
                }
            },0, 185000);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void playGreenpath(){
        stopMusic();
        try {
            mediaPlayer = new MediaPlayer(new Media((new File("src/main/music/simple/2.mp3").toURI().toURL().toString())));
            mediaPlayer.setVolume(0.5);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int j = 0;
                @Override
                public void run() {

                    if (j == 1){
                        startMusic(1);
                        timer.cancel();
                        return;
                    }

                    j++;
                }
            },0, 217000);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void playWhitePalace(){
        stopMusic();
        try {
            mediaPlayer = new MediaPlayer(new Media((new File("src/main/music/simple/3.mp3").toURI().toURL().toString())));
            mediaPlayer.setVolume(0.5);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int j = 0;
                @Override
                public void run() {

                    if (j == 1){
                        startMusic(1);
                        timer.cancel();
                        return;
                    }

                    j++;
                }
            },0, 260000);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void playHornet(){
        stopMusic();
        try {
            mediaPlayer = new MediaPlayer(new Media((new File("src/main/music/fight/1.mp3").toURI().toURL().toString())));
            mediaPlayer.setVolume(0.5);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int j = 0;
                @Override
                public void run() {

                    if (j == 1){
                        startMusic(2);
                        timer.cancel();
                        return;
                    }

                    j++;
                }
            },0, 167000);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
