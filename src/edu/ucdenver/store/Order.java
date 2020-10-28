package edu.ucdenver.store;

import java.time.LocalDate;

public class Order {

    private int orderNumber;
    private boolean status; //false for inprocess and true for finalized
    private LocalDate finalizedDate; //made when finalized of order


    Order(String customerID){
        this.orderNumber = customerID.hashCode();//hash their id for unique order numbers
        this.status = false;//initially not finalzed
    }


    public int getOrderNumber() {
        return orderNumber;
    }


    public void finalizeOrder(){
        this.status = true;
        this.finalizedDate = LocalDate.now();
    }



}
