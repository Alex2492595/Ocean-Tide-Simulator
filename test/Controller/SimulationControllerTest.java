/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Controller;

import Model.PlanetaryData;
import Model.TideModel;
import java.net.URL;
import java.util.ResourceBundle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nelson Pham
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class SimulationControllerTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
    
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

    /**
     * Test of initialize method, of class SimulationController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        SimulationController instance = new SimulationController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModel method, of class SimulationController.
     */
    @Test
    public void testSetModel() {
        System.out.println("setModel");
        TideModel model = null;
        SimulationController instance = new SimulationController();
        instance.setModel(model);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
