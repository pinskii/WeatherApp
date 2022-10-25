
package com.mycompany.testi;

/**
 *
 * @author Matti
 */
import javax.xml.parsers.SAXParser;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

public class WeatherDataPoint {
    private float totalCloudCover;
    private float temperature;
    private float windSpeedMS;
    private LocalDateTime dateTime;
    
    public WeatherDataPoint (){}
    
    public WeatherDataPoint(float totalCloudCover, float temperature, float windSpeedMS, LocalDateTime dateTime){
        this.totalCloudCover = totalCloudCover;
        this.temperature = temperature;
        this.windSpeedMS = windSpeedMS;
        this.dateTime = dateTime;
        
    }
    public float getTemperature(){
        return this.temperature;
    }
    public DayOfWeek getDay () {
        return this.dateTime.getDayOfWeek();
    }
    public float getWindSpeed(){
        return this.windSpeedMS;
    }public float getTotalCloudCover(){
        return this.totalCloudCover;
    }public LocalDateTime getDateTime(){
        return this.dateTime;
    }
    
}
