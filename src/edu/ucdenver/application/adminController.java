package edu.ucdenver.application;


import edu.ucdenver.server.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextArea;

public class adminController{
    //Variables in Create New User Tab
    public TextField txtNewUserName;
    public TextField txtNewUserEmail;
    public TextField txtNewUserPassword;
    public Button btnAddNewUser;
    public TextFlow txtNewUserErrorMessage;
    //Manage Product Variables
    //Add New Product
    public TextField txtNewProductID;
    public TextField txtNewProductName;
    public TextField txtNewProductBrandName;
    public TextArea txtNewProductDescription;
    public DatePicker datePickerNewProductDateAdded;
    public ChoiceBox choiceBoxNewProductHomeArea;
    public TextField txtNewAuthorName;
    public DatePicker datePickerNewPublicationDate;
    public TextField txtNewNumPages;
    public TextField txtNewSerialNum;
    public TextField txtWarrantyPeriod;
    public TextField txtNewComputerSpecs;
    public TextField txtNewCellphoneIMEI;
    public ChoiceBox choiceBoxCellphoneIOS;
    public Button btnAddNewProduct;
    public TextFlow txtAddNewProductMessage;
    //Delete Product
    public TextField txtDeleteProductID;
    public Button btnDeleteProduct;
    public TextFlow txtDeleteProductMessage;
    //Add/Remove Category from Product
    public TextField txtIDToAddRemoveCategory;
    public Button btnRemoveCategoryFromProduct;
    public ChoiceBox choiceBoxCategoryList;
    public Button btnAddCategoryToProduct;
    public TextFlow txtAddRemoveCategoryFromProductMessage;
    //ManageProductCategories
    //Add
    public TextField txtAddNewCategory;
    public Button btnAddNewCategory;
    public TextFlow textFlowAddCategoryMessage;
    //Delete
    //Can below be consolidated? There are multiple category lists.  Both will need updating when categories are added and deleted
    public ChoiceBox choiceBoxCategoryListDelete;
    public TextFlow textFlowDeleteCategoryMessage;
    public Button btnDeleteCategory;
    //Set Default Category
    public ChoiceBox choiceBoxCategoryListDefault;
    public Button btnSetDefaultCategory;
    public TextFlow textFlowSetDefaultCategoryMessage;
    //Finalized Order Report Variables
    public ChoiceBox choiceBoxCustomerList;
    public Button btnGetCustomerOrderReport;
    public TextFlow textFlowCustomerOrderList;
    public DatePicker datePickerStartListOrders;
    public DatePicker datePickerEndListOrders;
    public TextFlow textFlowOrderListBetweenDates;
    public Button btnGetFinalizedOrders;
    //Save to File and Exit Options
    public Button btnAdminTerminateServerAndSave;
    public Button btnAdminExit;
    public TextFlow textFlowAdminSaveMessage;




    //Not sure if this line is needed
    Client client;


    adminController(){

    }


    public void AddNewUser(){

    }


    public void AddNewProduct(){

    }

    public void DeleteProduct(){

    }

    public void RemoveCategoryFromProduct(){

    }

    public void AddCategoryToProduct(){

    }

    public void AddNewCategory(){

    }

    public void DeleteCategory(){

    }

    public void SetDefaultCategory(){

    }

    public void GetCustomerOrderReport(){

    }

    public void GetFinalizedOrders(){

    }

    public void TerminateServerAndSave(){

    }

    public void Exit(){

    }






}

