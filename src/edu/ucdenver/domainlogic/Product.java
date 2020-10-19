package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public abstract class Product {
    private String productID;
    private String productName;
    private String brandName;
    private String productDescription;
    private LocalDate dateofIncorporation;

    Product(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation){
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.brandName = brandName;
        this.dateofIncorporation = dateofIncorporation;
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
}
