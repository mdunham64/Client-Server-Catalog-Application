package edu.ucdenver.store;

public class Customer extends User{
    private Order order;


    public Customer(String username, String email, String password){
        super(username, email, password, false);
        this.order = new Order(username);
    }

    public void clearOrder(){
        order.getOrderList().clear();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
