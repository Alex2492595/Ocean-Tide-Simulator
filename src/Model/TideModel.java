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

public class TideModel {

    private final DoubleProperty currentTide = new SimpleDoubleProperty(0.0);
    private final DoubleProperty simulationTime = new SimpleDoubleProperty(Instant.now().getEpochSecond());
    private final DoubleProperty simulationSpeed = new SimpleDoubleProperty(3600.0); // seconds simulated per real second

    private final DoubleProperty amplitudeMeters = new SimpleDoubleProperty(1.5); // default amplitude
    private final DoubleProperty phaseOffsetRadians = new SimpleDoubleProperty(0.0);
    private final DoubleProperty lunarPhaseFactor = new SimpleDoubleProperty(1.0);

    private final Timeline timeline;

    public TideModel() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> tick(0.1)));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    // tick simulated seconds = realSeconds * simulationSpeed
    private void tick(double realSeconds) {
        double simSecondsAdvance = realSeconds * simulationSpeed.get();
        double newSimTime = simulationTime.get() + simSecondsAdvance;
        simulationTime.set(newSimTime);

        double tide = TidalCalculation.computeTide(
                newSimTime,
                amplitudeMeters.get(),
                phaseOffsetRadians.get(),
                lunarPhaseFactor.get()
        );
        currentTide.set(tide);
    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    // Properties
    public DoubleProperty currentTideProperty() { return currentTide; }
    public DoubleProperty simulationTimeProperty() { return simulationTime; }
    public DoubleProperty simulationSpeedProperty() { return simulationSpeed; }
    public DoubleProperty amplitudeMetersProperty() { return amplitudeMeters; }
    public DoubleProperty phaseOffsetRadiansProperty() { return phaseOffsetRadians; }
    public DoubleProperty lunarPhaseFactorProperty() { return lunarPhaseFactor; }
}
