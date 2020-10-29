package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class CellPhones extends Electronics{
    private String imei;
    private String operatingSystem;

    public CellPhones(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation,
               int serialNumber, int warrantyPeriod, String imei, String operatingSystem){
        super(productID,productName,brandName,productDescription,dateofIncorporation,serialNumber,warrantyPeriod);
        this.imei = imei;
        this.operatingSystem = operatingSystem;
    }
    @Override
    public String getProductDetails(){
        return String.format("---Cell Phones%s%nIMEI: %s%nOS: %s", super.getProductDetails(), this.getImei(),
                this.getOperatingSystem());
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
