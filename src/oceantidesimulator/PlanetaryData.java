/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oceantidesimulator;

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
    
    private boolean sunEffect; //The Sun's gravitational effect
    private double distanceEarthMoon; //In meters (default)
    private double distanceEarthSun; //In meters (default January 1st)
    private double gravityEarth; //In meters per second^2 (default)
    private double rotationEarthSun; //In revolutions per minute
    private double rotationMoonEarth; //In revolutions per minute
    private double rotationEarthOnAxis; //In revolutions per minute

    public PlanetaryData() {
        this.sunEffect = true;
        this.distanceEarthMoon = 3.84e8;
        this.distanceEarthSun = 1.47e11;
        this.gravityEarth = 9.81;
        this.rotationEarthSun = 7e-4;
        this.rotationMoonEarth = 2.5e-5;
        this.rotationEarthOnAxis = 6.9e-4;
    }

    public boolean isSunEffect() {
        return sunEffect;
    }

    public void setSunEffect(boolean sunEffect) {
        this.sunEffect = sunEffect;
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
