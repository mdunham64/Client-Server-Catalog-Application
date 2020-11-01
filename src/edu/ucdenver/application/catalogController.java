package edu.ucdenver.application;


import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.domainlogic.HomeProducts;
import edu.ucdenver.domainlogic.Product;
import edu.ucdenver.server.Client;
import edu.ucdenver.server.ClientWorker;
import edu.ucdenver.store.Customer;
import edu.ucdenver.store.Order;
import edu.ucdenver.store.Store;
import edu.ucdenver.store.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.TouchEvent;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class catalogController{
    //Variables
    //Browse
    public ChoiceBox<Category> choiceBoxListOfCategoriesBrowse;
    public Button btnBrowseCategory;
    public ChoiceBox choiceBoxProductsFromCategory;
    public Button btnGetDetailsAddToOrderBrowse;
    public TextFlow txtProductDetailsSearch;
    //Search
    public Button btnSearchProducts;
    public Button btnGetDetailsAddToOrderSearch;
    public ChoiceBox ChoiceBoxSearchResults;
    public TextField txtSearchProducts;
    //Modify Order
    public ChoiceBox choiceBoxProductsInOrder;
    public Button btnRemoveProductFromOrder;
    public Button btnCancelOrder;
    public Button btnFinalizeOrder;
    public Button btnCreateNewOrder;
    //Exit
    public Button btnExit;
    public ListView<Product> listProductListForBrowse;
    public TextFlow txtProductDetails;
    public TextField txtCategoryToBrowse;
    public ChoiceBox<Category> choiceBoxListofCategory;
    public Button btnAddToOrderProductDetailsSearch;
    public ListView<Product> listProductSearchResults;
    public ListView<Product> listProductsInOrder;
    public Button btnUpdateOrderList;
    public Button btnUpdateMyOrders;
    public ListView<Order> listMyOrders;

    private Store theStore;

    //Once again not sure if line below is needed
    Client client;
    Customer customer;

    public catalogController(){
        client = new Client();
        theStore = new Store();
        customer = new Customer("default", "email", "password");

        client.connect();
        this.choiceBoxListofCategory = new ChoiceBox<Category>();
    }

    public void initialize(){
        this.choiceBoxListofCategory.setItems(FXCollections.observableArrayList(this.theStore.getCategories()));
    }

    public void showAlert(String cmd){
        Alert alert;
        if(client.isConnected()){
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]) {
                    case "OK":
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "Action complete: " + respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                    case "ERR":
                        alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR, "Server Response:" + e.getMessage(), ButtonType.OK);
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR, "Client is not connected", ButtonType.OK);
            alert.show();
        }
    }

    public void GetProductDetailsAddToOrderBrowse(){

    }

    public void btnSearchProducts(ActionEvent actionEvent){
        listProductSearchResults.getItems().clear();

    }

    public void GetProductDetailsAddToOrderSearch(){

    }

    public void CreateNewOrder(){

    }
    public void CancelOrder(){

    }

    public void RemoveProductFromOrder(){

    }

    public void FinalizeOrder(){

    }

    public void Exit(){

    }
    public void btnBrowseCategory(ActionEvent actionEvent) {
        listProductListForBrowse.getItems().clear(); //clears the list so with each new click, the list is refreshed
        Category categoryChoice = choiceBoxListofCategory.getValue();
        System.out.println();
        listProductListForBrowse.setItems(FXCollections.observableArrayList(this.theStore.browseCategory(categoryChoice)));
    }

    public void btnAddToOrder(ActionEvent actionEvent) {
        //FIXME
        Product listSelect = listProductListForBrowse.getSelectionModel().getSelectedItem();
        //this.theStore.addProdToOrder(this.customer, listSelect);
        String cmd = "APO|" + listSelect.getProductID();
        showAlert(cmd);

    }
    public void btnAddToOrderV2(ActionEvent actionEvent) {
        Product listSelect = listProductSearchResults.getSelectionModel().getSelectedItem();
        this.theStore.addProdToOrder(this.customer, listSelect);
        System.out.println(customer.getOrder().getOrderList());
    }

    public void clickSearchProducts(ActionEvent actionEvent) {
        listProductSearchResults.getItems().clear();
        String searchTerm = txtSearchProducts.getText();
        listProductSearchResults.setItems(FXCollections.observableArrayList(this.theStore.searchByTerm(searchTerm)));
    }

    public void UpdateOrderListButton(ActionEvent actionEvent) {
        listProductsInOrder.getItems().clear();
        listProductsInOrder.setItems(FXCollections.observableArrayList(this.customer.getOrder().getOrderList()));
    }

    public void removeButtonFromOrderButton(ActionEvent actionEvent) {
        Product listSelect = listProductsInOrder.getSelectionModel().getSelectedItem();
        this.customer.getOrder().removeProductFromOrder(listSelect);
        listProductsInOrder.getItems().clear();
        listProductsInOrder.setItems(FXCollections.observableArrayList(this.customer.getOrder().getOrderList()));
    }

    public void finalizeOrderButton(ActionEvent actionEvent) {
        Order currentOrder = this.customer.getOrder();
        this.theStore.finalizeOrder(this.customer, currentOrder);
        this.customer.getOrder().getOrderList().clear();

    }

    public void UpdateMyOrdersbutton(ActionEvent actionEvent) {
        listMyOrders.getItems().clear();
        listMyOrders.setItems(FXCollections.observableArrayList(theStore.getFinalOrdersByEmail(this.customer.getEmail())));

    }

    public void cancelOrderButton(ActionEvent actionEvent) {
    }
}
