/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oceantidesimulator;

/**
 *
 * @author 6317924
 */
public class TidalCalculation {
private static final int[] TWELFTHS = {0, 1, 2, 3, 3, 2, 1};
PlanetaryData planetaryData = new PlanetaryData();
int hours;
double tidalForce;
double tidalHeight; // in meters
double maxTidalHeight;// in meters
double minTidalHeight; // in meters
    
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
 }

