package edu.ucdenver.server;

import edu.ucdenver.domainlogic.*;
import edu.ucdenver.store.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClientWorker implements Runnable {
    private final Socket clientConnection;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;
    private final int id;

    private Customer custUser = new Customer("default", "email", "password");
    private Store store;

    public ClientWorker(Socket connection, Store store, int id){
        this.clientConnection = connection;
        this.keepRunningClient = true;
        this.id = id;
        this.store = store;
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
        String custEmail = "aaa";

        try {
            switch (arguments[0]) { //arguments[0] must be the command
                case "CNU": // create new user

                    response = "ERR|Did not add user.";

                    if(store.checkEmailList(arguments[2])) {
                        response = String.format("ERR|Email already exists");
                    }
                    else{
                        if(arguments[4].equals("T")){
                            Admin temp = new Admin(arguments[1],arguments[2],arguments[3]);
                            store.addAdmin(temp);
                        }
                        else {
                            Customer temp = new Customer(arguments[1], arguments[2], arguments[3]);
                            store.addCustomer(temp);
                        }
                        response = String.format("OK|Successfully Added new user: %s",clientMessage);
                    }
                    break;
                case "TEST": // connects a user to the server
                    response = "OK|Successfully connected to the Catalog.";

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
                        store.addHomeProduct(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,arguments[13]);
                    }
                    if (arguments[9].equals("NONE") && !arguments[6].equals("NONE")){
                        placeholder = new Books(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded, arguments[6],publicationDate,Integer.parseInt(arguments[8]));
                        store.addBook(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded, arguments[6],publicationDate,Integer.parseInt(arguments[8]));
                    }
                    if (!arguments[9].equals("NONE") && arguments[6].equals("NONE")
                     && arguments[12].equals("NONE")){
                        placeholder = new Computers(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[11]);
                        store.addComputer(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[11]);
                    }
                    if (!arguments[9].equals("NONE") && arguments[6].equals("NONE") && !arguments[12].equals("NONE")){
                        placeholder = new CellPhones(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[12],arguments[14]);
                        store.addCellPhone(arguments[1],arguments[2],arguments[3],arguments[4],dateProductAdded,Integer.parseInt(arguments[9]),Integer.parseInt(arguments[10]),arguments[12],arguments[14]);
                    }
                    response = String.format("OK|Successfully added product: %s", placeholder.getProductName());
                    break;
                case "RP": // remove product ADMIN
                    for (Product p : store.getProductList()){
                        if(arguments[1].equalsIgnoreCase(p.getProductID())){
                            store.getProductList().remove(p);
                            response = String.format("OK|Successfully deleted product: %s",p.getProductName());
                        }
                        else
                            response = "ERR|The product is not in the catalog.";
                    }
                    break;
                case "CUSTLIST":
                    response = "CUSTLIST";
                    for(Order ord : this.store.getfinalizedOrders()){
                        response += "|" + ord.getCustEmail();
                    }
                    break;
                case "ANC": // add new category ADMIN
                    Category temp = new Category(arguments[1],arguments[2],arguments[3]);
                    store.addNewCategory(arguments[1],arguments[2],arguments[3]);
                    response = String.format("OK|Successfully added Category: %s", temp.getCategoryName());
                    break;
                case "SDC": // set default category ADMIN
                    Category.setDefaultvalues(arguments[1],arguments[2],arguments[3]);
                    response = String.format("OK|Successfully set the default category to: %s", Category.defaultName);
                    break;
                case "DC": // delete category ADMIN
                    for (Category c : store.getCategories()){
                        if(arguments[1].equalsIgnoreCase(c.getCategoryName())){
                            store.removeCategory(c);
                            response = String.format("OK|Successfully deleted category: %s", c.getCategoryName());
                        }
                        else
                            response = String.format("ERR|The category is not in the catalog.");
                    }
                    break;
                case "APO": // add product to order CUSTOMER
                case "SAPO":
                    response = "OK|";
                    String theProd = arguments[1];
                    for(Product p : this.store.getProductList()){
                        if(p.getProductName().equalsIgnoreCase(theProd)){
                            this.custUser.getOrder().getOrderList().add(p);
                        }
                    }
                    break;
                case "BSP":
                    response = "BSP";
                    String searchTerm = arguments[1];
                    this.store.searchByTerm(searchTerm);
                    for(Product p : this.store.searchByTerm(searchTerm)){
                        response += "|" + p.getProductName();
                    }
                    break;
                case "ORD":
                    response = "ORD";
                    for(Product p : this.custUser.getOrder().getOrderList()){
                        response += "|" + p.getProductName();
                    }
                    break;
                case "RPO": // remove product from order CUSTOMER
                    response = "OK|";
                    for(Product p : this.custUser.getOrder().getOrderList()){
                        if(p.getProductName().equalsIgnoreCase(arguments[1])){
                            this.custUser.getOrder().getOrderList().remove(p);
                            break;
                        }
                    }
                    break;
                case "FINAL":
                    this.store.finalizeOrder(custUser, custUser.getOrder());
                    response = "OK|";
                    for(Order o : this.store.getfinalizedOrders()){
                        System.out.println(o);
                    }
                    break;
                case "RCFP":
                    // FIXME : display error if removeCatFromProd doesnt run successfully
                    String prodID = arguments[1];
                    String catergory = arguments[2];

                    this.store.removeCatFromProduct(prodID, catergory);
                    System.out.println(String.format("Categories in Product %s are now: ", prodID));
                    for(Product p : this.store.getProductList()){
                        if(p.getProductID().equalsIgnoreCase(prodID)){
                            System.out.println(p.getCategories());
                        }
                    }
                    response = "OK|Successfully removed";
                    break;
                case "ACTP":
                    String prodID2 = arguments[1];
                    String catToAdd = arguments[2];
                    this.store.addCatToProduct(prodID2, catToAdd);
                    for(Product p : this.store.getProductList()){
                        if(p.getProductID().equalsIgnoreCase(prodID2)){
                            System.out.println(p.getCategories());
                        }
                    }
                    break;
                case "ICBONE":
                    response = "ICBONE";
                    for(Category c : this.store.getCategories()){
                        response += "|" + c.getCategoryName();
                    }
                    break;
                case "T": // terminate client, will save store file
                    saveToFile();
                    response = "OK|Successfully saved the catalog.";
                    closeClientConnection();
                    break;
                case "STORE":
                    break;
                case "AL":
                    for (User u : this.store.getAdmins()){
                        if(u.getEmail().equalsIgnoreCase(arguments[1])){
                            if(u.getPassword().equalsIgnoreCase(arguments[2])){
                                response = "OK|Successfully logged into Admin Application. Please close this window to proceed.";
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
                case "USERLOGIN":
                    System.out.println(arguments[1]);

                    for (User u : this.store.getCustomers()){
                        if(u.getEmail().equalsIgnoreCase(arguments[1])){
                            if(u.getPassword().equalsIgnoreCase(arguments[2])){
                                this.custUser = new Customer("default", arguments[1], "password");
                                response = "OK|Successfully logged into Customer Application. Please close this window to proceed.";
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
                case "INITEMAIL":
                    response = "INITEMAIL|" + this.custUser.getEmail();
                    break;
                case "SAVE":
                    response = "OK|Saved Catalog to Catalog.ser.";
                    this.store.saveToFile();
                    break;
                case "LCC":
                    response = "LCC|" + custUser.getUsername()+"|"+custUser.getEmail()+"|"+custUser.getPassword();
                    break;
                case "ICB":
                    response = "ICB";
                    for(Category c : this.store.getCategories()){
                        response += "|"+c.getCategoryName();
                    }
                    break;
                case "GCOR":
                    response = "GCOR";
                    String searchTerm1 = arguments[1];
                    for(Order o : this.store.getfinalizedOrders()){
                        if(o.getCustEmail().equalsIgnoreCase(searchTerm1)){
                            String str = Integer.toString(o.getOrderNumber());
                            response += "|" + str;
                        }
                    }
                    break;
                case "DATE":
                    response = "DATE";
                    String start = arguments[1];
                    String end = arguments[2];
                    LocalDate localStart = LocalDate.parse(start);
                    LocalDate localEnd = LocalDate.parse(end);

                    for(Order ord : this.store.getfinalizedOrders()){
                        if((ord.getFinalizedDate().isAfter(localStart) || ord.getFinalizedDate().isEqual(localStart)) &&
                                (ord.getFinalizedDate().isBefore(localEnd) || ord.getFinalizedDate().isEqual(localEnd))){
                            response += "|" + Integer.toString(ord.getOrderNumber());
                        }
                    }
                        break;
                case "BBC":
                    response = "BBC";
                    this.store.browseCategory(arguments[1]);
                    for(String s : this.store.browseCategory(arguments[1])){
                        response += "|" + s;
                    }
                    break;
                case "UMO":
                    response = "UMO";
                    ArrayList<Order> iter = store.getFinalOrdersByEmail(custUser.getEmail());
                    for(Order ord : iter){
                        response += "|"+ Integer.toString(ord.getOrderNumber());
                    }
                    break;
                case "COO":
                    response = "OK|";
                    this.custUser.getOrder().getOrderList().clear();
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

    public void saveToFile(){
        String filename = "./StoreFile.ser";

        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this.store);
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
