/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oceantidesimulator;

import java.time.Duration;

/**
 * 
 * @author Alexander Nikopoulos
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class PlanetaryData {
    
    private static final double GRAVITATIONAL_CONSTANT = 6.67e-11; //In m^3/kg*s^2
    private static final double MASS_EARTH = 5.97e24; //In kilograms
    private static final double MASS_MOON = 7.35e22; //In kilograms
    private static final double MASS_SUN = 1.99e30; //In kilograms
    
    private boolean sunEffectOn; //The Sun's gravitational effect
    private double distanceEarthMoon; //In meters (default)
    private double distanceEarthSun; //In meters (default January 1st)
    private double gravityEarth; //In meters per second^2 (default)
    private double rotationEarthSun; //In degrees per day
    private double rotationMoonEarth; //In degrees per hour
    private double rotationEarthOnAxis; //In degrees per hour
    private Duration duration; //The time duration for the simulation

    public PlanetaryData() {
        this.sunEffectOn = true;
        this.distanceEarthMoon = 3.84e8;
        this.distanceEarthSun = 1.47e11;
        this.gravityEarth = 9.81;
        this.rotationEarthSun = 360 / 365.25;
        this.rotationMoonEarth = 360 / 655.2;
        this.rotationEarthOnAxis = 360 / 24;
    }
    
    /**
     * Calculates the moon's gravitational force with respect to the Earth
     * @return the moon's gravitational force
     */
    public double calculateMoonForce() {
        return GRAVITATIONAL_CONSTANT * ((MASS_MOON * MASS_EARTH) / Math.pow(distanceEarthMoon, 2));
    }
    
    /**
     * Calculates the Sun's gravitational force with respect to the Earth
     * @return the Sun's gravitational force
     */
    public double calculateSunForce() {
        if (sunEffectOn) {
            return GRAVITATIONAL_CONSTANT * ((MASS_SUN * MASS_EARTH) / Math.pow(distanceEarthSun, 2));
        } else {
            return 0;
        }
    }
    
    /**
     * Calculates where the Earth, Sun and Moon would be after a duration of time. 
     */
    public void changeCelestialBodiesPosition() {
        double numOfDays = duration.toDays();
        double numOfHours = duration.toHours();
        
        double positionOfMoon = rotationMoonEarth * numOfHours; // The angular displacement of the Moon around the Earth.
        double positionOfEarth = rotationEarthSun * numOfDays; // The angular displacement of the Earth around the Sun. 
        
        while (positionOfMoon >= 360) {// Subtracts 360 degrees when the angular displacement of the Moon when it's greater than 360 degrees.
            positionOfMoon -= 360;
        }
        
        while (positionOfEarth >= 360) {// Subtracts 360 degrees when the angular displacement of the Earth when it's greater than 360 degrees.
            positionOfEarth -= 360;
        }
    }

    public boolean isSunEffectOn() {
        return sunEffectOn;
    }

    public void setSunEffectOn(boolean sunEffectOn) {
        this.sunEffectOn = sunEffectOn;
    }

    public double getDistanceEarthMoon() {
        return distanceEarthMoon;
    }

    public void setDistanceEarthMoon(double distanceEarthMoon) {
        this.distanceEarthMoon = distanceEarthMoon;
    }

    public double getDistanceEarthSun() {
        return distanceEarthSun;
    }

    public void setDistanceEarthSun(double distanceEarthSun) {
        this.distanceEarthSun = distanceEarthSun;
    }

    public double getGravityEarth() {
        return gravityEarth;
    }

    public void setGravityEarth(double gravityEarth) {
        this.gravityEarth = gravityEarth;
    }

    public double getRotationEarthSun() {
        return rotationEarthSun;
    }

    public void setRotationEarthSun(double rotationEarthSun) {
        this.rotationEarthSun = rotationEarthSun;
    }

    public double getRotationMoonEarth() {
        return rotationMoonEarth;
    }

    public void setRotationMoonEarth(double rotationMoonEarth) {
        this.rotationMoonEarth = rotationMoonEarth;
    }

    public double getRotationEarthOnAxis() {
        return rotationEarthOnAxis;
    }

    public void setRotationEarthOnAxis(double rotationEarthOnAxis) {
        this.rotationEarthOnAxis = rotationEarthOnAxis;
    }
}
