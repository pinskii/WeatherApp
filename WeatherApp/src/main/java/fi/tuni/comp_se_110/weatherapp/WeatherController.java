package fi.tuni.comp_se_110.weatherapp;

/**
 * @author Pinja Rontu
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class WeatherController {
    @FXML
    private TextField locationTextField;
    @FXML
    private DatePicker sctDayDatePicker;
    @FXML
    private ToggleGroup graph;
    @FXML
    private AnchorPane tempChart;
    
    private String selectedValue;
    private String location;
    private LocalDate localDate;
    
    private RootController parentController;
    private HashMap<String, HashMap<String, String>> options = new HashMap();

    public void updateLocation() {
        
    }
    
    public void updateDate() {
        
    }
    
    public void updateSelectedView() {
        
    }
    
    
    public void setLocation() {
        
    }
    
    public void setDate() {
        
    }
    
    public void setTableContent() {
        
    }
    
    public void initializeParentController(RootController parentController) {
        this.parentController = parentController;
    }
    
    public void setGraphContent() {
        RadioButton selectedRadioButton = (RadioButton) graph.getSelectedToggle();
        selectedValue = selectedRadioButton.getText();
        location = locationTextField.getText();
        localDate = sctDayDatePicker.getValue();
            
        if(location.isEmpty() || localDate == null) {
            if(location.isEmpty() && localDate == null){
                // alert about no location
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Choose a location and a date!");
                alert.showAndWait();
            }
            else if(localDate == null) {
                // alert about no date
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Choose a date!");
                alert.showAndWait();
            } else if(location.isEmpty()) {
                // alert about both missing
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Type a location!");
                alert.showAndWait();
            }
        } 
        else if (localDate.isAfter(LocalDate.now()) && (selectedValue.equals("daily min and max") || selectedValue.equals("daily average"))){
            // alert about choosing a earlier date
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a date in the past!");
            alert.showAndWait();
        } else if (localDate.isBefore(LocalDate.now())&& !(selectedValue.equals("daily min and max") || selectedValue.equals("daily average"))){
            // alert about choosing a later date
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a date in the future!");
            alert.showAndWait();
        } else {
            try {
                tempChart.getChildren().clear();
                LineChart lineChart = WeatherModel.drawGraph(selectedValue, location, localDate);
                tempChart.getChildren().add(lineChart);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private HashMap<String, String> getOptions() {
        HashMap<String, String> newOption = new HashMap();
        newOption.put("location", locationTextField.getText());
        newOption.put("date", sctDayDatePicker.getValue().toString());
        newOption.put("selected", ((RadioButton)graph.getSelectedToggle()).getText());
        return newOption;
    }
    
    public void loadPreference() {
        String[] preferenceNames = parentController.getPreferenceNames(PreferenceType.WEATHER);
        if(preferenceNames == null) {
            return;
        }
        ChoiceDialog cd = new ChoiceDialog(preferenceNames[0], preferenceNames);
        cd.setTitle("Preference");
        cd.setHeaderText("Select preference.");
        cd.showAndWait();
        String selected = cd.getResult().toString();
        setPreference(selected);
        setGraphContent();
    }
    
    public void setPreference(String name) {
        HashMap<String, String> preference = parentController.getPreference(PreferenceType.WEATHER, name);
        if(preference != null) {         
            locationTextField.setText(preference.get("location"));
            sctDayDatePicker.setValue(LocalDate.parse(preference.get("date"), DateTimeFormatter.ISO_DATE));
            for(Toggle toggle : graph.getToggles()) {
                if(((RadioButton) toggle).getText().equals(preference.get("selected"))){
                    graph.selectToggle(toggle);
                }
            }
        }
    }
    
    public void savePreference() {
        TextInputDialog td = new TextInputDialog("Preference");
        td.setTitle("Preference");
        td.setHeaderText("Enter preference name.");
        td.showAndWait();
        String preferenceName = td.getResult();
        HashMap<String, String> options = getOptions();
        parentController.addNewPreference(PreferenceType.WEATHER, preferenceName, options);
    }
}
