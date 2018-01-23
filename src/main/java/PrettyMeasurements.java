public class PrettyMeasurements {
    public final String caqi;
    public final String pm25;
    public final String pm10;
    public final String temperature;
    public final String humidity;

    public PrettyMeasurements(Measurements measurements) {
        this.caqi = Long.toString(Math.round(measurements.airQualityIndex));
        this.pm25 = Long.toString(Math.round(measurements.pm25)) + " μg/m3";
        this.pm10 = Long.toString(Math.round(measurements.pm10)) + " μg/m3";
        this.temperature = Long.toString(Math.round(measurements.temperature)) + "\u2103";
        this.humidity = Long.toString(Math.round(measurements.humidity)) + "%";
    }
}
