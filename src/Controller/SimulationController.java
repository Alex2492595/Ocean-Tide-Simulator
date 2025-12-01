/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import Model.PlanetaryData;
import Model.TideModel;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
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
    private Circle moonPath; //The circular path of the moon.
    
    @FXML
    private Circle sunPath; //The "path" of the Sun, since the Earth will be still in the simulation.
    
    private TideModel model;
    
    private RotateTransition earthRotate;
    private PathTransition moonRotate;
    private PathTransition sunRotate; //The "circular path" of the Sun.
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Sets the images needed in the simulation
        resetIcon.setImage(new Image("file:images/reset.png"));
        pauseIcon.setImage(new Image("file:images/pause.png"));
        playIcon.setImage(new Image("file:images/play.png"));
        
        earth.setImage(new Image("file:images/earth.png"));
        moon.setImage(new Image("file:images/moon.png"));
        sun.setImage(new Image("file:images/sun.png"));
        
        clip = new Rectangle();
        clip.widthProperty().bind(simulPane.widthProperty());
        clip.heightProperty().bind(simulPane.heightProperty());
        
        simulPane.setClip(clip); //Clips off parts of the Sun that go outside of the simulPane.
        
        earthRotate = new RotateTransition(Duration.seconds(1), earth); //The Earth will rotate once per day per second.
        moonRotate = new PathTransition();
        sunRotate = new PathTransition();
        
        earthRotate.setByAngle(360);
        earthRotate.setRate(-1); 
        earthRotate.setInterpolator(Interpolator.LINEAR);
        earthRotate.setCycleCount(Timeline.INDEFINITE);
        
        moonRotate.setDuration(Duration.seconds(27.3)); //The Moon will rotate around the Earth in 27.3 days (655.2 hours / (24 hours / day)). 
        moonRotate.setPath(moonPath);
        moonRotate.setNode(moon);
        moonRotate.setRate(-1);
        moonRotate.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        moonRotate.setInterpolator(Interpolator.LINEAR);
        moonRotate.setCycleCount(Timeline.INDEFINITE);
        
        sunRotate.setDuration(Duration.seconds(365.25)); //The Earth will rotate around the Sun in 365.25 days.  
        sunRotate.setPath(sunPath);
        sunRotate.setNode(sun);
        sunRotate.setRate(-1);
        sunRotate.setOrientation(PathTransition.OrientationType.NONE);
        sunRotate.setInterpolator(Interpolator.LINEAR);
        sunRotate.setCycleCount(Timeline.INDEFINITE);
        
        PlanetaryData planetaryData = new PlanetaryData();
        
        //Creates the tide model
        waterRect.setHeight(0);
        model = new TideModel(planetaryData);
        setModel(model);         

        //How speed will impact the celestial bodies
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double speed = newVal.doubleValue();
            
            earthRotate.setRate(-speed);
            moonRotate.setRate(-speed);
            sunRotate.setRate(-speed);
        });
        
        //Sets the format and updates the date and time
        model.simulationTimeProperty().addListener((obs, oldValue, newValue) -> {
            long epochSeconds = newValue.longValue();
            LocalDateTime simDateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC);

            dateAndTimeTF.setText(simDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });
    }
    
    /**
     * Sets the current tide and updates it according to user-set values
     * @param model the tide model
     */
    public void setModel(TideModel model) {
        this.model = model;

        model.simulationSpeedProperty().bind(speedSlider.valueProperty());

        tideLabel.textProperty().bind(model.currentTideProperty().asString("Current Tide: %.2f m"));
        
        //The current tide level is updated
        model.currentTideProperty().addListener((obs, oldVal, newVal) -> {
            updateWaterLevel(newVal.doubleValue());
        });
        
        //Updates of user-set values
        updateGravity();
        updateSpeed();
        updateDistance();
        updateSunEffect();
    }
    
    /**
     * Updates the water level of the tide
     * @param tideMeters the tide level in meters
     */
    private void updateWaterLevel(double tideMeters) {
        double min = -2;
        double max =  2;
        double maxHeight = 500; 
        double normalized = (tideMeters - min) / (max - min); 
        double newHeight = normalized * maxHeight;

        waterRect.setHeight(newHeight);
        waterRect.setY(0 - newHeight); 
    }
    
    //Updates the gravity and how it affects the tide
    private void updateGravity() {
        gravitySlider.valueProperty().addListener((observeable, oldValue, newValue) -> {
            gravityLbl.setText(String.format("%.2f", gravitySlider.getValue()) + " m/s^2");
            
            model.getPlanetaryData().setGravityEarth(gravitySlider.getValue());
        });
    }
    
    //Updates the speed of the simulation
    private void updateSpeed() {
        speedSlider.valueProperty().addListener((observeable, oldValue, newValue) -> {
            simulationSpeedLbl.setText(String.format("%.2f", speedSlider.getValue()) + " x");
        });
    }
    
    //Updates the earth-moon distance and how it affects the tide
    private void updateDistance() {
        distanceEarthMoonSlider.valueProperty().addListener((observeable, oldValue, newValue) -> {
            moonDistanceLbl.setText(String.format("%.0f", distanceEarthMoonSlider.getValue()) + " Km");

            //Updates the tide visually
            model.getPlanetaryData().setDistanceEarthMoon(distanceEarthMoonSlider.getValue() * 1000);
            
            //Updates the moon visually
            double progress = 0;
            
            if (moonRotate.getStatus() == Animation.Status.RUNNING || moonRotate.getStatus() == Animation.Status.PAUSED) {
                double currentTime = moonRotate.getCurrentTime().toMillis();
                double totalTime = moonRotate.getCycleDuration().toMillis();
                
                progress = currentTime / totalTime;
            }
            
            moonRotate.stop();
            moonPath.setRadius((newValue.doubleValue() / 384000) * 100);
            moonRotate.setPath(moonPath);
            
            Duration newTime = moonRotate.getCycleDuration().multiply(progress);
            moonRotate.jumpTo(newTime);
            moonRotate.play();
        });
    }
    
    //Updates the effect of the Sun on the tide
    private void updateSunEffect() {
        sunEffectCB.selectedProperty().addListener((obs, oldValue, newValue) -> {
            model.getPlanetaryData().setSunEffectOn(newValue);
            
            double opacity = newValue ? 1 : 0.25;
            
            FadeTransition fade = new FadeTransition(Duration.millis(300), sun);
            fade.setToValue(opacity);
            fade.play();
        });
    }
    
    //When play button is pressed
    @FXML
    private void onPlay() {
        model.start();
        
        earthRotate.play();
        moonRotate.play();
        sunRotate.play();
        
        playBtn.setDisable(true);
        pauseBtn.setDisable(false);
    }

    //When pause button is pressed
    @FXML
    private void onPause() {
        model.pause();
        
        earthRotate.pause();
        moonRotate.pause();
        sunRotate.pause();
        
        playBtn.setDisable(false);
        pauseBtn.setDisable(true);
    }

    //When reset button is pressed
    @FXML
    private void onReset() {
        model.pause();
        model.simulationTimeProperty().set(Instant.parse("2025-01-01T00:00:00Z").getEpochSecond());
        
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
        
        earthRotate.stop();
        moonRotate.stop();
        sunRotate.stop();
     }
}
