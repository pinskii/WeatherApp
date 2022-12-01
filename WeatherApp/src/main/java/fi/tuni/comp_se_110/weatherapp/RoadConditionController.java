
package fi.tuni.comp_se_110.weatherapp;

import java.util.HashMap;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RoadConditionController implements Initializable{
    @FXML
    private AnchorPane chart;
    @FXML
    private ComboBox<String> streetBox;
    @FXML
    private ComboBox<String> infoOptsBox;
    @FXML
    private ToggleGroup road;
    @FXML
    private CheckBox messagesCheckbox;
    @FXML
    private Label trafficMessage;
    @FXML
    private DatePicker sctDayDatePicker;
    @FXML
    private TextField xMinVal;
    @FXML
    private TextField xMaxVal;
    @FXML
    private TextField yMinVal;
    @FXML
    private TextField yMaxVal;
    
    
    private ObservableList<String> infoOpts;
    ArrayList<String> streets = new ArrayList<>();  
    ObservableList<String> streetIDs;
    
    private HashMap<String, ArrayList<RoadConditionForecastPoint>> roadConditionData = new HashMap<>();
    
    public void showTrafficMessages() {
        
    }
    
    public void updateLocation() {
        
    }
    
    public void updateDate() {
        
    }
    
    public void updateSelectedView() {
        
    }
    
    public void updateTimeline() {
        
    }
    
    public void updateForecast() {
        
    }
    
    public void updateTrafficMessages() {
        
    }
    
    public void updateRoadConditionOptions() {
        
    }
    
    public ArrayList<Double> getCoordinates() {
        ArrayList<Double> coords = new ArrayList<>();
        if(!xMinVal.getText().isEmpty() && !xMaxVal.getText().isEmpty() 
            && !yMinVal.getText().isEmpty() && !yMaxVal.getText().isEmpty()
            && xMinVal.getText().matches("\\d+") 
            && xMaxVal.getText().matches("\\d+")
            && yMinVal.getText().matches("\\d+") 
            && yMaxVal.getText().matches("\\d+")) {
            
            double xmin = Double.parseDouble(xMinVal.getText());
            double xmax = Double.parseDouble(xMaxVal.getText());
            double ymin = Double.parseDouble(yMinVal.getText());
            double ymax = Double.parseDouble(yMaxVal.getText());
        
            if (xmin != xmax && ymin != ymax) {
                if ((xmin >= 20 && xmin <= 32) && (xmax >= 20 && xmax <= 32) && 
                    (ymin >= 59 && ymin <= 72) && (ymax >= 59 && ymax <= 72)) {
                    coords.add(xmin);
                    coords.add(xmax);
                    coords.add(ymin);
                    coords.add(ymax);
                    return coords;
                }
            }
        }
        return null; 
    }
    
    public void setGraphContent(ActionEvent e) {
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();
            RadioButton selectedRadioButton = (RadioButton) road.getSelectedToggle();
            String selectedValue = selectedRadioButton.getText();

            if (selectedValue.equals("maintenance")) {
                LocalDate date = sctDayDatePicker.getValue();

                try {
                    chart.getChildren().clear();
                    BarChart barChart = RoadConditionModel.drawBars(date, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
                    chart.getChildren().add(barChart);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (selectedValue.equals("road condition forecast")) {
                String info = infoOptsBox.getValue();
                String id = streetBox.getValue();

                try {
                    chart.getChildren().clear();
                    LineChart lineChart = RoadConditionModel.drawChart(coords.get(0), coords.get(1), coords.get(2), coords.get(3), id, info);
                    chart.getChildren().add(lineChart);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        

        
    }
    
    public void showTrafficMessages(ActionEvent e) {
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();
            if (messagesCheckbox.isSelected()) {
            String msgToPrint = RoadConditionModel.getTrafficMsgInfo(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
            trafficMessage.setText(msgToPrint);
            } else {
                trafficMessage.setText("");
            }
        }

        
    }
    
    public void setStreetIDOptions(ActionEvent e) {
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();
            System.out.println(coords);
            roadConditionData = DigiTrafficApi.getRoadConditionsForecast(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
            for (Map.Entry<String, ArrayList<RoadConditionForecastPoint>> set :
                roadConditionData.entrySet()) {

                streets.add(set.getKey());
            }
            System.out.println(streets);
            streetIDs = FXCollections.observableArrayList(streets);
            System.out.println(streetIDs);
            streetBox.setItems(streetIDs);
        }
        else {
            // alert about wrong coordinates
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Check coordinates!");
            alert.showAndWait();
        }

        
            
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        infoOpts = FXCollections.observableArrayList("Precipitation", "Slipperiness", "Overall road condition");
        infoOptsBox.setItems(infoOpts);
    }
    
    // Added because the funcion was not implemented
    public void setGraphContent() {
        
    }
    
    // Added because the funcion was not implemented
    public void setStreetIDOptions() {
        
    }
    
    private HashMap<String, String> getOptions() {
        HashMap<String, String> newOption = new HashMap();
        return null;
    }
}
