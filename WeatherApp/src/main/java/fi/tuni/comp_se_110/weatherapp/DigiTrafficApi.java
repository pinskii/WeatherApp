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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
/**
 *
 * @author Matti
 */
public class DigiTrafficApi {
        private static String baseUrl = "http://tie.digitraffic.fi";
        public static HashMap<String, Integer> getRoadMaintenanceTasks (LocalDate starttime, int x_min, int y_min, int x_max, int y_max){
            HashMap<String, Integer> returnData = new HashMap<>();
            LocalDateTime starttimeLocalDateTime = LocalDateTime.of(starttime.getYear(), starttime.getMonthValue(), starttime.getDayOfMonth(), 0, 0);
            LocalDateTime endtimeLocalDateTime = starttimeLocalDateTime.plusHours(24);
            String starttimeString = Utils.formatDate(starttimeLocalDateTime);
            String endtimeString = Utils.formatDate(endtimeLocalDateTime);
            String[] preOptions = {
                "xMin="+String.valueOf(x_min),
                "yMin="+String.valueOf(y_min),
                "xMax="+String.valueOf(x_max),
                "yMax="+String.valueOf(y_max),
                "endFrom="+starttimeString,
                "endBefore="+endtimeString
            };
            ArrayList<String> options = new ArrayList<>();
            options.addAll(Arrays.asList(preOptions));
            String data = roadMaintenanceTasks(options);
            JsonArray features = JsonParser.parseString(data).getAsJsonObject().get("features").getAsJsonArray();
            for(int i = 0; i < features.size(); i++){
                JsonArray tasksArray = features.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("tasks").getAsJsonArray();
                for(int n = 0; n < tasksArray.size(); n++){
                    String task = tasksArray.get(n).getAsString();
                    if(!returnData.containsKey(task)){
                        returnData.put(task, 1);
                    }else{
                        returnData.put(task, returnData.get(task)+1);
                    }
                }
            }
            System.out.println(returnData);
            return returnData;
        }
        private static String roadMaintenanceTasks(ArrayList<String> options){
            String uri = baseUrl + "/api/maintenance/v1/tracking/routes?";
            for(String option : options){
                uri = uri  + option + "&";
            }
            System.out.println(uri);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
              .header("accept-encoding", "gzip")
              .uri(URI.create(uri))
              .build();
            try {
                HttpResponse<InputStream> result = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                if(result.headers().firstValue("content-encoding").isEmpty()){
                    byte[] bytes = result.body().readAllBytes();
                    String returnData = new String(bytes,StandardCharsets.UTF_8);
                    System.out.println("normal: "+returnData);
                    return returnData;
                }else{
                    GZIPInputStream zipStream = new GZIPInputStream(result.body());
                    byte[] bytes = zipStream.readAllBytes();
                    String returnData = new String(bytes,StandardCharsets.UTF_8);
                    System.out.println("gzipped: "+ returnData);
                    return returnData;
                }
            }catch(Exception e){
                System.out.println(e);
                return "Error";
            }
        }
        private static String metadataLocations(String id){
            String url = "/api/v3/metadata/locations/"+id;
            String uri = baseUrl + url;
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(uri))
              .header("accept-encoding", "gzip")
              .build();
            try {
                HttpClient client = HttpClient.newHttpClient();
                String returnData = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                System.out.println("returnData: "+returnData);
                if(JsonParser.parseString(returnData).getAsJsonObject().get("status").getAsInt() == 200){
                    return returnData;
                }else{
                    return null;
                }
            }catch(Exception e){
                return null;
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
