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
private static final int[] TWELFTHS = {0, 1, 2, 3, 3, 2, 1};
private final PlanetaryData planetaryData;
int hours;
double tidalForce;
double tidalHeight; // in meters
double maxTidalHeight;// in meters
double minTidalHeight; // in meters

public TidalCalculation(PlanetaryData planetaryData) {
    this.planetaryData = planetaryData;
}
    
public double calculateTidalForce() {
    tidalForce = (2 * planetaryData.getGRAVITATIONAL_CONSTANT() * planetaryData.getMASS_EARTH() * planetaryData.getMASS_MOON() * planetaryData.getRadiusEarth())/(Math.pow(planetaryData.getDistanceEarthMoon(), 3));
    return tidalForce;
}

public double calculateTidalHeight() {
    tidalHeight = (planetaryData.getGRAVITATIONAL_CONSTANT() * planetaryData.getMASS_MOON() * Math.pow(planetaryData.getRadiusEarth(), 2))/(planetaryData.getGravityEarth() * (Math.pow(planetaryData.getDistanceEarthMoon(), 3)));
    return tidalHeight;
}
     
public double calculateRuleOfTwelfths(double startHeight, double nextHeight, int hour) {
        
        if (hour < 0) {
            throw new IllegalArgumentException("Hour must be >= 0 for this example.");
        }

        double currentStart = startHeight;
        double currentEnd   = nextHeight;

        while (hour > 6) {
            hour -= 6;

            double temp = currentStart;
            currentStart = currentEnd;
            currentEnd = temp;
        }

        double range = currentEnd - currentStart;

        int sumTwelfths = 0;
        for (int i = 0; i <= hour; i++) {
            sumTwelfths += TWELFTHS[i];
        }

        double change = (range * sumTwelfths) / 12.0;
        return currentStart + change;
    }

// Semidiurnal lunar tide period (hours). Approx 12.42 hours for M2 constituent.
    public static final double SEMIDIURNAL_PERIOD_HOURS = 12.42;

    /**
     * Compute tide height (meters) at a given simulation time.
     *
     * @param epochSeconds absolute simulation time in seconds (can be simulation time)
     * @param amplitudeMeters base amplitude for the chosen location (meters)
     * @param phaseOffsetRadians phase offset (radians) for local phase (depends on longitude/harbour)
     * @param lunarPhaseFactor multiplier [0.5 - 1.5] to approximate spring/neap.
     * @return tide height in meters (relative to mean sea level)
     */
    public static double computeTide(double epochSeconds, double amplitudeMeters, double phaseOffsetRadians, double lunarPhaseFactor) {
        double hours = epochSeconds / 3600.0;
        double omega = 2.0 * Math.PI / SEMIDIURNAL_PERIOD_HOURS;
        double seaLevel = amplitudeMeters * lunarPhaseFactor * Math.sin(omega * hours + phaseOffsetRadians);

        return seaLevel;
    }
 }

