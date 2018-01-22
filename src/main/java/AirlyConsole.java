import com.martiansoftware.jsap.JSAPResult;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;

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
        SensorMeasurements sensorMeasurements =readAndHandleSensorMeasurements(sensorId);
        if (sensorMeasurements == null)
            return;
        printCurrentSensorMeasurements(sensorMeasurements.currentMeasurements);
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

    public void showNearestSensorCurrentMeasurements(double latitude, double longitude) {

    }

    public void showNearestSensorHistoricMeasurements(double latitude, double longitude) {

    }

    private void printCurrentSensorMeasurements(Measurements sensorMeasurements) {
        System.out.println(sensorMeasurements.airQualityIndex);
        System.out.println(sensorMeasurements.pm1);
        System.out.println(sensorMeasurements.pm10);
        System.out.println(sensorMeasurements.pm25);
        System.out.println(sensorMeasurements.temperature);
    }

    private void printHistoricSensorMeasurements(MeasurementsWithInterval[] sensorMeasurements) {

    }

}
