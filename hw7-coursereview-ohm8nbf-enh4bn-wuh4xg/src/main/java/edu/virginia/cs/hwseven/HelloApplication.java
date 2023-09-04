package edu.virginia.cs.hwseven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader login = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(login.load(), 560, 800);
        stage.setTitle("CourseView");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}