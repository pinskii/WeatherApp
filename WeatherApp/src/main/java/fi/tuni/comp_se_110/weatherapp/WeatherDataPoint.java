package fi.tuni.comp_se_110.weatherapp;


/**
 *
 * @author Matti
 */

import java.time.LocalDateTime;
import java.time.DayOfWeek;

public class WeatherDataPoint {
    private String totalCloudCover;
    private String temperature;
    private String windSpeedMS;
    private LocalDateTime dateTime;
    
    public WeatherDataPoint (){}
    
    public WeatherDataPoint(String totalCloudCover, String temperature, String windSpeedMS, LocalDateTime dateTime){
        this.totalCloudCover = totalCloudCover;
        this.temperature = temperature;
        this.windSpeedMS = windSpeedMS;
        this.dateTime = dateTime;  
    }
    public String getTemperature(){
        return this.temperature;
    }
    public DayOfWeek getDay () {
        return this.dateTime.getDayOfWeek();
    }
    public String getWindSpeed(){
        return this.windSpeedMS;
    }public String getTotalCloudCover(){
        return this.totalCloudCover;
    }public LocalDateTime getDateTime(){
        return this.dateTime;
    }
    
}
