package com.farald.airlyconsole;

public class Measurements {
    public final double airQualityIndex;
    public final double humidity;
    public final String measurementTime;
    public final double pm1;
    public final double pm10;
    public final double pm25;
    public final double pollutionLevel;
    public final double pressure;
    public final double temperature;
    public final String windDirection;
    public final double windSpeed;

    public Measurements(double airQualityIndex, double humidity, String measurementTime, double pm1, double pm10, double pm25, double pollutionLevel, double pressure, double temperature, String windDirection, double windSpeed) {
        this.airQualityIndex = airQualityIndex;
        this.humidity = humidity;
        this.measurementTime = measurementTime;
        this.pm1 = pm1;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.pollutionLevel = pollutionLevel;
        this.pressure = pressure;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }
}
