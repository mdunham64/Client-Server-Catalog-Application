package edu.ucdenver.application;


import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.server.Client;
import edu.ucdenver.store.User;
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
    public TextField txtNewProductAreaofUse;
    public TextField txtNewAuthorName;
    public DatePicker datePickerNewPublicationDate;
    public TextField txtNewNumPages;
    public TextField txtNewSerialNum;
    public TextField txtWarrantyPeriod;
    public TextField txtNewComputerSpecs;
    public TextField txtNewCellphoneIMEI;
    public TextField txtCellphoneOS;
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
    public TextField txtDeleteCategory;
    public TextField txtSetDefaultCategory;
    public TextField txtAddnewcategoryid;
    public TextField txtaddnewcategorydescription;
    public TextField txtsetdefaultcatid;
    public TextField txtsetdefaultcatdesc;


    Client client;


    public adminController() {
        client = new Client();
        client.connect();
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
    public void AddNewUser(javafx.event.ActionEvent actionEvent) {
        String name = txtNewUserName.getText();
        String email = txtNewUserEmail.getText();
        String password = txtNewUserPassword.getText();
        String cmd = "CNU|" + name + "|" + email + "|" + password;

        showAlert(cmd);
    }

    public void AddNewProduct(javafx.event.ActionEvent actionEvent) {
        String productID = txtNewProductID.getText();
        String productName = txtNewProductName.getText();
        String brand = txtNewProductBrandName.getText();
        String productDesc = txtNewProductDescription.getText();
        LocalDate dateProdAdded = datePickerNewProductDateAdded.getValue();
        String areaofuse;
        if(txtNewProductAreaofUse.getText().isEmpty())
            areaofuse = "NONE";
        else
            areaofuse = txtNewProductAreaofUse.getText();
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
        String os;
        if(txtCellphoneOS.getText().isEmpty())
            os = "NONE";
        else
            os = txtCellphoneOS.getText();
        String cmd = "ANP|" + productID + "|" + productName + "|" + brand + "|" + productDesc + "|" + dateProdAdded + "|" + authorName + "|"
                + publicationDate + "|" + numOfPages + "|" + serialNumber + "|" + warrantyperiod + "|" + computerspecs + "|" + cellphoneIMEI
                + "|" + areaofuse + "|" + os;

        showAlert(cmd);
    }

    public void DeleteProduct() {
        String productID = txtDeleteProductID.getText();
        String cmd = "RP|" + productID;

        showAlert(cmd);
    }

    public void RemoveCategoryFromProduct() {

    }

    public void AddCategoryToProduct() {

    }

    public void AddNewCategory() {
        String anewcategory = txtAddNewCategory.getText();
        String anewcategoryid = txtAddnewcategoryid.getText();
        String anewcategorydescp = txtaddnewcategorydescription.getText();
        String cmd = "ANC|" + anewcategory + "|" + anewcategoryid + "|" + anewcategorydescp;

        showAlert(cmd);
    }

    public void DeleteCategory() {
        String deletecategory = txtDeleteCategory.getText();
        String cmd = "DC|" + deletecategory;

        showAlert(cmd);
    }

    public void SetDefaultCategory() {
        String setdefaultcategory = txtSetDefaultCategory.getText();
        String setdefaultcatid = txtsetdefaultcatid.getText();
        String setdefaultcatdescp = txtsetdefaultcatdesc.getText();
        String cmd = "SDC|" + setdefaultcategory + "|" + setdefaultcatid + "|" + setdefaultcatdescp;

        showAlert(cmd);
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



