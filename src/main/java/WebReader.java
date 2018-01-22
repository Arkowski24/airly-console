import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebReader {
    private final String apiKey;
    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    private final String apiWebAddress = "https://airapi.airly.eu";

    public WebReader() {
        try {
            apiKey = System.getenv("API_KEY");
        } catch (SecurityException e) {
            throw new SecurityException("API_KEY access is forbidden." + e);
        }
        if (apiKey == null) {
            throw new IllegalStateException("API_KEY wasn't found.");
        }
    }

    public WebReader(String apiKey) {
        this.apiKey = apiKey;
    }

    public SensorMeasurements readSensorMeasurements(int sensorID, int historyHours, int historyResolutionHours) throws URISyntaxException, IOException, AuthenticationException {
        CloseableHttpResponse response;
        //Read website
        try {
            URI uri = buildSensorMeasurementsURI(sensorID, historyHours, historyResolutionHours);
            HttpGet request = new HttpGet(uri);
            request.addHeader("apikey", this.apiKey);

            response = httpclient.execute(request);
        } catch (URISyntaxException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException(e);
        }

        return handleServerResponse(response);
    }

    public SensorMeasurements readNearestSensorMeasurements(double latitude, double longitude, int maxDistance) throws URISyntaxException, IOException, AuthenticationException {
        CloseableHttpResponse response;
        //Read website
        try {
            URI uri = buildNearestSensorMeasurementsURI(latitude, longitude, maxDistance);
            HttpGet request = new HttpGet(uri);
            request.addHeader("apikey", this.apiKey);

            response = httpclient.execute(request);
        } catch (URISyntaxException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException(e);
        }

        return handleServerResponse(response);
    }

    private SensorMeasurements handleServerResponse(CloseableHttpResponse response) throws IOException, AuthenticationException {
        //Handle response
        int responseStatusCode = response.getStatusLine().getStatusCode();
        if (responseStatusCode == 401) {
            throw new AuthenticationException("Wrong API Key.");
        } else if (responseStatusCode != 200) {
            throw new HttpResponseException(responseStatusCode, "Problem with connection.");
        }

        //Parse json
        String responseString = new BasicResponseHandler().handleResponse(response);
        return parseSensorMeasurements(responseString);
    }


    private URI buildSensorMeasurementsURI(int sensorID, int historyHours, int historyResolutionHours) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(apiWebAddress + "/v1/sensor/measurements");
        uriBuilder.addParameter("sensorId", Integer.toString(sensorID));
        uriBuilder.addParameter("historyHours", Integer.toString(historyHours));
        uriBuilder.addParameter("historyResolutionHours", Integer.toString(historyResolutionHours));
        return uriBuilder.build();
    }

    private URI buildNearestSensorMeasurementsURI(double latitude, double longitude, int maxDistance) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(apiWebAddress + "/v1/nearestSensor/measurements");
        uriBuilder.addParameter("latitude", Double.toString(latitude));
        uriBuilder.addParameter("longitude", Double.toString(longitude));
        uriBuilder.addParameter("maxDistance", Integer.toString(maxDistance));
        return uriBuilder.build();
    }

    private SensorMeasurements parseSensorMeasurements(String jsonResponse){
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, SensorMeasurements.class);
    }

}
