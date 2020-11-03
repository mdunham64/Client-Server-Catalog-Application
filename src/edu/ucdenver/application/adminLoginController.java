package edu.ucdenver.application;

import edu.ucdenver.server.Client;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextFlow;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;


public class adminLoginController {
    public static boolean isloggedin = false;
    public TextField txtAdminServer;
    public TextField txtAdminPort;
    public TextField txtAdminEmail;
    public TextField txtAdminPassword;
    public Button btnConnectAdmin;
    public Button btnLogin;
    public Tab tabAdminConnect;
    public Tab tabAdminLogin;

    Client client;


    public adminLoginController() {

    }
    public void showAlert(String cmd){
        Alert alert;
        if(client.isConnected()){
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]) {
                    case "OK":
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "Action complete: " + respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                    case "ERR":
                        alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR, "Server Response:" + e.getMessage(), ButtonType.OK);
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR, "Client is not connected", ButtonType.OK);
            alert.show();
        }
    }

    public void ConnectToServer(ActionEvent actionEvent) {
        String serverip = txtAdminServer.getText();
        String port = txtAdminPort.getText();
        int portnum = Integer.parseInt(port);
        if((serverip.equalsIgnoreCase("localhost")) && (portnum == 10001)) {
            client = new Client(serverip, portnum);
            client.connect();
            String cmd = "TEST|";
            showAlert(cmd);
        }
        else{
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Incorrect server IP or port number. Try using IP: localhost and port: 10001",ButtonType.OK);
            alert.show();
        }
    }

    public void AdminLoginToServer(ActionEvent actionEvent){
        String email = txtAdminEmail.getText();
        String password = txtAdminPassword.getText();
        String cmd = "AL|" + email + "|" + password;

        Alert alert;
        if(client.isConnected()){
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]) {
                    case "OK":
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "Action complete: " + respArgs[1], ButtonType.OK);
                        alert.show();
                        isloggedin=true;
                        break;
                    case "ERR":
                        alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR, "Server Response:" + e.getMessage(), ButtonType.OK);
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR, "Client is not connected", ButtonType.OK);
            alert.show();
        }
    }
}
