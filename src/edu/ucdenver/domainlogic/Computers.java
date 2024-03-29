package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Computers extends Electronics{
    private String technicalSpecifications;

    public Computers(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
              int serialNumber, int warrantyPeriod, String technicalSpecifications){
        super(productID,productName,brandName,productDescription,dateofIncorporation,serialNumber,warrantyPeriod);
        this.technicalSpecifications = technicalSpecifications;
        this.addCategory("COMPUTERS", "126", "COMPUTER Products");

    }

    @Override
    public String getProductDetails(){
        return String.format("---Computers%s%nTech Specs: %s", super.getProductDetails(), this.getTechnicalSpecifications());
    }

    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }
}
