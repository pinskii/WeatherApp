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
    
    public void setGraphContent() {
        RadioButton selectedRadioButton = (RadioButton) graph.getSelectedToggle();
        selectedValue = selectedRadioButton.getText();
        
        location = locationTextField.getText();
        
        localDate = sctDayDatePicker.getValue();
        
        try {
            tempChart.getChildren().clear();
            LineChart lineChart = WeatherModel.drawGraph(selectedValue, location, localDate);
            tempChart.getChildren().add(lineChart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void initializeParentController(RootController parentController) {
        this.parentController = parentController;
    }
    
    public Map<String, String> getOptions() {
        Map<String, String> newOption = new HashMap();
        newOption.put("location", locationTextField.getText());
        newOption.put("date", sctDayDatePicker.getValue().toString());
        newOption.put("selected", ((RadioButton)graph.getSelectedToggle()).getText());
        return newOption;
    }
    
    HashMap<String, String> getHashOptions() {
        HashMap<String, String> newOption = new HashMap();
        newOption.put("location", locationTextField.getText());
        newOption.put("date", sctDayDatePicker.getValue().toString());
        newOption.put("selected", ((RadioButton)graph.getSelectedToggle()).getText());
        return newOption;
    }
    
    public void loadPreference() {
        String selected = parentController.selectPreference(PreferenceType.WEATHER);
        setPreference(selected);
   
    }
    
    public void setPreference(String name) {
        Map<String, String> preference = parentController.getPreference(PreferenceType.WEATHER, name);
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
    
    public void setPreference(Map<String, String> preference) {
        if(preference != null) {         
            locationTextField.setText(preference.get("location"));
            sctDayDatePicker.setValue(LocalDate.parse(preference.get("date"), DateTimeFormatter.ISO_DATE));
            for(Toggle toggle : graph.getToggles()) {
                if(((RadioButton) toggle).getText().equals(preference.get("selected"))){
                    graph.selectToggle(toggle);
                }
            }
            setGraphContent();
        }
    }
    
    public void savePreference() {
        parentController.savePreference(PreferenceType.WEATHER, getOptions());
    }
    
    public void test() {
        
    }
    
    public LineChart getLineChart() {
        LineChart lineChart = (LineChart) tempChart.getChildren().get(0);
        return lineChart;
    }
    
    public void getDataset() {
        
    }
    
}
