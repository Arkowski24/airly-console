public class PrettyMeasurements {
    public final String caqi;
    public final String pm25;
    public final String pm25NormPercent;
    public final String pm10;
    public final String pm10NormPercent;
    public final String temperature;
    public final String humidity;

    public PrettyMeasurements(Measurements measurements, int numbersLength) {
        double pm25Norm = 25;
        double pm10Norm = 40;
        this.caqi = Long.toString(Math.round(measurements.airQualityIndex));
        this.pm25 = getAdjustedNumber(Math.round(measurements.pm25), numbersLength) + " μg/m3";
        this.pm25NormPercent = Long.toString(Math.round(measurements.pm25 / pm25Norm * 100)) + "%";
        this.pm10 = getAdjustedNumber(Math.round(measurements.pm10), numbersLength) + " μg/m3";
        this.pm10NormPercent = Long.toString(Math.round(measurements.pm10 / pm10Norm * 100)) + "%";
        this.temperature = Long.toString(Math.round(measurements.temperature)) + "\u2103";
        this.humidity = Long.toString(Math.round(measurements.humidity)) + "%";
    }

    public static String getAdjustedNumber(Number index, int length){
        return String.format("%1$" + length + "s", index);
    }
}
