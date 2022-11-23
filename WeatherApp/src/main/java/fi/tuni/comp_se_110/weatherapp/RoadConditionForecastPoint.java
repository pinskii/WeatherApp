/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.comp_se_110.weatherapp;

/**
 *
 * @author Matti
 */
public class RoadConditionForecastPoint {
    private String precipitation;
    private String slipperiness;
    private String overallCondition;
    private String time;
    
    public RoadConditionForecastPoint(){};
    public RoadConditionForecastPoint(String precipitation, String slipperiness, String overallCondition, String time){
        this.precipitation = precipitation;
        this.slipperiness = slipperiness;
        this.overallCondition = overallCondition;
        this.time = time;
    }
    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getSlipperiness() {
        return slipperiness;
    }

    public void setSlipperiness(String slipperiness) {
        this.slipperiness = slipperiness;
    }

    public String getOverallCondition() {
        return overallCondition;
    }

    public void setOverallCondition(String overallCondition) {
        this.overallCondition = overallCondition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
