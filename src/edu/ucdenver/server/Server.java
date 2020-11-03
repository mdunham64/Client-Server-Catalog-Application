package edu.ucdenver.server;

import edu.ucdenver.store.User;
import edu.ucdenver.store.Store;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final int port;
    private final int backlog;
    private int connectionCounter; // keep track of how many clients are connected.
    private ServerSocket serverSocket;
    private boolean keepRunningClient;
    private Store store;


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

            System.out.println("Enter 1 to load Catalog from file named: StoreFile.ser.");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if (option == 1){
                this.store = Store.loadFromFile();
                System.out.println("Successfully loaded Store from: StoreFile.ser.");
            }
            if(option == 2){
                this.store = new Store();
                System.out.println("Creating new store.");
            }
            for (User u : this.store.getAdmins())
                System.out.println(u.getEmail() + "|" + u.getPassword());

            while(true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();

                    ClientWorker cw = new ClientWorker(clientConnection,this.store, this.connectionCounter); // add parameters later

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
