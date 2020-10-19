package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Electronics extends Product{
    private int serialNumber;
    private LocalDate warrantyPeriod;

    Electronics(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,int serialNumber, LocalDate warrantyPeriod){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.serialNumber = serialNumber;
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(LocalDate warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
