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
    
    public TidalCalculationTest() {
    } 

    /**
     * Test of calculateTidalHeight method, of class TidalCalculation.
     */
    @Test
    public void testCalculateTidalHeightZero() {
        System.out.println("calculateTidalHeight");
        TidalCalculation instance = null;
        double expResult = 0.0;
        double result = instance.calculateTidalHeight();
        assertEquals(expResult, result, 0);
        fail("The test case is a prototype.");
    }

     /**
     * Test of computeTide method with 0, of class TidalCalculation.
     */
    @Test
    public void testComputeTideZero() {
        System.out.println("computeTide");
        double epochSeconds = 0.0;
        double amplitudeMeters = 0.0;
        double phaseOffsetRadians = 0.0;
        double lunarPhaseFactor = 0.0;
        double expResult = 0.0;
        double result = TidalCalculation.computeTide(epochSeconds, amplitudeMeters, phaseOffsetRadians, lunarPhaseFactor);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of computeTide method with regular numbers, of class TidalCalculation.
     */
    @Test
    public void testComputeTide1() {
        System.out.println("computeTide");
        double epochSeconds = 100.0;
        double amplitudeMeters = 2.0;
        double phaseOffsetRadians = 1.0;
        double lunarPhaseFactor = 1.0;
        double expResult = 1.697960576;
        double result = TidalCalculation.computeTide(epochSeconds, amplitudeMeters, phaseOffsetRadians, lunarPhaseFactor);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of computeTide method with regular numbers, of class TidalCalculation.
     */
    @Test
    public void testComputeTide2() {
        System.out.println("computeTide");
        double epochSeconds = 3600.0;
        double amplitudeMeters = 4.0;
        double phaseOffsetRadians = 0.0;
        double lunarPhaseFactor = 2.0;
        double expResult = 3.876706984;
        double result = TidalCalculation.computeTide(epochSeconds, amplitudeMeters, phaseOffsetRadians, lunarPhaseFactor);
        assertEquals(expResult, result);
    }
    
}
