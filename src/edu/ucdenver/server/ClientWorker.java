package edu.ucdenver.server;

import edu.ucdenver.domainlogic.*;
import edu.ucdenver.store.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDate;
public class ClientWorker implements Runnable {
    private final Socket clientConnection;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;
    private final int id;

    // FIXME : these are probably in the wrong spot
    private Customer custUser = new Customer("default", "email", "password");
    private Store store = new Store();


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
                    response = String.format("OK|Successfully Added new user: %s",clientMessage);
                    User temp = new User(arguments[1],arguments[2],arguments[3], true);
                    User.users.add(temp);
                    break;
                case "LAU": // login a user
                    break;
                case "ANP": // add new product ADMIN
                    /*
                    arg[1] = productID
                    arg[2] = productName
                    arg[3] = brandName
                    arg[4] = productDescription
                    arg[5] = dateProductAdded
                    arg[6] = authorname
                    arg[7] = publicationdate
                    arg[8] = numOfPages
                    arg[9] = serialNumber
                    arg[10] = warrantyPeriod
                    arg[11] = computerspecs
                    arg[12] = cellphoneIMEI
                    arg[13] = areaofuse
                    arg[14] = os
                     */
                    Product placeholder = null;
                    LocalDate dateProductAdded = null;
                    LocalDate publicationDate = null;
                    if(!arguments[5].equals("NONE")) {
                        String[] dateFields = arguments[5].split("-");
                        dateProductAdded = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]), Integer.parseInt(dateFields[2]));
                    }
                    if(!arguments[7].equals("NONE")) {
                        String[] dateFields = arguments[7].split("-");
                        publicationDate = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]), Integer.parseInt(dateFields[2]));
                    }

                    if (arguments[6].equals("NONE") && arguments[9].equals("NONE")){
                        placeholder = new HomeProducts(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,arguments[13]);
                        User.products.add(placeholder);
                    }
                    if (arguments[9].equals("NONE") && !arguments[6].equals("NONE")){
                        placeholder = new Books(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded, arguments[6],publicationDate,Integer.parseInt(arguments[8]));
                        User.products.add(placeholder);
                    }
                    if (!arguments[9].equals("NONE") && arguments[6].equals("NONE")
                     && arguments[12].equals("NONE")){
                        placeholder = new Computers(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[11]);
                        User.products.add(placeholder);
                    }
                    if (!arguments[9].equals("NONE") && arguments[6].equals("NONE") && !arguments[12].equals("NONE")){
                        placeholder = new CellPhones(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[12],arguments[14]);
                        User.products.add(placeholder);
                    }
                    response = String.format("OK|Successfully added product: %s", placeholder.getProductName());
                    break;
                case "RP": // remove product ADMIN
                    for (Product p : User.products){
                        if(arguments[1].equalsIgnoreCase(p.getProductID())){
                            User.products.remove(p);
                            response = String.format("OK|Successfully deleted product: %s",p.getProductName());
                        }
                        else
                            response = String.format("ERR|The product is not in the catalog.");
                    }
                    break;
                case "ACP": // add category to product ADMIN
                    break;
                case "DCP": // delete category to product ADMIN
                    break;
                case "ANC": // add new category ADMIN
                    Category temp1 = new Category(arguments[1],arguments[2],arguments[3]);
                    User.categories.add(temp1);
                    response = String.format("OK|Successfully added Category: %s", temp1.getCategoryName());
                    break;
                case "SDC": // set default category ADMIN
                    Category.setDefaultvalues(arguments[1],arguments[2],arguments[3]);
                    response = String.format("OK|Successfully set the default category to: %s", Category.defaultName);
                    break;
                case "DC": // delete category ADMIN
                    for (Category c : User.categories){
                        if(arguments[1].equalsIgnoreCase(c.getCategoryName())){
                            User.categories.remove(c);
                            response = String.format("OK|Successfully deleted category: %s", c.getCategoryName());
                        }
                        else
                            response = String.format("ERR|The category is not in the catalog.");
                    }
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
                    String theProd = arguments[1];
                    for(Product p : this.store.getProductList()){
                        if(p.getProductID().equalsIgnoreCase(theProd)){
                            this.custUser.getOrder().getOrderList().add(p);
                            response = String.format("OK|Successfully added %s to the order", p.getProductName());
                        }
                    }
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
                case "TEST":
                    response = "OK|Successfully connected to Java Catalog.";
                    break;
                case "AL":
                    for (User u : User.users){
                        if(u.getEmail().equalsIgnoreCase(arguments[1])){
                            if(u.getPassword().equalsIgnoreCase(arguments[2])){
                                response = "OK|Successfully logged into Admin Application.";
                            }
                            else{
                                response = "ERR|Incorrect password.";
                            }
                        }
                        else{
                            response = "ERR|Incorrect email.";
                        }
                    }
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

            sendRequest("Connected to Catalog Java Server");

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
