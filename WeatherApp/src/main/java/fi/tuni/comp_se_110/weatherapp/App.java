package fi.tuni.comp_se_110.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the root class of the Application.
 * It's responsible for keeping track of global variables and creating the UI.
 * @author Akseli Alapeltola
 */
public class App extends Application {
    
    public RootController rootViewController;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        rootViewController = fxmlLoader.getController();
        stage.setTitle("WeatherApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}