package edu.ucdenver.store;

public class Customer extends User{

    private Order order;

    public Customer(String username, String email, String password){
        super(username, email, password);
        this.order = new Order(username);
    }

}
