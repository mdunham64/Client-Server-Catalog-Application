package edu.ucdenver.application;


import edu.ucdenver.server.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextArea;

public class catalogController{
    //Variables
    //Browse
    public ChoiceBox choiceBoxListOfCategoriesBrowse;
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

    //Once again not sure if line below is needed
    Client client;

    public catalogController(){

    }

    public void BrowseCategory(){

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


}
