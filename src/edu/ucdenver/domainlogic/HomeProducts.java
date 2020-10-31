package edu.ucdenver.domainlogic;

import edu.ucdenver.store.User;

import java.time.LocalDate;

public class HomeProducts extends Product{
    private String location;

    public HomeProducts(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String location){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.location = location;
        this.addCategory("HOME", "123", "Home Products");
    }

    @Override
    public String getProductDetails(){
        return String.format("--Home--%n%sLocation: %s", super.getProductDetails(), this.getLocation());
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
