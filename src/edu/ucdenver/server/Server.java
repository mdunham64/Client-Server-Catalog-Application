package edu.ucdenver.server;

import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.domainlogic.Product;
import edu.ucdenver.store.Admin;
import edu.ucdenver.store.User;
import edu.ucdenver.store.Store;

import java.io.*;
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

            User admin = new Admin("admin","admin@ucdenver.edu","");
            while(true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();


                    //if loading from file, load the admin user and populate the catalog
                    //else create default admin with email of admin@ucdenver.edu and pass blank
                    //attach object of Admin for saved information
                    ClientWorker cw = new ClientWorker(clientConnection, this.connectionCounter); // add parameters later

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

    public Store loadFromFile(){
        String filename = "./catalogData.ser";
        ObjectInputStream ois = null;
        Store theStore = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(filename));
            theStore = (Store) ois.readObject();
        }
        catch(Exception e){
            e.printStackTrace();
            theStore = new Store();
        }
        finally {
            if ( ois != null){
                try{ois.close();}
                catch(IOException ioe){ioe.printStackTrace();}
            }
        }
        return theStore;
    }

    public void saveToFile(){
        String filename = "./catalogData.ser";

        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            if(oos != null){
                try{
                    oos.close();
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }
}
