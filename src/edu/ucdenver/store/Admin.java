package edu.ucdenver.store;

import edu.ucdenver.domainlogic.*;


import java.time.LocalDate;
import java.util.ArrayList;

public class Admin extends User {

    //TODO : I need to keep track of valid emails/users
    private static ArrayList<String> userList = new ArrayList<>();
    private static ArrayList<Product> productList = new ArrayList<>();

    public Admin(String username, String email, String password, boolean bool) {
        super(username, email, password, bool);
    }


    //TODO  :   users have valid email,abc@domain.com
    //      :   it wants a valid domain so check "."
    public boolean isValidEmail(String s) {
        return true;
    }

    public void createNewUser(String email, String name, String pass) {

    }

    public void addNewCategory(String categoryName, String categoryID, String categoryDescription) throws IllegalArgumentException {
        for (Category c : getCategories()) {
            if (c.getCategoryName().equalsIgnoreCase(categoryName)) {
                throw new IllegalArgumentException("Category is already in catalog");
            }
        }
        getCategories().add(new Category(categoryName, categoryID, categoryDescription));
    }

    //removes category from product based on the name of the category.
    public void removeCategory(Product prod, String catName) {
        for (Category c : getCategories()) {
            if (c.getCategoryName().equalsIgnoreCase(catName)) {
                prod.getCategories().remove(c);
            }
        }
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
                              LocalDate dateofIncorporation, int serialNumber, LocalDate warranty) {
        if (!searchProduct(productName)) {
            Product newEProduct = new Electronics(productID, productName, brandName, productDescription, dateofIncorporation, serialNumber, warranty);
            productList.add(newEProduct);
        }
        else
            System.out.print("Eletronic already exists");
    }

    public void addComputer(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, int serialNumber, LocalDate warrantyPeriod, String technicalSpecifications) {
        if (!searchProduct(productName)) {
            Product newComputer = new Computers(productID, productName, brandName, productDescription, dateofIncorporation,
                    serialNumber, warrantyPeriod, technicalSpecifications);
            productList.add(newComputer);
        }
        else
            System.out.print("Computer already exists");
    }


    public void addCellPhone(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
                             int serialNumber, LocalDate warrantyPeriod, String IMEI, String operatingSystem) {
        if (!searchProduct(productName)) {
            Product newCell = new CellPhones(productID, productName, brandName, productDescription, dateofIncorporation,
                    serialNumber, warrantyPeriod, IMEI, operatingSystem);
            productList.add(newCell);
        }
        else
            System.out.print("Cell Phone already exists");
    }
    public void printProductList(){
        for (Product p : returnAdminProduct()){
            System.out.print(p.getProductDetails());
        }
    }

    public ArrayList<Product> returnAdminProduct(){
        return productList;
    }
}
