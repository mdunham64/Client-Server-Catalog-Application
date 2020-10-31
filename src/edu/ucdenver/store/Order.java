package edu.ucdenver.store;

import edu.ucdenver.domainlogic.Product;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order {

    private int orderNumber;
    private String status;
    private LocalDate finalizedDate; //made when finalized of order
    private ArrayList<Product> orderList;
    private String custEmail;


    Order(String customerID){
        this.orderNumber = customerID.hashCode();//hash their id for unique order numbers
        this.status = "in process";
        orderList = new ArrayList<>();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getProductDetails(Product details){
        String temp = "";
        if (orderList.contains(details)){
            temp = details.getProductDetails();
        }
        return temp;
    }

    public void createNewOrder(){
        orderList = new ArrayList<>();
    }

    public String listOrderProducts(){
        String returnThis = "";
        for (Product p: orderList){
            returnThis.concat(p.getProductName()+"%n");
        }
        return returnThis;
    }

    public void addProductToOrder(Product addThis){
        try{
            orderList.add(addThis);
        }catch(NullPointerException e){
            System.out.printf("Something went wrong adding this %s", addThis.getProductName());
        }
    }

    public void removeProductFromOrder(Product removeThis){
        //FIXME : I dont think the compiler likes this try/catch. It works but when you run it, the console
        // blows up
        for (Product p: orderList){
            try{
                orderList.remove(removeThis);
            }catch(NullPointerException e){
                System.out.printf("Could not find this product in your list. %s", removeThis.getProductName());
            }
        }
    }

    public void finalizeOrder(){
        this.status = "finalized";
        this.finalizedDate = LocalDate.now();
    }

    public ArrayList<Product> getOrderList() {
        return orderList;
    }

    public boolean searchForProduct(Product p){
        for (Product temp : orderList) {
            if (temp.getProductName().toLowerCase().equals(p.getProductName().toLowerCase())) {
                return true;
            }
        }return false;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setFinalizedDate(LocalDate finalizedDate) {
        this.finalizedDate = finalizedDate;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
    return String.format("%d", this.getOrderNumber());
    }
}
