package edu.ucdenver.store;

import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.domainlogic.Product;
import edu.ucdenver.domainlogic.Electronics;
import edu.ucdenver.domainlogic.Books;
import edu.ucdenver.domainlogic.CellPhones;
import edu.ucdenver.domainlogic.Computers;


import java.net.Socket;
import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;
    private static ArrayList<Category> categories = new ArrayList<Category>();
    private static ArrayList<Product> products = new ArrayList<Product>();

    public User(String username, String email, String password){
        this. username = username;
        this.email = email;
        this.password = password;
    }

    /*public Socket login(String email, String password){
        //needs finishing
    }*/

    //Browsing & Searching methods
    //FIXME need to make the searches NOT case sensitive.
    public Product searchCatalog(CharSequence searchKey)throws IllegalArgumentException{
        for(Product p : getProducts()){
            if(p.getProductName().contains(searchKey)){
                return p;
            }
        }
        for(Product p : getProducts()){
            if(p.getProductDescription().contains(searchKey)){
                return p;
            }
        }
        throw new IllegalArgumentException("No product matches that search term.");
    }
    //FIXME : Needs to be case insensitive.
    //This is from #5 in use cases. It says to display a list of products in a category.
    // I return a list so that the UI can just call this method and display the string made in this
    // method - jb
    public String browseCategory(CharSequence searchKey)throws IllegalArgumentException{
        String prods;
        for(Category c : getCategories()){
            if(c.getCategoryName().contains(searchKey)){
                prods = c.getProductNames();
                return c.getProductNames();
            }
        }
        throw new IllegalArgumentException("No product matches that search term.");
    }

    //gets and sets
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }
    public static ArrayList<Product> getProducts() {
        return products;
    }
}
