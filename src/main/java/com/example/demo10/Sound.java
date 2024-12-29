package com.example.demo10;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Sound {
    MediaView mediaView;
    MediaPlayer mediaPlayer;
    public Sound(String music,Pane root) {
        Media media = new Media(new File(music).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
