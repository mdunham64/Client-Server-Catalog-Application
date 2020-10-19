package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class HomeProducts extends Product{
    private String location;

    HomeProducts(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String location){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
