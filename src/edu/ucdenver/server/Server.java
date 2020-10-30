package edu.ucdenver.server;

import edu.ucdenver.domainlogic.Product;
import edu.ucdenver.store.Admin;
import edu.ucdenver.store.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final int port;
    private final int backlog;
    private int connectionCounter; // keep track of how many clients are connected.
    private ServerSocket serverSocket;
    private boolean keepRunningClient;


    public Server(int port, int backlog){
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        this.keepRunningClient = false;
    }

    public Server(){
        this(10001,10);
    }

    private Socket waitForClientConnection() throws IOException {
        System.out.println("Waiting for a connection...");
        Socket clientConnection = this.serverSocket.accept();
        this.connectionCounter++;
        System.out.printf("Connection #%d accepted from %s %n",this.connectionCounter, clientConnection.getInetAddress().getHostName());

        this.keepRunningClient = true;
        return clientConnection;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try{
            this.serverSocket = new ServerSocket(this.port,this.backlog);

            // ADD IN CODE FOR LOADING CATALOG FROM FILE HERE

            User admin = new Admin("admin","admin@ucdenver.edu","",true);
            while(true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();

                    //if loading from file, load the admin user and populate the catalog
                    //else create default admin with email of admin@ucdenver.edu and pass blank
                    //attach object of Admin for saved information
                    ClientWorker cw = new ClientWorker(clientConnection, admin, this.connectionCounter); // add parameters later

                    executorService.execute(cw);
                }
                catch(IOException ioe){
                    System.out.println("\n-------\nServer Terminated");
                    ioe.printStackTrace();
                }
            }

        }
        catch(IOException ioException){
            System.out.println("Cannot open the server...");
            executorService.shutdown();
            ioException.printStackTrace();
        }
    }
}
