package com.example.demo10;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ChickenAnimation {
    private final ImageView imageView;
    private final Image[] frames;
    private int currentFrame;

    public ChickenAnimation(double width, double height) {

        frames = new Image[]{
                new Image(getClass().getResource("/chicken.png").toExternalForm()),
                new Image(getClass().getResource("/chicken2.png").toExternalForm())
        };

        imageView = new ImageView(frames[0]);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), e -> nextFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

    }


    private void nextFrame() {
        currentFrame = (currentFrame + 1) % frames.length;
        imageView.setImage(frames[currentFrame]);
    }

}
