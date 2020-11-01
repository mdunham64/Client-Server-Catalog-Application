package edu.ucdenver.domainlogic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Category implements Serializable {
    private String categoryName;
    private String categoryID;
    public String categoryDescription;
    public static String defaultName = "home";
    public static String defaultID = "12345";
    public static String defaultcategoryDescription = "NONE";
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
        this.categoryDescription = defaultcategoryDescription;
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
    public static void setDefaultvalues(String catname, String catid, String catDesc){
        defaultName = catname;
        defaultID = catid;
        defaultcategoryDescription = catDesc;
    }

    @Override
    public String toString(){
        return String.format("%s", this.getCategoryName());
    }
}
