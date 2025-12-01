/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nelson Pham
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class TidalCalculationTest {
    
    @Test
    public void testCalculateTidalHeight() {
        PlanetaryData planetaryData = new PlanetaryData();
        TidalCalculation tidalCalculation = new TidalCalculation(planetaryData);

        double expected = (planetaryData.getGRAVITATIONAL_CONSTANT() * 
                          planetaryData.getMASS_MOON() * 
                          Math.pow(planetaryData.getRadiusEarth(), 2)) /
                          (planetaryData.getGravityEarth() * 
                          Math.pow(planetaryData.getDistanceEarthMoon(), 3));

        double result = tidalCalculation.calculateTidalHeight();
        
        assertEquals(expected, result, 1e-12);
    }
    
    @Test
    public void testComputeTideAtZero() {
        double tide = TidalCalculation.computeTide(0.0, 0.0, 0.0, 0.0);
        
        assertEquals(0.0, tide, 1e-12);
    }

    @Test
    public void testComputeTideWithValues() {
        double tide = TidalCalculation.computeTide(100.0, 2.0, 1.0, 1.0);

        double hours = 100.0 / 3600.0;
        double omega = 2.0 * Math.PI / TidalCalculation.SEMIDIURNAL_PERIOD_HOURS;
        double expected = 2.0 * 1.0 * Math.sin(omega * hours + 1.0);

        assertEquals(expected, tide, 1e-9);
    }
}