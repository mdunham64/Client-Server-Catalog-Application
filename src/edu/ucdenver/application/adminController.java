package edu.ucdenver.application;


import edu.ucdenver.server.Client;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class adminController {
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

    Client client;


    public adminController() {
        client = new Client();
        client.connect();
        Alert alert;
    }

    public void AddNewUser(javafx.event.ActionEvent actionEvent) {
        String name = txtNewUserName.getText();
        String email = txtNewUserEmail.getText();
        String password = txtNewUserPassword.getText();
        Alert alert;
        String cmd = "CNU|" + name + "|" + email + "|" + password;

        if (client.isConnected()) {
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

    public void AddNewProduct(javafx.event.ActionEvent actionEvent) {
        String productID = txtNewProductID.getText();
        String productName = txtNewProductName.getText();
        String brand = txtNewProductBrandName.getText();
        String productDesc = txtNewProductDescription.getText();
        LocalDate dateProdAdded = datePickerNewProductDateAdded.getValue();
        //ADD AREA OF USE ONE HERE TOO SOON
        String areaofuse = "";
        String authorName;
        if(txtNewAuthorName.getText().isEmpty())
            authorName = "NONE";
        else
            authorName = txtNewAuthorName.getText();
        LocalDate publicationDate;
        if(datePickerNewPublicationDate.getValue()==null)
            publicationDate = LocalDate.of(2020,1,1);
        else
            publicationDate = datePickerNewPublicationDate.getValue();
        String numOfPages;
        if(txtNewNumPages.getText().isEmpty())
            numOfPages = "NONE";
        else
            numOfPages = txtNewNumPages.getText();
        String serialNumber;
        if(txtNewSerialNum.getText().isEmpty())
            serialNumber = "NONE";
        else
            serialNumber = txtNewSerialNum.getText();
        String warrantyperiod;
        if(txtWarrantyPeriod.getText().isEmpty())
            warrantyperiod = "NONE";
        else
            warrantyperiod = txtWarrantyPeriod.getText();
        String computerspecs;
        if(txtNewComputerSpecs.getText().isEmpty())
            computerspecs = "NONE";
        else
            computerspecs = txtNewComputerSpecs.getText();
        String cellphoneIMEI;
        if(txtNewCellphoneIMEI.getText().isEmpty())
            cellphoneIMEI = "NONE";
        else
            cellphoneIMEI = txtNewCellphoneIMEI.getText();
        //ADD CHOICE BOX INFO FOR OS VERSION HERE
        String os = "";
        Alert alert;
        String cmd = "ANP|" + productID + "|" + productName + "|" + brand + "|" + productDesc + "|" + dateProdAdded + "|" + authorName + "|"
                + publicationDate + "|" + numOfPages + "|" + serialNumber + "|" + warrantyperiod + "|" + computerspecs + "|" + cellphoneIMEI
                + areaofuse + "|" + os;

        if (client.isConnected()) {
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

    public void DeleteProduct() {

    }

    public void RemoveCategoryFromProduct() {

    }

    public void AddCategoryToProduct() {

    }

    public void AddNewCategory() {

    }

    public void DeleteCategory() {

    }

    public void SetDefaultCategory() {

    }

    public void GetCustomerOrderReport() {

    }

    public void GetFinalizedOrders() {

    }

    public void TerminateServerAndSave() {

    }

    public void Exit() {

    }

}



