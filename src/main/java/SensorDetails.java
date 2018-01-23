public class SensorDetails {
    public final int id;
    public final String name;
    public final String vendor;
    public final Location location;
    public final Address address;
    public final int pollutionLevel;

    public SensorDetails(int id, String name, String vendor, Location location, Address address, int pollutionLevel) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.location = location;
        this.address = address;
        this.pollutionLevel = pollutionLevel;
    }
}
