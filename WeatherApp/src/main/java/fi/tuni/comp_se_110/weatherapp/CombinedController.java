
package fi.tuni.comp_se_110.weatherapp;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CombinedController implements Initializable {
    @FXML
    private AnchorPane weatherChart;
    @FXML
    private AnchorPane roadChart;
    @FXML
    private ComboBox<String> streetBox;
    @FXML
    private ComboBox<String> citiesBox;
    @FXML
    private ToggleGroup info;
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
    
    private final ArrayList<String> streets = new ArrayList<>();  
    private ObservableList<String> streetIDs;
    private ObservableList<String> cities;
    
    private HashMap<String, ArrayList<RoadConditionForecastPoint>> roadConditionData = new HashMap<>();
    private HashMap<String, Double[]> citiesWithCoords = new HashMap<>();
    
    Double[] treCoords = {23.56,61.43,23.94,61.55}; // 23 ja 24 / 61 ja 62
    Double[] jkylaCoords = {25.63,62.21,25.82,62.31}; // 25 ja 26 / 62 ja 63
    Double[] helCoords = {24.85,60.13,25.23,60.29}; // 24 ja 25 / 60 ja 61
    Double[] ouluCoords = {25.28,64.95,25.61,65.10}; // 25 ja 26 / 64 ja 66
    Double[] tkuCoords = {22.08,60.34,22.37,60.50}; // 22 ja 23 / 60 ja 61
    Double[] vaasaCoords = {21.42,62.98,21.82,63.17}; // 21 ja 22 / 62 ja 64
    Double[] rniemiCoords = {25.63,66.47,25.75,66.51}; // 25 ja 26 / 66 ja 67
    
    private RootController parentController;
    
    public void updateLocation() {
        
    }
    
    public void updateCoordinates() {
        
    }
    
    public void updateTimeInterval() {
        
    }
    
    public void updateSelectedView() {
        
    }
    
    public void setLocation() {
       
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
            
        else {
            String boxLocation = citiesBox.getValue();
            
            for (Map.Entry<String, Double[]> set :
                 citiesWithCoords.entrySet()) {

                if(set.getKey().equals(boxLocation)) {
                    for (var coord : set.getValue()) {
                        coords.add(coord);
                    }
                }
            }
        }
        return null;        
    }
    
    public String getLocation() {
        String location = "";
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();
            String boxLocation = citiesBox.getValue();

            if (boxLocation.isEmpty()) {
                for (Map.Entry<String, Double[]> set :
                     citiesWithCoords.entrySet()) {

                    if ((set.getValue()[0] >= 23.0 && set.getValue()[0] <= 24.0) && (set.getValue()[1] >= 61.0 && set.getValue()[1] <= 62.0)) {
                        location = "Tampere";
                    }
                    else if ((set.getValue()[0] >= 25.0 && set.getValue()[0] <= 26.0) && (set.getValue()[1] >= 62.0 && set.getValue()[1] <= 66.0)) {
                        location = "Jyväskylä";
                    }
                    else if ((set.getValue()[0] >= 24.0 && set.getValue()[0] <= 25.0) && (set.getValue()[1] >= 60.0 && set.getValue()[1] <= 61.0)) {
                        location = "Helsinki";
                    }
                    else if ((set.getValue()[0] >= 25.0 && set.getValue()[0] <= 26.0) && (set.getValue()[1] >= 64.0 && set.getValue()[1] <= 66.0)) {
                        location = "Oulu";
                    }
                    else if ((set.getValue()[0] >= 22.0 && set.getValue()[0] <= 23.0) && (set.getValue()[1] >= 60.0 && set.getValue()[1] <= 61.0)) {
                        location = "Turku";
                    }
                    else if ((set.getValue()[0] >= 21.0 && set.getValue()[0] <= 22.0) && (set.getValue()[1] >= 62.0 && set.getValue()[1] <= 64.0)) {
                        location = "Vaasa";
                    }
                    else if ((set.getValue()[0] >= 25.0 && set.getValue()[0] <= 26.0) && (set.getValue()[1] >= 66.0 && set.getValue()[1] <= 67.0)) {
                        location = "Rovaniemi";
                    }
                }
            } else {
                location = boxLocation;
            }

            return location;
        }
        return null;
    }
    
    public void setGraphContents() {
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();
        
            String location = getLocation(); 

            RadioButton selectedRadioButton = (RadioButton) info.getSelectedToggle();
            String selectedValue = selectedRadioButton.getText();

            if (selectedValue.equals("weather & road maintenance")) {
                LocalDate date = sctDayDatePicker.getValue();

                try {
                    weatherChart.getChildren().clear();
                    LineChart tempChart = CombinedModel.drawWeatherGraph(location, date);
                    weatherChart.getChildren().add(tempChart);

                    roadChart.getChildren().clear();
                    BarChart barChart = CombinedModel.drawMaintenanceGraph(date, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
                    roadChart.getChildren().add(barChart);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (selectedValue.equals("weather & overall road condition")) {
                String id = streetBox.getValue();
                LocalDate date = sctDayDatePicker.getValue();

                try {
                    weatherChart.getChildren().clear();
                    LineChart tempChart = CombinedModel.drawWeatherGraph(location, date);
                    weatherChart.getChildren().add(tempChart);

                    roadChart.getChildren().clear();
                    LineChart lineChart = CombinedModel.drawRoadConditionGraph(coords.get(0), coords.get(1), coords.get(2), coords.get(3), id);
                    roadChart.getChildren().add(lineChart);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void setStreetIDOptions(ActionEvent e) {
        ArrayList<Double> coords = null;
        if (getCoordinates() != null) {
            coords = getCoordinates();

            roadConditionData = DigiTrafficApi.getRoadConditionsForecast(coords.get(0), coords.get(1), coords.get(2), coords.get(3));

            for (Map.Entry<String, ArrayList<RoadConditionForecastPoint>> set :
                 roadConditionData.entrySet()) {

                streets.add(set.getKey());
            }

            streetIDs = FXCollections.observableArrayList(streets);
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
    
    public void initializeParentController(RootController parentController) {
        this.parentController = parentController;
    }
    
    public HashMap<String, String> getOptions() {
        HashMap<String, String> newOptions = new HashMap();
        newOptions.put("xMin", xMinVal.getText());
        newOptions.put("xMax", xMaxVal.getText());
        newOptions.put("yMin", yMinVal.getText());
        newOptions.put("yMax", yMaxVal.getText());
        newOptions.put("date", sctDayDatePicker.getValue().toString());
        newOptions.put("selected", ((RadioButton)info.getSelectedToggle()).getText());
        newOptions.put("street_id", streetBox.getValue());
        newOptions.put("city", citiesBox.getValue());
        return newOptions;
    }
    
    public void loadPreference() {
        String selected = parentController.selectPreference(PreferenceType.COMBINED);
        setPreference(selected);
    }
    
    public void setPreference(String name) {
        Map<String, String> preference = parentController.getPreference(PreferenceType.COMBINED, name);
        setPreference(preference);
    }
    
    public void setPreference(Map<String, String> preference) {
        if(preference != null) {
            xMinVal.setText(preference.get("xMin"));
            xMaxVal.setText(preference.get("xMax"));
            yMinVal.setText(preference.get("yMin"));
            yMaxVal.setText(preference.get("yMax"));
            sctDayDatePicker.setValue(LocalDate.parse(preference.get("date"), DateTimeFormatter.ISO_DATE));
            for(Toggle toggle : info.getToggles()) {
                if(((RadioButton) toggle).getText().equals(preference.get("selected"))) {
                    info.selectToggle(toggle);
                }
            }
            streetBox.setValue(preference.get("street_id"));
            citiesBox.setValue(preference.get("graph_info"));
            setGraphContents();
        }
    }
    
    public void savePreference() {
        parentController.savePreference(PreferenceType.COMBINED, getOptions());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cities = FXCollections.observableArrayList("Tampere", "Jyväskylä", "Helsinki", "Oulu", "Turku", "Vaasa", "Rovaniemi");
        citiesBox.setItems(cities);
        
        citiesWithCoords.put("Tampere", treCoords);
        citiesWithCoords.put("Jyväskylä", jkylaCoords);
        citiesWithCoords.put("Helsinki", helCoords);
        citiesWithCoords.put("Oulu", ouluCoords);
        citiesWithCoords.put("Turku", tkuCoords);
        citiesWithCoords.put("Vaasa", vaasaCoords);
        citiesWithCoords.put("Rovaniemi", rniemiCoords);
    }
}
