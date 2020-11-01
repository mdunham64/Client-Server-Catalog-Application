package edu.ucdenver.store;

import com.sun.tools.corba.se.idl.constExpr.Or;
import edu.ucdenver.domainlogic.*;

import javax.rmi.CORBA.PortableRemoteObjectDelegate;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Store implements Serializable {
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Order> finalizedOrders = new ArrayList<>();
    public Store(){

    }

    public void createNewAdmin(String email, String name, String pass) {
        //TODO : verify email to user list
        Admin newAdmin = new Admin(email, name, pass);
    }

    public void createNewCustomer(String username, String email, String password){
        //TODO : verify email to user list
        Customer newCustomer = new Customer(username, email, password);
    }

    public void loginUser(){
        ;
    }

    private boolean searchProduct(String prodName) {
        for (Product temp : productList) {
            if (temp.getProductName().toLowerCase().equals(prodName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> searchByTerm(String searchTerm){
        ArrayList<Product> temp = new ArrayList<>();
        for(Product p : this.productList){
            if(p.getProductName().toLowerCase().contains(searchTerm.toLowerCase())){
                temp.add(p);
                continue;
            }
            else if(p.getProductDescription().toLowerCase().contains(searchTerm.toLowerCase())){
                //checks to see if the productID matches one in the temp list, dont add
                temp.add(p);
                continue;
            }
        }
        if(temp.size() == 0){
            System.out.print("No Results"); // in the UI this should be an alert
        }
        return temp;
    }

    public void addHomeProduct(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String location) {
        if (!searchProduct(productName)) {
            Product newHomeProduct = new HomeProducts(productID, productName, brandName, productDescription, dateofIncorporation, location);
            productList.add(newHomeProduct);
        }
        else
            System.out.print("Home Product already exists");
    }
    public void addBook(String productID, String productName, String brandName, String productDescription,
                        LocalDate dateofIncorporation, String author, LocalDate pubDate, int numPages) {
        if (!searchProduct(productName)) {
            Product newBook = new Books(productID, productName, brandName, productDescription, dateofIncorporation, author, pubDate, numPages);
            productList.add(newBook);
        }
        else
            System.out.print("Book already exists");
    }

    public void addElectronic(String productID, String productName, String brandName, String productDescription,
                              LocalDate dateofIncorporation, int serialNumber, int warranty) {
        if (!searchProduct(productName)) {
            Product newEProduct = new Electronics(productID, productName, brandName, productDescription, dateofIncorporation, serialNumber, warranty);
            productList.add(newEProduct);
        }
        else
            System.out.print("Eletronic already exists");
    }

    public void addComputer(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, int serialNumber, int warrantyPeriod, String technicalSpecifications) {
        if (!searchProduct(productName)) {
            Product newComputer = new Computers(productID, productName, brandName, productDescription, dateofIncorporation,
                    serialNumber, warrantyPeriod, technicalSpecifications);
            productList.add(newComputer);
        }
        else
            System.out.print("Computer already exists");
    }

    public void addCellPhone(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
                             int serialNumber, int warrantyPeriod, String IMEI, String operatingSystem) {
        if (!searchProduct(productName)) {
            Product newCell = new CellPhones(productID, productName, brandName, productDescription, dateofIncorporation,
                    serialNumber, warrantyPeriod, IMEI, operatingSystem);
            productList.add(newCell);
        }
        else
            System.out.print("Cell Phone already exists");
    }

    public void addNewCategory(String categoryName, String categoryID, String categoryDescription) throws IllegalArgumentException {
        for (Category c : this.categories) {
            if (c.getCategoryName().equalsIgnoreCase(categoryName)) {
                throw new IllegalArgumentException("Category is already in catalog");
            }
        }
        this.categories.add(new Category(categoryName, categoryID, categoryDescription));
    }

    public void removeCategory(Category c) {
        for (Category c1 : this.categories) {
            if (c1.getCategoryName().equalsIgnoreCase(c.getCategoryName())) {
                this.categories.remove(c1);
            }
            else{
                System.out.println("Category Not Found");
            }
        }
    }

    public boolean searchCategory(String name){
        for (Category temp : categories) {
            if (temp.getCategoryName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }return false;
    }
    public void addCatToProduct(Product p, Category c){
        if(!searchCategory(c.getCategoryName())){
            p.addCategory(c.getCategoryName(), c.getCategoryID(), c.getCategoryDescription());
        }
        else{
            System.out.println("Category Already Exists");
        }
    }
    public void removeCatFromProduct(Product p, Category c){
        for(Category c1 : p.categories){
            if(c1.getCategoryName().equalsIgnoreCase(c.getCategoryName())){
                p.categories.remove(c1);
            }
        }
    }

    public ArrayList<String> browseCategory(String c){
        ArrayList<String> temp = new ArrayList<>();
        //loop thru user products looking for category choice.name
        for (Product p : this.productList) {
            for (int i = 0; i<p.categories.size(); i++){
                if (p.categories.get(i).getCategoryName().equalsIgnoreCase(c)) {
                    temp.add(p.getProductName());
                    break;
                }
            }
        }
        return temp;
    }

    public String getProdDetails(Product p){
        return p.getProductDetails();
    }

    public void createNewOrder(Customer c){
        c.clearOrder();
    }

    //~~CUSTOMER FUNCTIONS~~

    public void addProdToOrder(Customer custy, Product addThis){
        try{
            custy.getOrder().addProductToOrder(addThis);
        }catch(NullPointerException e){
            System.out.printf("Something went wrong adding this %s", addThis.getProductName());
        }
    }

    public void removeProductFromOrder(Customer custy, String removeThis){
        try {
            for (Product p : custy.getOrder().getOrderList()) {
                System.out.print("MAC TEST");
                if(custy.getOrder().searchForProduct(removeThis)){
                    custy.getOrder().getOrderList().remove(p);
                }
            }
        }
            catch(NullPointerException e){
                System.out.printf("Could not find this product in your list. %s", removeThis);
            }
    }

    public void finalizeOrder(Customer c, Order o){
        //set a unique order number
        int temp = 0;

        for(Order f : finalizedOrders){
            if(temp <= f.getOrderNumber()){
                temp = f.getOrderNumber()+1;
            }
        }
        o.setOrderNumber(temp);

        //set email
        o.setCustEmail(c.getEmail());
        //set status
        o.setStatus("Final");
        //set the date of the order
        o.setFinalizedDate(LocalDate.now());
        //add to finalizedOrders list
        this.finalizedOrders.add(o);
        c.setOrder(new Order(c.getUsername()));
        //for testing, delete later
    }

    //Getters & Setters
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<Admin> admins) {

        this.admins = admins;
    }

    public void addAdmin(Admin a){
        admins.add(a);
    }

    public void addCustomer(Customer c){
        customers.add(c);
    }

    public ArrayList<Order> getfinalizedOrders() {
        return finalizedOrders;
    }

    public void setfinalizedOrders(ArrayList<Order> orders) {
        this.finalizedOrders = finalizedOrders;
    }
    public ArrayList<Order> getFinalOrdersByEmail(String email){
        ArrayList<Order> temp = new ArrayList<>();
        for(Order o : finalizedOrders){
            if(o.getCustEmail().equalsIgnoreCase(email)){
                temp.add(o);
            }
        }
        return temp;
    }

    public boolean checkEmailList(String email){

        for(Admin a : admins){
            if(a.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        for(Customer c : customers){
            if(c.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }

    public void saveToFile(){
        String filename = "./university.ser";


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

    public static Store loadFromFile(){
        String filename = "./StoreFile.ser";
        ObjectInputStream ois = null;
        Store store = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(filename));
            store = (Store) ois.readObject();
        }
        catch(Exception e){
            e.printStackTrace();
            store = new Store();
        }
        finally {
            if ( ois != null){
                try{ois.close();}
                catch(IOException ioe){ioe.printStackTrace();}
            }
        }
        return store;
    }

}
