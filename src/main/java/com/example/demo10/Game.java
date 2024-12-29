package com.example.demo10;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Details model;
    private Stage stage;
    private Pane root;
    private List<ImageView> chickens;
    private List<ImageView> bullets;
    private Random random;

    private Text scoreText;
    private Text timerText;

    private Timeline chickenSpawner;
    private Timeline gameTimer;
    private int timeLeft = 30;

    private Sound sound_shot;
    private Sound sound_reload;
    private ImageView pistol;
    private ImageView pistolFire;

    public Game(Details model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.root = new Pane();
        this.chickens = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.random = new Random();

        initUI();
    }

    private void initUI() {
        ImageView background = new ImageView(new Image(getClass().getResource("/background.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(600);
        root.getChildren().add(background);
        sound_shot = new Sound("src/main/resources/music2.mp3",root);
        sound_reload = new Sound("src/main/resources/sound_reload.mp3",root);
        sound_shot.getMediaPlayer().setVolume(0.1);

        scoreText = new Text("Score: 0");
        scoreText.setStyle("-fx-font-size: 20; -fx-fill: white;");
        scoreText.setX(10);
        scoreText.setY(20);

        timerText = new Text("Time: 30");
        timerText.setStyle("-fx-font-size: 20; -fx-fill: white;");
        timerText.setX(700);
        timerText.setY(20);

        pistol = new ImageView(new Image(getClass().getResource("/Layer2.png").toExternalForm()));
        pistol.setFitWidth(40);
        pistol.setFitHeight(40);
        pistol.setY(50);
        pistol.setX(10);
        pistol.setVisible(true);

        pistolFire = new ImageView(new Image(getClass().getResource("/Layer1.png").toExternalForm()));
        pistolFire.setFitWidth(40);
        pistolFire.setFitHeight(40);
        pistolFire.setY(50);
        pistolFire.setX(10);
        pistolFire.setVisible(false);

        root.getChildren().addAll(scoreText, timerText,pistol,pistolFire);

        initAmmoDisplay();

        chickenSpawner = new Timeline(new KeyFrame(Duration.seconds(0.7), e -> spawnChicken()));
        chickenSpawner.setCycleCount(Timeline.INDEFINITE);

        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerText.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                endGame();
            }
        }));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
    }

    private void pistolFire(){
        pistol.setVisible(false);
        pistolFire.setVisible(true);
        Timeline pistolFireView = new Timeline(new KeyFrame(Duration.seconds(0.5),e->{}));
        pistolFireView.play();
        pistolFireView.setOnFinished(e->{
            pistolFire.setVisible(false);
            pistol.setVisible(true);
        });
    }

    private void  initAmmoDisplay() {
        double x = 630;
        double y = 550;

        for (int i = 0; i < model.getMaxAmmo(); i++) {
            ImageView bullet = new ImageView(new Image(getClass().getResource("/bullet.png").toExternalForm()));
            bullet.setFitWidth(20);
            bullet.setFitHeight(20);
            bullet.setX(x + i * 25);
            bullet.setY(y);
            bullets.add(bullet);
            root.getChildren().add(bullet);
        }
    }

    private void reloadAmmoDisplay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {}));
        timeline.play();
        timeline.setOnFinished(e -> {
            for(ImageView bullet : bullets) {
                bullet.setVisible(true);
            }
        });

    }


    public void fireBullet() {
        if (model.getAmmo() > 0) {
            model.setAmmo(model.getAmmo() - 1);
            sound_shot.getMediaPlayer().stop();
            sound_shot.getMediaPlayer().play();
            pistolFire();
            if (!bullets.isEmpty()) {
                ImageView bullet = bullets.get(model.getAmmo());
                bullet.setVisible(false);
            }
        }
    }

    public void startGame() {
        Scene scene = new Scene(root, 800, 600);
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                fireBullet();
            }
        });
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                reload();
            }
        });
        chickenSpawner.play();
        gameTimer.play();

        stage.setScene(scene);
        stage.setTitle("Moorhuhn Game");
        stage.show();
    }

    private void spawnChicken() {
        ImageView chicken = new ImageView(new Image(getClass().getResource("/chicken.png").toExternalForm()));
        chicken.setFitWidth(60);
        chicken.setFitHeight(60);
        chicken.setX(0);
        chicken.setY(random.nextInt(540));

        Timeline chickenAnimation = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (chicken.getImage().getUrl().contains("chicken.png")) {
                chicken.setImage(new Image(getClass().getResource("/chicken2.jpg").toExternalForm()));
            } else {
                chicken.setImage(new Image(getClass().getResource("/chicken.png").toExternalForm()));
            }
        }));
        chickenAnimation.setCycleCount(Timeline.INDEFINITE);
        chickenAnimation.play();

        Timeline movement = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            chicken.setX(chicken.getX() + random.nextInt(8) + 1);
            if (chicken.getX() > 800) {
                root.getChildren().remove(chicken);
                chickens.remove(chicken);
            }
        }));
        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();

        chicken.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shootChicken(chicken);

            }

        } );

        chickens.add(chicken);
        root.getChildren().add(chicken);
    }



    private void reload() {
        sound_reload.getMediaPlayer().stop();
        sound_reload.getMediaPlayer().play();
        model.reload();
        reloadAmmoDisplay();
    }

    private void shootChicken(ImageView chicken) {
        if (model.getAmmo() > 0) {
            root.getChildren().remove(chicken);
            chickens.remove(chicken);
            model.addScore(10);
            updateUI();
        }
    }

    private void updateUI() {
        scoreText.setText("Score: " + model.getScore());
    }

    private void endGame() {
        chickenSpawner.stop();
        gameTimer.stop();
        stage.close();

        Stage menuStage = new Stage();
        Menu menuController = new Menu(menuStage);
        menuController.setScore(model.getScore());
        menuController.initMenu();
    }


}
