package fi.tuni.comp_se_110.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * This is the root class of the Application.
 * It's responsible for keeping track of global variables and creating the UI.
 * @author Akseli Alapeltola
 */
public class App extends Application {
    
    private HashMap<PreferenceType, HashMap<String, HashMap<String, String>>> preferences = new HashMap();
    public RootController rootViewController;
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 740, 480);
        rootViewController = fxmlLoader.getController();
        rootViewController.initializeRoot(this);
        stage.setTitle("WeatherApp");
        stage.setScene(scene);
        stage.show();
    }
    
    public void addNewPreference(PreferenceType type, String name, HashMap<String, String> data) {
        if(preferences.get(type) == null) {
            preferences.put(type, new HashMap());
        }
        preferences.get(type).put(name, data);
    }
    
    public String[] getPreferenceNames(PreferenceType type) {
        if(!preferences.containsKey(type)) {
            return null;
        }
        HashMap<String, HashMap<String, String>> selected = preferences.get(type);
        String[] preferenceNames = selected.keySet().toArray(new String[selected.size()]);
        return preferenceNames;
    }
    
    public HashMap<String, String> getPreference(PreferenceType type, String name) {
        return preferences.get(type).get(name);
    }

    public static void main(String[] args) {
        launch();
    }

}