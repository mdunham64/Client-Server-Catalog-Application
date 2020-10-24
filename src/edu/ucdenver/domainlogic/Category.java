package edu.ucdenver.domainlogic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Category {
    private String categoryName;
    private String categoryID;
    private String categoryDescription;
    private String defaultName = "home";
    private String defaultID = "12345";
    private ArrayList<Product> products = new ArrayList<Product>();


    public Category(String categoryName, String categoryID, String categoryDescription){
        if(categoryName.length() < 30) {
            this.categoryName = categoryName;
        }
        else{
            this.categoryName = categoryName.substring(0, 30); // this trims the name to not exceed 30 chars
        }
        this.categoryID = categoryID;
        this.categoryDescription = categoryDescription;
    }
    public Category(){
        this.categoryName = defaultName;
        this.categoryID = defaultID;
        this.categoryDescription = "Description for this product is empty\n";
    }
    public String getProductNames(){
        String names= "";
        for(Product p : products){
            names += String.format("%s, ", p.getProductName());
        }
        return names;
    }


    //Gets and Sets
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        if(categoryName.length() < 30) {
            this.categoryName = categoryName;
        }
        else{
            this.categoryName = categoryName.substring(0, 30); // this trims the name to not exceed 30 chars
        }
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


}
