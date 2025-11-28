/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
    private Slider gravitySlider1;
    
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
    private Circle moonPath; // The circular path of the moon.
    
    @FXML
    private Circle sunPath; // The "path" of the Sun, since the Earth will be still in the simulation. 
    
    private RotateTransition earthRotate;
    private PathTransition moonRotate;
    private PathTransition sunRotate; // The "circular path" of the Sun.
    
    /**
     * Plays the animation of the Earth, Sun and Moon.
     * @param event 
     */
    @FXML
    void playButtonPressed(ActionEvent event) {
        earthRotate.play();
        moonRotate.play();
        sunRotate.play();
    }
    
    /**
     * Pauses the animation of the Earth, Sun and Moon.
     * @param event 
     */
    @FXML
    void pauseButtonPressed(ActionEvent event) {
        earthRotate.pause();
        moonRotate.pause();
        sunRotate.pause();
    }
    
    /**
     * Resets the animation of the Earth, Sun and Moon to the start.
     * @param event 
     */
    @FXML
    void resetButtonPressed(ActionEvent event) {
        earthRotate.stop();
        moonRotate.stop();
        sunRotate.stop();
        
        earth.setRotate(0);
        moon.setTranslateX(0);
        moon.setTranslateY(0);
        
        sun.setTranslateX(0);
        sun.setTranslateY(0);
    }
    
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
        
        moonRotate.setDuration(Duration.seconds(27.3)); // the Earth has to rotate 27.3 times for the Moon to revolve once.  
        moonRotate.setPath(moonPath);
        moonRotate.setNode(moon);
        moonRotate.setRate(-1);
        moonRotate.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        moonRotate.setInterpolator(Interpolator.LINEAR);
        moonRotate.setCycleCount(Timeline.INDEFINITE);
        
        sunRotate.setDuration(Duration.seconds(365.25)); // the Earth has to rotate 27.3 times for the Moon to revolve once.  
        sunRotate.setPath(sunPath);
        sunRotate.setNode(sun);
        sunRotate.setRate(-1); // Sets the Sun rotating counterclock-wise.
        sunRotate.setOrientation(PathTransition.OrientationType.NONE);
        sunRotate.setInterpolator(Interpolator.LINEAR);
        sunRotate.setCycleCount(Timeline.INDEFINITE);
    }
}
