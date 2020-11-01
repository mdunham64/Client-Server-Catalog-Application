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
    public ListView<String> listProductListForBrowse;
    public TextFlow txtProductDetails;
    public TextField txtCategoryToBrowse;
    public ChoiceBox<String> choiceBoxListofCategory;
    public Button btnAddToOrderProductDetailsSearch;
    public ListView<Product> listProductSearchResults;
    public ListView<Product> listProductsInOrder;
    public Button btnUpdateOrderList;
    public Button btnUpdateMyOrders;
    public ListView<Order> listMyOrders;

    private Store theStore;

    //Once again not sure if line below is needed
    Client client;
    Customer customer = new Customer("default", "email", "password");

    public catalogController(){
        client = new Client();
        client.connect();

        String cmd = "LCC|";
        inputParser(cmd);
        this.choiceBoxListofCategory = new ChoiceBox<String>();
    }

    public void initialize(){
        String cmd = "ICB";
        inputParser("ICB|");

    }
    public void inputParser(String cmd){
        Alert alert;
        if(client.isConnected()){
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]) {
                    case "LCC":
                        this.customer.setUsername(respArgs[1]);
                        this.customer.setEmail(respArgs[2]);
                        this.customer.setPassword(respArgs[3]);
                        break;
                    case "ICB":
                        ArrayList<String> temp = new ArrayList<>();
                        for (int i = 1; i<respArgs.length; i++){
                            temp.add(respArgs[i]);
                        }
                        choiceBoxListofCategory.setItems(FXCollections.observableArrayList(temp));
                        break;
                    case "BBC":
                        ArrayList<String> temp1 = new ArrayList<>();
                        for (int i = 1; i<respArgs.length; i++){
                            temp1.add(respArgs[i]);
                        }
                        listProductListForBrowse.getItems().clear();
                        listProductListForBrowse.setItems(FXCollections.observableArrayList(temp1));
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

    public void btnBrowseCategory(ActionEvent actionEvent) {
        String searchTerm = "BBC|" + choiceBoxListofCategory.getValue();
        inputParser(searchTerm);
    }

    public void btnAddToOrder(ActionEvent actionEvent) {
        /*//FIXME
        Product listSelect = listProductListForBrowse.getSelectionModel().getSelectedItem();
        //this.theStore.addProdToOrder(this.customer, listSelect); //adds to the local customer variable
        String cmd = "APO|" + listSelect.getProductID();
        showAlert(cmd);*/

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
        Order orderSelection = listMyOrders.getSelectionModel().getSelectedItem();
        for(Order o : this.theStore.getfinalizedOrders()){
            if(o.getOrderNumber() == orderSelection.getOrderNumber()){
                this.theStore.getfinalizedOrders().remove(o);
            }
        }
    }
    public void exitButton(ActionEvent actionEvent) {
        String cmd = "T";
        showAlert(cmd);
    }
}
