package edu.ucdenver.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage newStage = new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("catalogUI.fxml"));
        primaryStage.setTitle("Customer Server Connection");
        primaryStage.setScene(new Scene(root1, 720, 800));
        showSecondaryStage(newStage);
        if(customerLoginController.isloggedin) {
            newStage.close();
            primaryStage.show();
        }
    }

    private void showSecondaryStage(Stage newStage) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("customerLoginUI.fxml"));
        newStage.setTitle("Customer Application");
        newStage.setScene(new Scene(root2,670,500));
        newStage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
