package Model;


import java.time.Duration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.time.LocalDate;

/**
 *
 * @author Mihir Patel
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class SimulationModifier {
    private double speed; // Speed of the simulation.
    private LocalDate time = LocalDate.now();; 
    private Duration duration; 

    private PlanetaryData data = new PlanetaryData();
    
    
    /**
     * Calculates the rotation speed of the Earth around the Sun, the Moon around
     * the Earth, and the Earth on its axis multiplied by a user defined factor.
     */
    private void changeRotationRate() {
        double earthAroundSunSpeed = data.getRotationEarthSun();
        double moonAroundEarthSpeed = data.getRotationMoonEarth();
        double rotationEarthSpeed = data.getRotationEarthOnAxis();
        
        earthAroundSunSpeed *= speed;
        moonAroundEarthSpeed *= speed; 
        rotationEarthSpeed *= speed;   
    }
    
    // TODO: Add factors affecting tide
}
