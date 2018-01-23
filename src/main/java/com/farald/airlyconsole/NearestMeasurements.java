package com.farald.airlyconsole;

public class NearestMeasurements extends Measurements {
    public final int id;
    public final String name;
    public final String vendor;
    public final Location location;
    public final Address address;

    public NearestMeasurements(double airQualityIndex, double humidity, String measurementTime, double pm1, double pm10, double pm25, double pollutionLevel, double pressure, double temperature, String windDirection, double windSpeed, int id, String name, String vendor, Location location, Address address) {
        super(airQualityIndex, humidity, measurementTime, pm1, pm10, pm25, pollutionLevel, pressure, temperature, windDirection, windSpeed);
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.location = location;
        this.address = address;
    }
}
