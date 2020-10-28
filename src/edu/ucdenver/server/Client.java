package edu.ucdenver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int serverPort;
    private final String serverIp;

    private boolean isConnected;

    private Socket serverConnection;
    private PrintWriter output;
    private BufferedReader input;

    public Client(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
        this.isConnected = false;
    }


    public Client() {
        this("localhost", 24001); //default constructor, use the predefined ip/port we are using.
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void connect() {
        displayMessage("Attempting connection to Server.");
        try {
            this.serverConnection = new Socket(this.serverIp, this.serverPort);
            this.isConnected = true; // at this point, no exception then we're connected!
            this.output = this.getOutputStream();
            this.input = this.getInputStream();

            getServerInitialResponse();

        } catch (IOException e) { //If something went wrong...
            this.input = null;
            this.output = null;
            this.serverConnection = null;
            this.isConnected = false;

        }
    }

    private PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.serverConnection.getOutputStream(), true);
    }

    private BufferedReader getInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
    }

    public void disconnect() {
        displayMessage("\n Terminating Client Connection to Server.");
        try {
            this.input.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.serverConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getServerInitialResponse() throws IOException {
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
    }
    public String sendRequest(String request) throws IOException { //send message and returns the server response.
        this.output.println(request);
        displayMessage("CLIENT >> " + request);
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
        return srvResponse;
    }

    private void displayMessage(String message) {
        System.out.println(message);
    }
}
