/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import Model.TideModel;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * 
 * @author Alexander Nikopoulos, Mihir Patel, Nelson Pham
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
    private Pane simulPane;
    
    @FXML
    private Rectangle clip; 
    
    @FXML
    private ImageView earth;
    
    @FXML
    private ImageView moon;
    
    @FXML
    private ImageView sun;
    
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
    
    @FXML
    private Circle moonPath; // The circular path of the moon.
    
    @FXML
    private Circle sunPath; // The "path" of the Sun, since the Earth will be still in the simulation. 
    
    private TideModel model;
    
    private RotateTransition earthRotate;
    private PathTransition moonRotate;
    private PathTransition sunRotate; // The "circular path" of the Sun.
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetIcon.setImage(new Image("file:images/reset.png"));
        pauseIcon.setImage(new Image("file:images/pause.png"));
        playIcon.setImage(new Image("file:images/play.png"));
        
        earth.setImage(new Image("file:images/earth.png"));
        moon.setImage(new Image("file:images/moon.png"));
        sun.setImage(new Image("file:images/sun.png"));
        
        clip = new Rectangle();
        clip.widthProperty().bind(simulPane.widthProperty());
        clip.heightProperty().bind(simulPane.heightProperty());
        
        simulPane.setClip(clip); // Clips off parts of the Sun that go outside of the simulPane.
        
        earthRotate = new RotateTransition(Duration.seconds(1), earth); // The Earth will rotate once per day per second.
        moonRotate = new PathTransition();
        sunRotate = new PathTransition();
        
        earthRotate.setByAngle(360);
        earthRotate.setRate(-1); 
        earthRotate.setInterpolator(Interpolator.LINEAR);
        earthRotate.setCycleCount(Timeline.INDEFINITE);
        
        moonRotate.setDuration(Duration.seconds(27.3)); // the Moon will rotate around the Earth in 27.3 days (655.2 hours / (24 hours / day)). 
        moonRotate.setPath(moonPath);
        moonRotate.setNode(moon);
        moonRotate.setRate(-1);
        moonRotate.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        moonRotate.setInterpolator(Interpolator.LINEAR);
        moonRotate.setCycleCount(Timeline.INDEFINITE);
        
        sunRotate.setDuration(Duration.seconds(365.25)); // the Earth will rotate around the Sun in 365.25 days.  
        sunRotate.setPath(sunPath);
        sunRotate.setNode(sun);
        sunRotate.setRate(-1); // Sets the Sun rotating counterclock-wise.
        sunRotate.setOrientation(PathTransition.OrientationType.NONE);
        sunRotate.setInterpolator(Interpolator.LINEAR);
        sunRotate.setCycleCount(Timeline.INDEFINITE);
        
        waterRect.setHeight(0);
        model = new TideModel();  // create a new model here
        setModel(model);         

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double speed = newVal.doubleValue();

            // The original rates were all negative at the start.
            earthRotate.setRate(-speed); 
            moonRotate.setRate(-speed);
            sunRotate.setRate(-speed);
        });
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
        
        earthRotate.play();
        moonRotate.play();
        sunRotate.play();
        
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    @FXML
    private void onPause() {
        model.pause();
        
        earthRotate.pause();
        moonRotate.pause();
        sunRotate.pause();
        
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
    }

    @FXML
    private void onReset() {
        model.pause();
        model.simulationTimeProperty().set(Instant.now().getEpochSecond());
        
        earthRotate.stop();
        moonRotate.stop();
        sunRotate.stop();
        
        earth.setRotate(0);
        moon.setTranslateX(0);
        moon.setTranslateY(0);
        
        sun.setTranslateX(0);
        sun.setTranslateY(0);
        
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
        
        distanceEarthMoonSlider.setValue(384000);
        sunEffectCB.setSelected(true);
        gravitySlider.setValue(9.81);
        speedSlider.setValue(1.0);
     }
}
