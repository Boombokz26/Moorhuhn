package com.example.demo10;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Details {
    private int score;
    private int ammo;
    private final int maxAmmo;

    public Details() {
        this.score = 0;
        this.ammo = 6;
        this.maxAmmo = 6;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getAmmo() {
        return ammo;
    }
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void reload() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),e->{

        }));
        timeline.play();
        timeline.setOnFinished(e->{
            this.ammo = maxAmmo;
        });


    }
    public int getMaxAmmo() {
        return maxAmmo;
    }

}
