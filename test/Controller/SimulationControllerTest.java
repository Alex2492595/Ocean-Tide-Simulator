/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Controller;

import Model.TideModel;
import java.net.URL;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nelso
 */
public class SimulationControllerTest {
    
    public SimulationControllerTest() {
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
    }

    /**
     * Test of setModel method, of class SimulationController.
     */
    @Test
    public void testSetModel() {
        System.out.println("setModel");
        TideModel model = null;
    }
}
