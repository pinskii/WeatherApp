
package fi.tuni.comp_se_110.weatherapp;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SettingsController extends App{
    
    @FXML
    private AnchorPane anchorPane;
    
    private RootController parentController;
    
    public void saveDataset() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save dataset");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Dataset", "*.dataset"));
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        parentController.saveDataset(file);
    }
    
    public void loadDataset() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load dataset");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Dataset", "*.dataset"));
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        parentController.loadDataset(file);
    }
    
    public void initializeParentController(RootController parentController) {
        this.parentController = parentController;
    }
    
}
