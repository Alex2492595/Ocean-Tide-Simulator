/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import Model.TideModel;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

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
    private Rectangle waterRect;
    
    @FXML 
    private Label tideLabel;

    private TideModel model;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetIcon.setImage(new Image("file:images/reset.png"));
        pauseIcon.setImage(new Image("file:images/pause.png"));
        playIcon.setImage(new Image("file:images/play.png"));
        
        waterRect.setHeight(0);
        model = new TideModel();  // create a new model here
        setModel(model);         

    }
    
    public void setModel(TideModel model) {
        this.model = model;

        model.simulationSpeedProperty().bind(speedSlider.valueProperty());

        tideLabel.textProperty().bind(model.currentTideProperty().asString("Current Tide: %.2f m"));
        
        model.currentTideProperty().addListener((obs, oldVal, newVal) -> {
            updateWaterLevel(newVal.doubleValue());
        });
        updateGravity();
        updateSpeed();
        updateDistance();
    }
     
     private void updateWaterLevel(double tideMeters) {
        double min = -2;
        double max =  2;
        double maxHeight = 500; 
        double normalized = (tideMeters - min) / (max - min); 
        double newHeight = normalized * maxHeight;

        waterRect.setHeight(newHeight);
        waterRect.setY(0 - newHeight); 
    }
     
    private void updateGravity() {
        gravitySlider.valueProperty().addListener((observeable, oldvalue, newvalue) -> {
       gravityLbl.setText(String.format("%.2f", gravitySlider.getValue()) + " m/s^2");
    });
    }
    
    private void updateSpeed() {
        speedSlider.valueProperty().addListener((observeable, oldvalue, newvalue) -> {
       simulationSpeedLbl.setText(String.format("%.1f", speedSlider.getValue()) + " x");
    });
    }
    
    private void updateDistance() {
        distanceEarthMoonSlider.valueProperty().addListener((observeable, oldvalue, newvalue) -> {
       moonDistanceLbl.setText(String.format("%.1f", distanceEarthMoonSlider.getValue()) + " Km");
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
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
     }
     }
