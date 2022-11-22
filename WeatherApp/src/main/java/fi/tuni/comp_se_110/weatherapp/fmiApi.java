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
import java.time.Month;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FmiApi {
    private static LocalDateTime date= null;
    private static String windSpeedMS="NaN";
    private static String temperature="NaN";
    private static String totalCloudCover="NaN";
    private static ArrayList<WeatherDataPoint> returnData = new ArrayList<WeatherDataPoint>(){};
    static String baseUrl = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=";
    
    public static ArrayList<WeatherAverageTemperaturePoint> getMonthlyTemperature (String place, LocalDate starttime)throws Exception{
        LocalDateTime start = LocalDateTime.of(starttime.getYear(), starttime.getMonthValue(), 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusDays(1).plusHours(20);
        String endFormatted = formatDate(end);
        String starttimeFormatted = formatDate(start);
        String[] options = {
            "place="+place,
            "starttime="+starttimeFormatted,
            "endtime="+endFormatted,
            "timestep="+60*4,
            "parameters=Temperature"
        };
        String observationData = weatherObservationHourlySimple(options);
        Document document = loadXMLFromString(observationData);
        return parseObesrvationDataTemperatures(document);
    }
    public static ArrayList<WeatherDataPoint> getWeatherData (String place, LocalDate starttime) throws Exception{
        LocalDateTime start = starttime.atStartOfDay();
        LocalDateTime endtime = start.plusDays(2);
        LocalDateTime today = LocalDateTime.now().minusHours(2);
        String forecastParams = "Temperature,WindSpeedMS,TotalCloudCover";
        String starttimeFormatted = formatDate(start);
        String endtimeFormatted = formatDate(endtime);
        if(endtime.isBefore(today)){
            String[] options = {
            "place="+place,
            "starttime="+starttimeFormatted,
            "endtime="+endtimeFormatted,
            "timestep="+60,
            "parameters=Temperature,ws_10min,n_man"
            };
            String observationData = weatherObservationHourlySimple(options); 
            Document document = loadXMLFromString(observationData);
            parseObservationData(document);
        }else if(start.isBefore(today) && endtime.isAfter(today)){
            String todayFormatted = formatDate(today);
            System.out.println(todayFormatted);
            System.out.println(endtimeFormatted);
            String[] observationOptions = {
                "place="+place,
                "starttime="+starttimeFormatted,
                "endtime="+todayFormatted,
                "timestep="+60,
                "parameters=Temperature,ws_10min,n_man"
            };
            String observationData = weatherObservationHourlySimple(observationOptions);
            Document document = loadXMLFromString(observationData);
            parseObservationData(document);
            String[] forecastOptions = {
                "place="+place,
                "starttime="+todayFormatted,
                "endtime="+endtimeFormatted,
                "timestep="+60,
                "parameters=Temperature,WindSpeedMS,TotalCloudCover"
            };
            String forecastData = harmonieForecastSimple(forecastOptions);
            document = loadXMLFromString(forecastData);
            parseForecastData(document);
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
    
    private static String formatDate(LocalDateTime date){
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
                if(parameterType.equals("WindSpeedMS")){
                    windSpeedMS = value;
                }     
                else if(parameterType.equals("Temperature")){
                    temperature = value;
                }
                else if(parameterType.equals("TotalCloudCover")){
                    totalCloudCover = value;

                }
                
                if(counter == 3){
                    counter = 0;
                    if(!totalCloudCover.equals("NaN") && !temperature.equals("NaN") && !windSpeedMS.equals("NaN")){
                        WeatherDataPoint point = new WeatherDataPoint(totalCloudCover, temperature, windSpeedMS, date);
                        returnData.add(point);
                    }
                    totalCloudCover = "NaN";
                    temperature = "NaN";
                    windSpeedMS= "NaN";
                    
                    System.out.println("pushed");
                } 
                System.out.println("2: "+ split[2]);
                System.out.println("3: "+ split[3]);
                System.out.println("4: "+ split[4]);
                counter++;
            }
    }
    private static ArrayList<WeatherAverageTemperaturePoint> parseObesrvationDataTemperatures (Document xmlData){
        ArrayList<WeatherAverageTemperaturePoint> returnTemperatureData = new ArrayList<>();
        xmlData.normalize();
        NodeList elements = xmlData.getElementsByTagName("BsWfs:BsWfsElement");
        for(int i = 0; i < elements.getLength(); i++){
            String text = elements.item(i).getTextContent().trim();
            text = text.replaceAll("\\s+"," ");
            String[] split = text.split(" ");
            String value = split[4];
            LocalDateTime dateTemperature = formatDateStringToLocalDateTime(split[2].substring(0, split[2].length()-1));
            WeatherAverageTemperaturePoint point = new WeatherAverageTemperaturePoint(value, dateTemperature);
            returnTemperatureData.add(point);
        }
        for(WeatherAverageTemperaturePoint point : returnTemperatureData){
            System.out.println("date: "+ point.getDate());
            System.out.println("Temperature: "+ point.getTemperature());
        }
        return returnTemperatureData;
    }
    private static void parseObservationData (Document xmlData){
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
                if(parameterType.equals("ws_10min")){
                    windSpeedMS = value;
                }     
                else if(parameterType.equals("Temperature")){
                    temperature = value;
                }
                else if(parameterType.equals("n_man")){
                    if(value.equals("NaN")){
                        totalCloudCover = value;
                    }else{
                        value = Double.toString(Double.parseDouble(value)*8);
                        totalCloudCover = value;
                    }
                    
                    

                }
                
                if(counter == 3){
                    counter = 0;
                    if(!totalCloudCover.equals("NaN") && !temperature.equals("NaN")){
                        WeatherDataPoint point = new WeatherDataPoint(totalCloudCover, temperature, windSpeedMS, date);
                        returnData.add(point);
                    }
                    totalCloudCover = "NaN";
                    temperature = "NaN";
                    windSpeedMS= "NaN";
                    
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