package edu.ucdenver.domainlogic;

import com.sun.xml.internal.bind.v2.TODO;
import edu.ucdenver.store.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Product implements Serializable {
    private String productID;
    private String productName;
    private String brandName;
    private String productDescription;
    private LocalDate dateofIncorporation;
    private String productCategory;
    //All products can have one or more catgories. The following array list allows us to store many cats for each product
    public ArrayList<Category> categories = new ArrayList<Category>();


    Product(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation){
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brandName = brandName;
        this.dateofIncorporation = dateofIncorporation;
        this.categories.add(new Category()); //default constructor sets item's category to 'home'
    }

    public String getProductDetails(){
        String deets = "~~~PRODUCT DETAILS~~~\n";
        deets += String.format("Name: %s%nID: %s%nCategory: %s%nBrand: %s%nDescription: %s%nInc: %s%n",
                this.getProductName(), this.getProductID(), this.getCategoryNames(), this.getBrandName(),
                this.getProductDescription(), this.getDateofIncorporation());
        return deets;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public LocalDate getDateofIncorporation() {
        return dateofIncorporation;
    }

    public void setDateofIncorporation(LocalDate dateofIncorporation) {
        this.dateofIncorporation = dateofIncorporation;
    }
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(String name, String id, String desc){
       this.categories.add(new Category(name, id, desc));
    }

    //this function returns the names of the categories the prod belongs to
    public String getCategoryNames(){

        String names= "";
        for(Category c : categories){
            names += String.format("%s, ", c.getCategoryName());
        }
        return names;
    }
    @Override
    public String toString(){
        return String.format("%s", this.getProductName());
    }
}

