package fi.tuni.comp_se_110.weatherapp;

import fi.tuni.comp_se_110.weatherapp.App;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
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
            weatherScene = new SubScene(fxmlLoader.load(), 740, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        weatherController = fxmlLoader.getController();
        weatherController.initializeParentController(this);
        weatherAnchorPane.getChildren().add(weatherScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("roadCondition.fxml"));
        SubScene roadConditionScene = null;
        try {
            roadConditionScene = new SubScene(fxmlLoader.load(), 740, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        roadConditionController = fxmlLoader.getController();
        roadConditionController.initializeParentController(this);
        roadAnchorPane.getChildren().add(roadConditionScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("combined.fxml"));
        SubScene combinedScene = null;
        try {
            combinedScene = new SubScene(fxmlLoader.load(), 740, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        combinedController = fxmlLoader.getController();
        combinedController.initializeParentController(this);
        combinedAnchorPane.getChildren().add(combinedScene);
        
        fxmlLoader = new FXMLLoader(App.class.getResource("settings.fxml"));
        SubScene settingsScene = null;
        try {
            settingsScene = new SubScene(fxmlLoader.load(), 740, 480);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        settingsController = fxmlLoader.getController();
        settingsController.initializeParentController(this);
        settingsAnchorPane.getChildren().add(settingsScene);
    }
    
    public void initializeRoot(App root) {
        this.root = root;
    }
    
    public void addNewPreference(PreferenceType type, String name, Map<String, String> data) {
        root.addNewPreference(type, name, data);
    }
    
    public String selectPreference(PreferenceType type) {
        String[] preferenceNames = root.getPreferenceNames(type);
        if(preferenceNames != null) {
            ChoiceDialog cd = new ChoiceDialog(preferenceNames[0], preferenceNames);
            cd.setTitle("Preference");
            cd.setHeaderText("Select preference.");
            cd.showAndWait();
            String selected = cd.getResult().toString();
            return selected;
        }
        return null;
    }
    
    public void savePreference(PreferenceType type, Map<String, String> preference) {
        TextInputDialog td = new TextInputDialog("Preference");
        td.setTitle("Preference");
        td.setHeaderText("Enter preference name.");
        td.showAndWait();
        String preferenceName = td.getResult();
        addNewPreference(type, preferenceName, preference);
    }
    
    public Map<String, String> getPreference(PreferenceType type, String name) {
        return root.getPreference(type, name);
    }
    
    public void saveDataset(File file) {
        Map<String, Map<String, String>> dataset = new HashMap();
        dataset.put("weather", weatherController.getOptions());
        dataset.put("road", roadConditionController.getOptions());
        dataset.put("combined", combinedController.getOptions());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(JsonConverter.toJSON(dataset));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadDataset(File file) {;
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch(IOException e) {
            e.printStackTrace();
        }
        Map<String, Map<String, String>> dataset = new HashMap();
        if(content != null) {
            dataset = JsonConverter.getFromJSON(content, Map.class);
        }
        if(dataset.get("weather") != null) {
            weatherController.setPreference(dataset.get("weather"));
        } 
        if(dataset.get("road") != null) {
            roadConditionController.setPreference(dataset.get("road"));
        }
        if(dataset.get("combined") != null) {
            combinedController.setPreference(dataset.get("combined"));
        }
    }
    
}
