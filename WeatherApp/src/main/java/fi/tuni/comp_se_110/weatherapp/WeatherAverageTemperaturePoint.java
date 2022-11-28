/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.comp_se_110.weatherapp;

import java.time.LocalDateTime;

/**
 *
 * @author Matti
 */
public class WeatherAverageTemperaturePoint {
    private String temperature;
    private LocalDateTime date;
    
    public WeatherAverageTemperaturePoint(){};
    
    public WeatherAverageTemperaturePoint(String temperature, LocalDateTime date){
        this.temperature = temperature;
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    
}
