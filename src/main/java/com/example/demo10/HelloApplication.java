package com.example.demo10;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Menu menuController = new Menu(primaryStage);
        menuController.initMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
