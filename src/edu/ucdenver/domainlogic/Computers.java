package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Computers extends Electronics{
    private String technicalSpecifications;

    Computers(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,int serialNumber, LocalDate warrantyPeriod, String technicalSpecifications){
        super(productID,productName,brandName,productDescription,dateofIncorporation,serialNumber,warrantyPeriod);
        this.technicalSpecifications = technicalSpecifications;
    }

    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }
}
