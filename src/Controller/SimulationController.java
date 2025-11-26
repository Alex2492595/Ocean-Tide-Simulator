/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import Model.TideModel;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.chart.*;

/**
 * 
 * @author Alexander Nikopoulos
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class SimulationController implements Initializable {

    @FXML
    private TextField dateAndTimeTF;
    
    @FXML
    private Label moonDistanceLbl;
    
    @FXML
    private Slider distanceEarthMoonSlider;
    
    @FXML
    private CheckBox sunEffectCB;
    
    @FXML
    private Label gravityLbl;
    
    @FXML
    private Slider gravitySlider;
    
    @FXML
    private Label simulationSpeedLbl;
    
    @FXML
    private Slider speedSlider;
    
    @FXML
    private Button resetBtn;
    
    @FXML
    private Button pauseBtn;
    
    @FXML
    private Button playBtn;
    
    @FXML
    private ImageView resetIcon;
    
    @FXML
    private ImageView pauseIcon;
    
    @FXML
    private ImageView playIcon;
    
    @FXML
    private LineChart<Number, Number> tideChart;
    
    @FXML
    private NumberAxis yAxis;

    private Model.TideModel model;
    private XYChart.Series<Number, Number> series;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetIcon.setImage(new Image("file:images/reset.png"));
        pauseIcon.setImage(new Image("file:images/pause.png"));
        playIcon.setImage(new Image("file:images/play.png"));
        
        yAxis.setLabel("Tide (m)");
        series = new XYChart.Series<>();
        series.setName("Tidal Height");
        tideChart.getData().add(series);

    }
    
     public void setModel(TideModel model) {
        this.model = model;

        // Bind slider to model speed
        model.simulationSpeedProperty().bind(speedSlider.valueProperty());

        // Update chart with new values periodically
        // We choose a listener to currentTide to sample the series
        model.currentTideProperty().addListener((obs, oldVal, newVal) -> {
            double simTime = model.simulationTimeProperty().get();
            double tide = newVal.doubleValue();

            // sample every ~10 seconds of simulated time to reduce points
            Platform.runLater(() -> {
                series.getData().add(new XYChart.Data<>(simTime, tide));
                // Keep last N points to avoid memory growth
                if (series.getData().size() > 500) {
                    series.getData().remove(0, series.getData().size() - 400);
                }
            });
        });
     }
        
    @FXML
    private void onPlay() {
        model.start();
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    @FXML
    private void onPause() {
        model.pause();
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
    }

    @FXML
    private void onReset() {
        model.pause();
        model.simulationTimeProperty().set(Instant.now().getEpochSecond());
        series.getData().clear();
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
     }
     }
