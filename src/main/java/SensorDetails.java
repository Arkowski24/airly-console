public class SensorDetails {
    public final int sensorId;
    public final String name;
    public final String vendor;
    public final Location location;
    public final Address address;
    public final double pollutionLevel;

    public SensorDetails(int sensorId, String name, String vendor, Location location, Address address, int pollutionLevel) {
        this.sensorId = sensorId;
        this.name = name;
        this.vendor = vendor;
        this.location = location;
        this.address = address;
        this.pollutionLevel = pollutionLevel;
    }

    public SensorDetails(NearestMeasurements measurements){
        this.sensorId = measurements.id;
        this.name = measurements.name;
        this.vendor = measurements.vendor;
        this.location = measurements.location;
        this.address = measurements.address;
        this.pollutionLevel = measurements.pollutionLevel;
    }
}
