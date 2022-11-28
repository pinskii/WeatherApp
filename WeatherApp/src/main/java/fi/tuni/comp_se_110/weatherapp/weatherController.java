package fi.tuni.comp_se_110.weatherapp;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

public class WeatherController {
    @FXML
    private TextField locationTextField;
    private DatePicker sctDayDatePicker;
    private RadioButton temperatureRadioButton;
    private RadioButton windRadioButton;
    private RadioButton cloudinessRadioButton;
    private RadioButton dailyAverageRadioButton;
    private RadioButton dailyMinMaxRadioButton;
    private TableView forecastTable;
    private LineChart forecastGraph;
    private LineChart tempMinMaxGraph;
    
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
        
    }
    
    public void setOptions() {
        
    }
    
    public Map getOptionSet() {
        Map optionMap = new HashMap();
        optionMap.put("location", locationTextField.getText());
        optionMap.put("day", sctDayDatePicker.getValue());
        RadioButton[] buttons = {temperatureRadioButton, windRadioButton, cloudinessRadioButton, dailyAverageRadioButton, dailyMinMaxRadioButton};
        String[] keys = {"temperature", "wind", "cloudiness", "average", "minMax"};
        int i = 0;
        for (RadioButton radioBtn : buttons) {
            if (radioBtn.isSelected()) {
                optionMap.put("selected", keys[i]);
            } 
            i++;
        }
        return optionMap;
    }
}
