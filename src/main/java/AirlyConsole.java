import com.martiansoftware.jsap.JSAPResult;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.lang.reflect.Array;
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
        List<String> baseAscii = getDanger();
        for (String line : baseAscii){
            System.out.println(line);
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

    private List<String> getDanger(){
        List<String> okAscii = new ArrayList<>();
        okAscii.add("    ____  _   ________");
        okAscii.add("   / __ \\/ | / / ____/");
        okAscii.add("  / / / /  |/ / / __  ");
        okAscii.add(" / /_/ / /|  / /_/ /  ");
        okAscii.add("/_____/_/ |_/\\____/   ");
        okAscii.add("                      ");
        return okAscii;
    }
}
