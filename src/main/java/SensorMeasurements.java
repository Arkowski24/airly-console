public class SensorMeasurements {
    public final Measurements currentMeasurements;
    public final MeasurementsWithInterval[] history;
    public final MeasurementsWithInterval[] forecast;

    public SensorMeasurements(Measurements currentMeasurements, MeasurementsWithInterval[] history, MeasurementsWithInterval[] forecast) {
        this.currentMeasurements = currentMeasurements;
        this.history = history;
        this.forecast = forecast;
    }
}
