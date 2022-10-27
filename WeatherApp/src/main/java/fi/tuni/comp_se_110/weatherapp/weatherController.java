package fi.tuni.comp_se_110.weatherapp;

/**
 * @author Pinja Rontu
 */

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
    
    
    public void setGraphContent(ActionEvent e) throws Exception {
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
    
    public void setOptions() {
        
    } 
}
