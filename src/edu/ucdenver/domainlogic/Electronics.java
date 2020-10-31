package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Electronics extends Product{
    private int serialNumber;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,int serialNumber, int warrantyPeriod){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.serialNumber = serialNumber;
        this.warrantyPeriod = warrantyPeriod;
        this.addCategory("ELECTRONICS", "127", "ELECTRONIC Products");

    }
    @Override
    public String getProductDetails(){
        return String.format("---Electronics---%n%sSerial #: %d%nWarranty Period: %s", super.getProductDetails(),
                this.getSerialNumber(), this.getWarrantyPeriod());
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
