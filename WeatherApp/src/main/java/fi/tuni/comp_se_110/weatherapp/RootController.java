package fi.tuni.comp_se_110.weatherapp;

import fi.tuni.comp_se_110.weatherapp.App;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Akseli Alapeltola
 */

public class RootController {

    public AnchorPane weatherAnchorPane;
    public AnchorPane roadAnchorPane;
    public AnchorPane combinedAnchorPane;
    public AnchorPane settingsAnchorPane;
    
    private WeatherController weatherController;
    private RoadConditionController roadConditionController;
    private CombinedController combinedController;
    private SettingsController settingsController;
    
    private App root;

    /**
     * Load and sets all the other views.
     */
    @FXML
    public void initialize() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("weather.fxml"));
        SubScene weatherScene = null; 
        try {
            weatherScene = new SubScene(fxmlLoader.load(), 640, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        weatherController = fxmlLoader.getController();
        weatherController.initializeParentController(this);
        weatherAnchorPane.getChildren().add(weatherScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("roadCondition.fxml"));
        SubScene roadConditionScene = null;
        try {
            roadConditionScene = new SubScene(fxmlLoader.load(), 640, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        roadConditionController = fxmlLoader.getController();
        roadAnchorPane.getChildren().add(roadConditionScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("combined.fxml"));
        SubScene combinedScene = null;
        try {
            combinedScene = new SubScene(fxmlLoader.load(), 640, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        combinedController = fxmlLoader.getController();
        combinedAnchorPane.getChildren().add(combinedScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("settings.fxml"));
        SubScene settingsScene = null;
        try {
            settingsScene = new SubScene(fxmlLoader.load(), 640, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        settingsController = fxmlLoader.getController();
        settingsAnchorPane.getChildren().add(settingsScene);
    }
    
    public void initializeRoot(App root) {
        this.root = root;
    }
    
    public void addNewPreference(PreferenceType type, String name, HashMap<String, String> data) {
        root.addNewPreference(type, name, data);
    }
    
    public String[] getPreferenceNames(PreferenceType type) {
        return root.getPreferenceNames(type);
    }
    
    public HashMap<String, String> getPreference(PreferenceType type, String name) {
        return root.getPreference(type, name);
    }
    
}
