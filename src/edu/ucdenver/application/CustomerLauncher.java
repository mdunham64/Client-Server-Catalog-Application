package edu.ucdenver.application;

import edu.ucdenver.server.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root1 = FXMLLoader.load(getClass().getResource("catalogUI.fxml"));
        primaryStage.setTitle("Customer Server Connection");
        primaryStage.setScene(new Scene(root1, 1000, 800));
        showSecondaryStage();
        primaryStage.show();
    }

    private void showSecondaryStage() throws IOException {
        Stage newStage = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("customerLoginUI.fxml"));
        newStage.setTitle("Customer Application");
        newStage.setScene(new Scene(root2,720,400));
        newStage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
