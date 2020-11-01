package edu.ucdenver.application;
import edu.ucdenver.server.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;


public class customerLoginController {
    public TextField txtServerPortErrorMessage;
    public TextField txtUserServerIP;
    public TextField txtUserPortNum;
    public Button btnAttemptConnection;
    
    Client client;

    public customerLoginController(){

    }

    public void ConnectUser(ActionEvent actionEvent){
        String serverIP = txtUserServerIP.getText();
        String serverport = txtUserPortNum.getText();
        int serverportint = Integer.parseInt(serverport);
        client = new Client(serverIP,serverportint);
        client.connect();
        String cmd = "TEST|";

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
    
    
}
