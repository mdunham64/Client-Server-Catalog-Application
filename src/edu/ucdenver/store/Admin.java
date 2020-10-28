package edu.ucdenver.store;

import edu.ucdenver.domainlogic.*;


import java.time.LocalDate;
import java.util.ArrayList;

public class Admin extends User{

    //TODO : I need to keep track of valid emails/users
    private static ArrayList<String> userList = new ArrayList<>();

    public Admin(String username, String email, String password, boolean bool){
        super(username, email, password, bool);
    }


    //TODO  :   users have valid email,abc@domain.com
    //      :   it wants a valid domain so check "."
    public boolean isValidEmail(String s){
        return true;
    }

    public void createNewUser(String email, String name, String pass){

    }

    public void addNewCategory(String categoryName, String categoryID, String categoryDescription)throws IllegalArgumentException{
        for(Category c : getCategories()){
            if(c.getCategoryName().equalsIgnoreCase(categoryName)){
                throw new IllegalArgumentException("Category is already in catalog");
            }
        }
        getCategories().add(new Category(categoryName, categoryID, categoryDescription));
    }

    //removes category from product based on the name of the category.
    public void removeCategory(Product prod, String catName){
        for(Category c : getCategories()){
            if(c.getCategoryName().equalsIgnoreCase(catName)){
                prod.getCategories().remove(c);
            }
        }
    }
    //this is used to search for products within the products arraylist within Category
    private Product searchProduct(String prodName){
        for(Product tempObject : getProducts()){
            if (tempObject.getProductName().equals(prodName)){
                return tempObject;
            }
        }
        return null;
    }
    //FIXME : The methods below can only be used by Admin. Should they be private?
    public void addHomeProduct(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String location) throws IllegalArgumentException{
        Product tempProduct = this.searchProduct(productName);
        if(tempProduct==null){
            throw new IllegalArgumentException("Product is already in the catalog");
        }
        Product newHomeProduct = new HomeProducts(productID, productName, brandName, productDescription, dateofIncorporation, location);
        getProducts().add(newHomeProduct);
    }
    public void addBook(String productID, String productName, String brandName, String productDescription,
                        LocalDate dateofIncorporation, String author, LocalDate pubDate, int numPages)
                        throws IllegalArgumentException{
        Product tempProduct = this.searchProduct(productName);
        if(tempProduct==null){
            throw new IllegalArgumentException("Product is already in the catalog");
        }
        Product newBook = new Books(productID, productName, brandName, productDescription, dateofIncorporation, author, pubDate, numPages);
        getProducts().add(newBook);
    }

    public void addElectronic(String productID, String productName, String brandName, String productDescription,
                        LocalDate dateofIncorporation, int serialNumber, LocalDate warranty)
            throws IllegalArgumentException{
        Product tempProduct = this.searchProduct(productName);
        if(tempProduct==null){
            throw new IllegalArgumentException("Product is already in the catalog");
        }
        Product newEProduct = new Electronics(productID, productName, brandName, productDescription, dateofIncorporation, serialNumber, warranty);
        getProducts().add(newEProduct);
    }
    public void addComputer(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
                            int serialNumber, LocalDate warrantyPeriod, String technicalSpecifications)
            throws IllegalArgumentException{
        Product tempProduct = this.searchProduct(productName);
        if(tempProduct==null){
            throw new IllegalArgumentException("Product is already in the catalog");
        }
        Product newComputer = new Computers(productID, productName, brandName, productDescription, dateofIncorporation,
                serialNumber, warrantyPeriod, technicalSpecifications);
        getProducts().add(newComputer);
    }

    public void addCellPhone(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
                             int serialNumber, LocalDate warrantyPeriod, String IMEI, String operatingSystem)
            throws IllegalArgumentException {
        Product tempProduct = this.searchProduct(productName);
        if (tempProduct == null) {
            throw new IllegalArgumentException("Product is already in the catalog");
        }
        Product newCell = new CellPhones(productID, productName, brandName, productDescription, dateofIncorporation,
                serialNumber, warrantyPeriod, IMEI, operatingSystem);
        getProducts().add(newCell);
    }

}
