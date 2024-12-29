package com.example.demo10;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu {
    private Stage stage;
    private Sound sound;
    public int score;
    Details model;
    public Menu(Stage stage) {
        this.stage = stage;
    }

    public void initMenu() {
        Pane root = new Pane();

        sound = new Sound("src/main/resources/music.mp3",root);
        sound.getMediaPlayer().setVolume(0.5);
        sound.getMediaPlayer().play();
        sound.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            stage.close();
            sound.getMediaPlayer().stop();
            Stage gameStage = new Stage();
            model = new Details();
            Game gameController = new Game(model, gameStage);
            gameController.startGame();
        });
        startButton.setLayoutX(150);
        startButton.setLayoutY(120);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            stage.close();
        });
        exitButton.setLayoutX(150);
        exitButton.setLayoutY(150);

        Text text = new Text("Your Score " + score );
        text.setLayoutX(150);
        text.setLayoutY(30);
        root.getChildren().addAll(startButton, exitButton,text);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }
    public void setScore(int score) {
        this.score = score;
    }

}
