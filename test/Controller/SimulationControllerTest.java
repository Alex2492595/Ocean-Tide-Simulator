/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Controller;

import Model.PlanetaryData;
import Model.TideModel;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nelson Pham
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class SimulationControllerTest {
    
    @Test
    public void testUpdateDistanceUpdatesModel() {
        PlanetaryData planetaryData = new PlanetaryData();
        TideModel model = new TideModel(planetaryData);

        double newDistance = 400000; //In km
        planetaryData.setDistanceEarthMoon(newDistance * 1000); //In meters

        assertEquals(400000000.0, planetaryData.getDistanceEarthMoon(), 0.1);
    }

    @Test
    public void testUpdateGravityUpdatesModel() {
        PlanetaryData planetaryData = new PlanetaryData();
        TideModel model = new TideModel(planetaryData);

        planetaryData.setGravityEarth(10.5);

        assertEquals(10.5, planetaryData.getGravityEarth(), 0.001);
    }

    @Test
    public void testSunEffectUpdatesModel() {
        PlanetaryData planetaryData = new PlanetaryData();
        TideModel model = new TideModel(planetaryData);

        planetaryData.setSunEffectOn(false);
        assertFalse(planetaryData.isSunEffectOn());

        planetaryData.setSunEffectOn(true);
        assertTrue(planetaryData.isSunEffectOn());
    }
}
