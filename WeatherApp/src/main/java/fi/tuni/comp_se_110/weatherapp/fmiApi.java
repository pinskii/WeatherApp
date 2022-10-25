package fi.tuni.comp_se_110.weatherapp;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class fmiApi {
    private static LocalDateTime date= null;
    private static String windSpeedMS=null;
    private static String temperature=null;
    private static String totalCloudCover=null;
    private static ArrayList<WeatherDataPoint> returnData = new ArrayList<WeatherDataPoint>(){};
    static String baseUrl = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=";
    
    public static ArrayList<WeatherDataPoint> getWeatherData (String place, LocalDate starttime) throws Exception{
        LocalDate endtime = starttime.plusDays(6);
        LocalDate today = LocalDate.now();
        String forecastParams = "Temperature,WindSpeedMS,TotalCloudCover";
        String starttimeFormatted = formatDate(starttime);
        String endtimeFormatted = formatDate(endtime);
        if(endtime.isBefore(today)){
            String[] options = {
            "place="+place,
            "starttime="+starttimeFormatted,
            "endtime="+endtimeFormatted,
            "parameters=t2m,ws_10min,n_man"
            };
            String observationData = weatherObservationHourlySimple(options);  
        }else if(starttime.isBefore(today) && endtime.isAfter(today)){
            String todayFormatted = formatDate(today);
            String[] observationOptions = {
                "place="+place,
                "starttime="+starttimeFormatted,
                "endtime="+todayFormatted,
                "parameters=Temperature,WindSpeedMS,TotalCloudCover"
            };
            String observationData = weatherObservationHourlySimple(observationOptions);
            String[] forecastOptions = {
                "place="+place,
                "starttime="+todayFormatted,
                "endtime="+endtimeFormatted,
                "timestep="+60,
                "parameters=Temperature,WindSpeedMS,TotalCloudCover"
            };
            String forecastData = harmonieForecastSimple(forecastOptions);
            System.out.println("keskellä");
        }else{
            String[] forecastOptions = {
                "place="+place,
                "starttime="+starttimeFormatted,
                "endtime="+endtimeFormatted,
                "timestep="+60,
                "parameters="+forecastParams
            };
            String forecastData = harmonieForecastSimple(forecastOptions);
            Document document = loadXMLFromString(forecastData);
            parseForecastData(document);
            
        }
        
        return returnData;
    };
    
    private static String formatDate(LocalDate date){
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'");
        return date.format(formatObj);
    }
    private static LocalDateTime formatDateStringToLocalDateTime(String date){
        LocalDateTime parsed = LocalDateTime.parse(date);
        return parsed;
    };
    
    private static void parseForecastData(Document xmlData){
            xmlData.normalize();
            NodeList elements = xmlData.getElementsByTagName("BsWfs:BsWfsElement");
            int counter = 0;
            for(int i= 0; i < elements.getLength(); i++){
                System.out.println("next");
                System.out.println(elements.item(i).getNodeName());
                String text = elements.item(i).getTextContent().trim();
                text = text.replaceAll("\\s+"," ");
                String[] split = text.split(" ");
                String parameterType = split[3];
                String value = split[4];
                date = formatDateStringToLocalDateTime(split[2].substring(0, split[2].length()-1));
                switch(parameterType){
                    case "WindSpeedMS" ->{
                       windSpeedMS = value;
                       break;
                    }                      
                    case "Temperature"->{
                        temperature = value;
                        break;
                    }  
                    case "TotalCloudCover"->{
                        totalCloudCover= value;
                        break;
                    }        
                }
                if(counter == 3){
                    counter = 0;
                    WeatherDataPoint point = new WeatherDataPoint(totalCloudCover, temperature, windSpeedMS, date);
                    returnData.add(point);
                    System.out.println("pushed");
                } 
                System.out.println("2: "+ split[2]);
                System.out.println("3: "+ split[3]);
                System.out.println("4: "+ split[4]);
                counter++;
            }
    }
    private static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        return builder.parse(is);
    }   
    private static String harmonieForecastSimple (String [] options){
        String uri = baseUrl + "fmi::forecast::harmonie::surface::point::simple";
        for(String option : options){
            uri = uri + "&" + option;
        }
        System.out.println(uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri))
          .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        }catch(Exception e){
            return "Error";
        }
    }
    private static String weatherObservationHourlySimple (String [] options) {
        String uri = baseUrl + "fmi::observations::weather::hourly::simple";
        for(String option : options){
            uri = uri + "&" + option;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri))
          .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();
        }catch(Exception e){
            return "error";
        }
    }
}