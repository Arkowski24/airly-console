import com.martiansoftware.jsap.JSAPResult;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AirlyConsole {
    private WebReader webReader;


    public void execute(JSAPResult jsapResult) {
        if (jsapResult.contains("api-key")) {
            String apiKey = jsapResult.getString("api-key");
            webReader = new WebReader(apiKey);
        } else {
            try {
                webReader = new WebReader();
            } catch (IllegalStateException e) {
                System.out.println("Cannot find API KEY.");
                return;
            } catch (SecurityException e) {
                System.out.println("Access to env API Key access is forbidden.");
                return;
            }
        }
        boolean showHistory = jsapResult.getBoolean("history");

        if (jsapResult.contains("sensor-id")) {
            int sensorId = jsapResult.getInt("sensor-id");
            if (!showHistory) {
                showSensorCurrentMeasurements(sensorId);
            } else {
                showSensorHistoricMeasurements(sensorId);
            }
        } else {
            if (!jsapResult.contains("latitude") || !jsapResult.contains("longitude")) {
                System.out.println("Program requires both latitude and longitude.");
                return;
            }
            double latitude = jsapResult.getDouble("latitude");
            double longitude = jsapResult.getDouble("longitude");
            if (!showHistory) {
                showNearestSensorCurrentMeasurements(latitude, longitude);
            } else {
                showNearestSensorHistoricMeasurements(latitude, longitude);
            }
        }
    }

    public void showSensorCurrentMeasurements(int sensorId) {
        SensorMeasurements sensorMeasurements = readAndHandleSensorMeasurements(sensorId);
        SensorDetails sensorDetails = readAndHandleSensorDetails(sensorId);
        if (sensorMeasurements == null || sensorDetails == null)
            return;
        printCurrentSensorMeasurements(sensorMeasurements.currentMeasurements, sensorDetails);
    }

    public void showSensorHistoricMeasurements(int sensorId) {
        SensorMeasurements sensorMeasurements =readAndHandleSensorMeasurements(sensorId);
        if (sensorMeasurements == null)
            return;
        printHistoricSensorMeasurements(sensorMeasurements.history);
    }

    private SensorMeasurements readAndHandleSensorMeasurements(int sensorId){
        SensorMeasurements sensorMeasurements;
        try {
            sensorMeasurements = webReader.readSensorMeasurements(sensorId, 24, 1);
        } catch (URISyntaxException e) {
            System.out.println("Couldn't create URI.");
            return null;
        } catch (IOException e) {
            System.out.println("Couldn't establish connection with server.");
            return null;
        } catch (AuthenticationException e) {
            System.out.println("API Key is not valid.");
            return null;
        }
        return sensorMeasurements;
    }

    private SensorDetails readAndHandleSensorDetails(int sensorId){
        SensorDetails sensorDetails;
        try {
            sensorDetails = webReader.readSensorDetails(sensorId);
        } catch (URISyntaxException e) {
            System.out.println("Couldn't create URI.");
            return null;
        } catch (IOException e) {
            System.out.println("Couldn't establish connection with server.");
            return null;
        } catch (AuthenticationException e) {
            System.out.println("API Key is not valid.");
            return null;
        }
        return sensorDetails;
    }

    public void showNearestSensorCurrentMeasurements(double latitude, double longitude) {

    }

    public void showNearestSensorHistoricMeasurements(double latitude, double longitude) {

    }

    private void printCurrentSensorMeasurements(Measurements measurements, SensorDetails details) {
        AirQuality airQuality = getAirQuality(Math.round(measurements.airQualityIndex));
        List<String> base = getAsciiForIndex(airQuality);
        PrettyMeasurements prettyMeasurements = new PrettyMeasurements(measurements);

        base.set(0, base.get(0) + " | Address: " + details.address);
        base.set(1, base.get(1) + " | CAQI: " + prettyMeasurements.caqi + " " + airQuality);
        base.set(2, base.get(2) + " | PM 2.5: " + prettyMeasurements.pm25);
        base.set(3, base.get(3) + " | PM 10: " + prettyMeasurements.pm10);
        base.set(4, base.get(4) + " | Temperature: " + prettyMeasurements.temperature);
        base.set(5, base.get(5) + " | Humidity: " + prettyMeasurements.humidity);

        for (String line : base){
            System.out.println(line);
        }
    }

    private List<String> getAsciiForIndex(AirQuality airQualityIndex){
        switch (airQualityIndex){
            case Good: return getGood();

            case Ok: return getOK();

            case Bad: return getBad();

            case Dangerous: return getDangerous();

            default: return null;
        }
    }

    private void printHistoricSensorMeasurements(MeasurementsWithInterval[] sensorMeasurements) {

    }

    private List<String> getGood(){
        List<String> okAscii = new ArrayList<>();
        okAscii.add("   ______                __");
        okAscii.add("  / ____/___  ____  ____/ /");
        okAscii.add(" / / __/ __ \\/ __ \\/ __  / ");
        okAscii.add("/ /_/ / /_/ / /_/ / /_/ /  ");
        okAscii.add("\\____/\\____/\\____/\\__,_/   ");
        okAscii.add("                           ");
        return okAscii;
    }

    private List<String> getOK(){
        List<String> okAscii = new ArrayList<>();
        okAscii.add("   ____  __  ");
        okAscii.add("  / __ \\/ /__");
        okAscii.add(" / / / / //_/");
        okAscii.add("/ /_/ / ,<   ");
        okAscii.add("\\____/_/|_|  ");
        okAscii.add("             ");
        return okAscii;
    }

    private List<String> getBad(){
        List<String> okAscii = new ArrayList<>();
        okAscii.add("    ____            __");
        okAscii.add("   / __ )____ _____/ /");
        okAscii.add("  / __  / __ `/ __  / ");
        okAscii.add(" / /_/ / /_/ / /_/ /  ");
        okAscii.add("/_____/\\__,_/\\__,_/   ");
        okAscii.add("                      ");
        return okAscii;
    }

    private List<String> getDangerous(){
        List<String> okAscii = new ArrayList<>();
        okAscii.add("    ____  _   ________");
        okAscii.add("   / __ \\/ | / / ____/");
        okAscii.add("  / / / /  |/ / / __  ");
        okAscii.add(" / /_/ / /|  / /_/ /  ");
        okAscii.add("/_____/_/ |_/\\____/   ");
        okAscii.add("                      ");
        return okAscii;
    }

    private AirQuality getAirQuality(double airQualityIndex){
        if (airQualityIndex < 40){
            return AirQuality.Good;
        }
        else if (airQualityIndex < 70){
            return AirQuality.Ok;
        }
        else if (airQualityIndex < 100){
            return AirQuality.Bad;
        }
        else {
            return AirQuality.Dangerous;
        }
    }
}
