package edu.ucdenver.application;

import edu.ucdenver.server.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root1 = FXMLLoader.load(getClass().getResource("adminUI.fxml"));
        primaryStage.setTitle("Admin Login Application");
        primaryStage.setScene(new Scene(root1, 720, 480));
        showSecondaryStage();
        primaryStage.show();
    }

    private void showSecondaryStage() throws IOException {
        Stage newStage = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("adminLoginUI.fxml"));
        newStage.setTitle("Admin Application");
        newStage.setScene(new Scene(root2,720,480));
        newStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
