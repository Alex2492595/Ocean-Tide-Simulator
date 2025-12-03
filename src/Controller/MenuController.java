/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author Alexander Nikopoulos
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class MenuController implements Initializable {

    private Stage stage;
    
    @FXML
    private ImageView logoIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logoIcon.setImage(new Image("file:images/logo1.png"));
    }
    
    /**
     * Switches the scene to the simulation once start is pressed
     * @param event 
     */
    @FXML
    private void switchScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Simulation.fxml"));
            
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();   
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
