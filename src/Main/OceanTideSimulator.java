//https://github.com/Alex2492595/Ocean-Tide-Simulator

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.MenuController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Alexander Nikopoulos, Mihir Patel, Nelson Pham
 * Team Project - OceanTideSimulator
 * 30/11/2025
 */
public class OceanTideSimulator extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
        
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Resources/Style.css").toExternalForm());
        stage.setTitle("Ocean Tide Simulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
