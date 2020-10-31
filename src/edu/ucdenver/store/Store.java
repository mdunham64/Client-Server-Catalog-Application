package edu.ucdenver.store;

import com.sun.tools.corba.se.idl.constExpr.Or;
import edu.ucdenver.domainlogic.*;

import javax.rmi.CORBA.PortableRemoteObjectDelegate;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Store implements Serializable {
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private static int orderNumber = 1234567;

    public Store(){
        //populate lists from files
        //the lines below populate these lists before we have tried to load from file.
        //FIXME
        this.categories.add(new Category("HOME", "000", "Description"));
        this.productList.add(new HomeProducts("000", "sample name", "test", "sample description",
                LocalDate.of(2020, 10, 30), "bathroom"));
        this.productList.add(new HomeProducts("111", "sample name1", "test1", "sample description1",
                LocalDate.of(2020, 10, 30), "bathroom1"));
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
        }return false;
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

    public ArrayList<Product> browseCategory(Category c){
        ArrayList<Product> temp = new ArrayList<>();

        //loop thru user products looking for category choice.name
        for (Product p : this.productList) {
            for (int i = 0; i<p.categories.size(); i++){
                if (p.categories.get(i).getCategoryName().equalsIgnoreCase(c.getCategoryName())) {
                    temp.add(p);
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

    public void removeProductFromOrder(Customer custy, Product removeThis){
        try {
            for (Product p : custy.getOrder().getOrderList()) {
                if(custy.getOrder().searchForProduct(removeThis)){
                    custy.getOrder().getOrderList().remove(removeThis);
                }
            }
        }
            catch(NullPointerException e){
                System.out.printf("Could not find this product in your list. %s", removeThis.getProductName());
            }
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
