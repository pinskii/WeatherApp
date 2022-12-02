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
                              
        LineChart lineChart = WeatherModel.drawGraph("temperature", location, date);
        lineChart.setMaxSize(410, 184);
        lineChart.setLegendVisible(false);
        return lineChart;
    }
    
    public static BarChart drawMaintenanceGraph(LocalDate starttime, double xmin, double ymin, double xmax, double ymax) {
        
        BarChart barChart = RoadConditionModel.drawBars(starttime, xmin, ymin, xmax, ymax);
        if(barChart != null ) {
            barChart.setMaxSize(457, 199);
            barChart.setLegendVisible(false);
        }
        return barChart;
    }
    
    public static LineChart drawRoadConditionGraph(double xmin, double ymin, double xmax, double ymax, String id) {
        
        LineChart lineChart = RoadConditionModel.drawChart(xmin, ymin, xmax, ymax, id, "Overall road condition");
        lineChart.setMaxSize(410, 199);
        lineChart.setLegendVisible(false);
        return lineChart;
    }
}
