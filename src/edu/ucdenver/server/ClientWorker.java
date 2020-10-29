package edu.ucdenver.server;

import edu.ucdenver.store.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

public class ClientWorker implements Runnable {
    private final Socket clientConnection;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;
    private final int id;

    public ClientWorker(Socket connection, int id){
        this.clientConnection = connection;
        this.keepRunningClient = true;
        this.id = id;
    }

    private void getOutputStream(Socket clientConnection) throws IOException {
        this.output = new PrintWriter(clientConnection.getOutputStream(), true);
    }

    private void getInputStream(Socket clientConnection) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }

    private void displayMessage(String message){
        System.out.printf("CLIENT[%d] >> %s%n",this.id, message);
    }

    private void processClientRequest() throws IOException {
        String clientMessage = this.input.readLine(); //recv from client
        displayMessage("CLIENT SAID>>>" + clientMessage);

        String[] arguments = clientMessage.split("\\|"); // this splits the string using | as the delimiter
        String response = ""; //This will bethe response to the server.

        try {
            switch (arguments[0]) { //arguments[0] must be the command
                case "CNU": // create new user
                    response = "OK|Successfully Added new user.";
                    //TODO  :   I made this an admin for now. Fourth arg is a boolean. Probably broke it.
                    User temp = new User(arguments[1],arguments[2],arguments[3], true);
                    break;
                case "LAU": // login a user
                    break;
                case "ANP": // add new product ADMIN
                    break;
                case "RP": // remove product ADMIN
                    break;
                case "ACP": // add category to product ADMIN
                    break;
                case "DCP": // delete category to product ADMIN
                    break;
                case "ANC": // add new category ADMIN
                    break;
                case "SDC": // set default category ADMIN
                    break;
                case "DC": // delete category ADMIN
                    break;
                case "SEARCH": // search the product list for keyword CUSTOMER
                    break;
                case "BC": // browse category list all products CUSTOMER
                    break;
                case "AP": // show all products CUSTOMER
                    break;
                case "NCO": // new customer order CUSTOMER
                    break;
                case "APO": // add product to order CUSTOMER
                    break;
                case "RPO": // remove product from order CUSTOMER
                    break;
                case "LOP": // list products in current order CUSTOMER
                    break;
                case "FO": // finalize the order so nothing can be added CUSTOMER
                    break;
                case "CO": // cancel order CUSTOMER
                    break;
                case "OR": // order report CUSTOMER
                    break;
                case "T": // terminate client
                    break;
                default:
                    response = "ERR| Unknown Command.";
            }
        }
        catch (IllegalArgumentException iae){
            response = "ERR|" + iae.getMessage();
        }
        this.sendRequest(response);
    }

    private void closeClientConnection(){
        //Try to close all input, output and socket.
        try {this.input.close();} catch (IOException|NullPointerException e){e.printStackTrace();}
        try {this.output.close();} catch (NullPointerException e){e.printStackTrace();}
        try {this.clientConnection.close();} catch (IOException|NullPointerException e){e.printStackTrace();}
    }

    public void sendRequest(String request) { //send message and returns the server response.
        System.out.println("SERVER >> "+ request);
        this.output.println(request);
    }

    @Override
    public void run() {
        displayMessage("Getting Data Streams.");
        try {
            getOutputStream(clientConnection);
            getInputStream(clientConnection);

            sendRequest("Connected to Library Java Server");

            while (this.keepRunningClient)
                processClientRequest();


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            closeClientConnection();
        }
    }
}
