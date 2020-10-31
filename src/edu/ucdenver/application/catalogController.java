package edu.ucdenver.application;


import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.domainlogic.HomeProducts;
import edu.ucdenver.domainlogic.Product;
import edu.ucdenver.server.Client;
import edu.ucdenver.server.ClientWorker;
import edu.ucdenver.store.Customer;
import edu.ucdenver.store.Store;
import edu.ucdenver.store.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.TouchEvent;
import javafx.scene.text.TextFlow;

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


    public void GetProductDetailsAddToOrderBrowse(){

    }

    public void SearchProducts(){

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
        this.theStore.addProdToOrder(this.customer, listSelect);
        System.out.println(customer.getOrder().getOrderList());
    }

}
