/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.comp_se_110.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author Matti
 */
public class DigiTrafficApi {
        private static String baseUrl = "http://tie.digitraffic.fi";
        
        private static String roadMaintenanceTasks(String[] options){
            String uri = baseUrl + "/maintenance/v1/tracking/tasks?";
            for(String option : options){
                uri = uri + "&" + option;
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(uri))
              .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            }catch(Exception e){
                return "Error";
            }
        }
        public static HashMap<String, ArrayList<RoadConditionForecastPoint>> getRoadConditionsForecast(int x_min, int y_min, int x_max, int y_max){
            String responseBody = roadConditionsForecast(String.valueOf(x_min), String.valueOf(y_min), String.valueOf(x_max), String.valueOf(y_max));
            JsonObject dataJsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            HashMap<String, ArrayList<RoadConditionForecastPoint>> returnMap = new HashMap<String,ArrayList<RoadConditionForecastPoint>>();
            JsonArray weatherData = dataJsonObject.get("weatherData").getAsJsonArray();
            for(int i = 0; i < weatherData.size(); i++){
                ArrayList<RoadConditionForecastPoint> tempArray = new ArrayList<RoadConditionForecastPoint>();
                String id = weatherData.get(i).getAsJsonObject().get("id").getAsString();
                JsonArray roadConditions = weatherData.get(i).getAsJsonObject().get("roadConditions").getAsJsonArray();
                for(int n = 0;n < roadConditions.size(); n++){
                    JsonObject data = roadConditions.get(n).getAsJsonObject();
                    if(!data.has("forecastConditionReason")){
                        continue;
                    }
                    String overallCondition = data.get("overallRoadCondition").getAsString();
                    String slipperiness = null;
                    String precipitation = data.get("forecastConditionReason").getAsJsonObject().get("precipitationCondition").getAsString();
                    String time = data.get("forecastName").getAsString();
                    if(data.get("forecastConditionReason").getAsJsonObject().has("frictionCondition")){
                        slipperiness = data.get("forecastConditionReason").getAsJsonObject().get("frictionCondition").getAsString();
                    }
                    RoadConditionForecastPoint point = new RoadConditionForecastPoint(precipitation, slipperiness, overallCondition, time);
                    tempArray.add(point);
                
                    
                }
                returnMap.put(id, tempArray);
            }
            return returnMap;
        }
        private static String roadConditionsForecast(String x_min, String y_min, String x_max, String y_max){
            String uri = baseUrl + "/api/v3/data/road-conditions/"+ x_min +"/"+y_min+"/"+ x_max+"/"+y_max;
            System.out.println("URI: " + uri);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(uri))
              .header("accept-encoding", "gzip")
              .build();
            try {
                InputStream responseStream = client.send(request, HttpResponse.BodyHandlers.ofInputStream()).body();
                GZIPInputStream zipStream = new GZIPInputStream(responseStream);
                byte[] bytes = zipStream.readAllBytes();
                String returnData = new String(bytes,StandardCharsets.UTF_8);
                return returnData;
            }catch(Exception e){
                System.out.println(e);
                return "Error";
            }
        }
}
