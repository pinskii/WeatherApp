package fi.tuni.comp_se_110.weatherapp;

/**
 * 
 * @author Melina Ruusunen
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;

public class CombinedModel {
    private static ArrayList<WeatherDataPoint> datapoints = new ArrayList<>();
    private static ArrayList<WeatherDataPoint> data = new ArrayList<>();

    private static final ArrayList<Float> temps = new ArrayList<>(); 
    private static final ArrayList<String> dates = new ArrayList<>(); 
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public static ArrayList<WeatherDataPoint> fetchWeatherData(String location, LocalDate date) throws Exception {
        return FmiApi.getWeatherData(location, date);
    }
        
    public static LineChart drawWeatherGraph(String location, LocalDate date) throws Exception{
        
        temps.clear();
        dates.clear();
        datapoints.clear();
        data.clear();
        
        datapoints = fetchWeatherData(location, date);
        
        int now_hour = LocalDateTime.now().getHour();
        int now_date = LocalDateTime.now().getDayOfMonth();
        
        for(int i = 0 ; i < datapoints.size() ; i++) {
            int datapoint_hour = datapoints.get(i).getDateTime().getHour();
            int datapoint_date = datapoints.get(i).getDateTime().getDayOfMonth();
            
            if (datapoint_hour >= now_hour || datapoint_date >= now_date) {
                data.add(datapoints.get(i));
            }
        }
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("time");
        xAxis.setTickLabelFont(Font.font("Arial"));
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("temperature");
        yAxis.setTickLabelFont(Font.font("Arial"));
     
        LineChart lineChart = new LineChart(xAxis, yAxis);
        
        for(int i = 0 ; i < 12 ; i++) {
            String dateAsString = data.get(i).getDateTime().format(formatter);
            dates.add(dateAsString);
            temps.add(Float.parseFloat(data.get(i).getTemperature()));
        }    
        
        XYChart.Series series = new XYChart.Series();
        for(int j = 0 ; j < temps.size() ; j++) {
            series.getData().add(new XYChart.Data(dates.get(j),temps.get(j)));
        }
        lineChart.getData().add(series);
                       
        return lineChart;
    }
}
