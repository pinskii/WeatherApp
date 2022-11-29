package fi.tuni.comp_se_110.weatherapp;

/**
 * 
 * @author Melina Ruusunen
 */

import java.time.LocalDate;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;

public class CombinedModel {
    
        
    public static LineChart drawWeatherGraph(String location, LocalDate date) throws Exception{
                              
        return WeatherModel.drawGraph("temperature", location, date);
    }
    
    public static BarChart drawMaintenanceGraph(LocalDate starttime, double xmin, double ymin, double xmax, double ymax) {
        
        return RoadConditionModel.drawBars(starttime, xmin, ymin, xmax, ymax);
    }
    
    public static LineChart drawRoadConditionGraph(double xmin, double ymin, double xmax, double ymax, String id) {
        
        return RoadConditionModel.drawChart(xmin, ymin, xmax, ymax, id, "Overall road condition");
    }
}
