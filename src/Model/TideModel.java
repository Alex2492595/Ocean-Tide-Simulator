/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;
import java.time.Instant;

/**
 *
 * @author Nelson Pham, Alexander Nikopoulos
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class TideModel {

    private final PlanetaryData planetaryData;
    
    private final DoubleProperty currentTide = new SimpleDoubleProperty(0.0);
    private final DoubleProperty simulationTime = new SimpleDoubleProperty(Instant.parse("2025-01-01T00:00:00Z").getEpochSecond()); //Sets the time for when Earth is closest to Sun (with Moon in between)
    private final DoubleProperty simulationSpeed = new SimpleDoubleProperty(1.0); //Seconds simulated per real second

    private final DoubleProperty amplitudeMeters = new SimpleDoubleProperty(2); //Default amplitude
    private final DoubleProperty phaseOffsetRadians = new SimpleDoubleProperty(0.0);
    private final DoubleProperty lunarPhaseFactor = new SimpleDoubleProperty(1.0);

    private final Timeline timeline;

    public TideModel(PlanetaryData planetaryData) {
        this.planetaryData = planetaryData;
        
        //The time in which the tide updates along with its simulated speed
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> tick(864)));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Calculates the tick rate for the simulation of the tide
     * @param realSeconds the number of real seconds to be simulated
     */
    private void tick(double realSeconds) {
        //Tick simulated seconds = realSeconds * simulationSpeed        
        double simSecondsAdvance = realSeconds * simulationSpeed.get();
        double newSimTime = simulationTime.get() + simSecondsAdvance;
        simulationTime.set(newSimTime);
        
        TidalCalculation tidalCalculation = new TidalCalculation(planetaryData);
        double sunFactor = planetaryData.isSunEffectOn() ? 0.45 : 0; //The Sun's effect on tides compared to the Moon is ~45%
        double totalAmplitude = tidalCalculation.calculateTidalHeight() * (1 + sunFactor);
        
        double tide = TidalCalculation.computeTide(
                newSimTime,
                totalAmplitude,
                phaseOffsetRadians.get(),
                lunarPhaseFactor.get()
        );
        
        currentTide.set(tide);
    }

    //Used to start the tide animation
    public void start() {
        timeline.play();
    }

    //Used to pause the tide animation
    public void pause() {
        timeline.pause();
    }

    //Used to check if the tide animation is running
    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    //Properties
    public DoubleProperty currentTideProperty() { return currentTide; }
    public DoubleProperty simulationTimeProperty() { return simulationTime; }
    public DoubleProperty simulationSpeedProperty() { return simulationSpeed; }
    public DoubleProperty amplitudeMetersProperty() { return amplitudeMeters; }
    public DoubleProperty phaseOffsetRadiansProperty() { return phaseOffsetRadians; }
    public DoubleProperty lunarPhaseFactorProperty() { return lunarPhaseFactor; }
    
    public PlanetaryData getPlanetaryData() {
        return planetaryData;
    }
}
