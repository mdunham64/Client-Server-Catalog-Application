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

}
