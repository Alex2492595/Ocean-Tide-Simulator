/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Nelson Pham
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class TidalCalculation {
    private final PlanetaryData planetaryData;
    double tidalHeight; //In meters

    public TidalCalculation(PlanetaryData planetaryData) {
        this.planetaryData = planetaryData;
    }

    //Calculates the amplitude of the tide
    public double calculateTidalHeight() {
        tidalHeight = (planetaryData.getGRAVITATIONAL_CONSTANT() * planetaryData.getMASS_MOON() * Math.pow(planetaryData.getRadiusEarth(), 2))/(planetaryData.getGravityEarth() * (Math.pow(planetaryData.getDistanceEarthMoon(), 3)));
        return tidalHeight;
    }

    //Semidiurnal lunar tide period (hours). Approximately 12.42 hours for M2 constituent.
    public static final double SEMIDIURNAL_PERIOD_HOURS = 12.42;

    /**
     * Compute tide height (meters) at a given simulation time.
     *
     * @param epochSeconds absolute simulation time (seconds)
     * @param amplitudeMeters base amplitude (meters)
     * @param phaseOffsetRadians phase offset (radians) for local phase
     * @param lunarPhaseFactor multiplier [0.5 - 1.5] to approximate spring/neap tides
     * @return tide height in meters (relative to mean sea level)
     */
    public static double computeTide(double epochSeconds, double amplitudeMeters, double phaseOffsetRadians, double lunarPhaseFactor) {
        double hours = epochSeconds / 3600.0;
        double omega = 2.0 * Math.PI / SEMIDIURNAL_PERIOD_HOURS;
        double seaLevel = amplitudeMeters * lunarPhaseFactor * Math.sin(omega * hours + phaseOffsetRadians);

        return seaLevel;
    }
 }
