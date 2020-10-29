package edu.ucdenver.application;

import edu.ucdenver.server.Client;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextFlow;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class adminLoginController {
    public TextField txtAdminServer;
    public TextField txtAdminPort;
    public TextField txtAdminEmail;
    public TextField txtAdminPassword;
    public TextField txtServerPortErrorMessage;
    public TextField txtAdminLoginErrorMessage;
    public Button btnConnectAdmin;
    public Button btnLogin;
    public Tab tabAdminConnect;
    public Tab tabAdminLogin;

    Client client;


    public adminLoginController() {


    }

    public void ConnectToServer(ActionEvent actionEvent) {


    }

    public void AdminLoginToServer(ActionEvent actionEvent){

    }



}
