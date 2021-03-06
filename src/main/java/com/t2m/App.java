package com.t2m;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/NewView.fxml"));

        Scene s = new Scene(root, 800, 600);

        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}