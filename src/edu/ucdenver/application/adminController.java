package edu.ucdenver.application;


import edu.ucdenver.domainlogic.Category;
import edu.ucdenver.server.Client;
import edu.ucdenver.store.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    public ChoiceBox<String> choiceBoxCategoryList;
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
    public ChoiceBox<String> choiceBoxCustomerList;
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
    public CheckBox txtNewUserStatus;
    public ChoiceBox<String> choiceBoxCategorytoRemove;
    public ListView<String> listCustomerOrders;
    public ListView<String> listFinalizedOdersbyDate;


    Client client;


    public adminController() {
        client = new Client();
        client.connect();
    }
    public void initialize(){
        //send to server for category names
        String icb = "ICBONE|";
        inputParser(icb);
        String initCustomerListChoiceBox = "CUSTLIST|";
        inputParser(initCustomerListChoiceBox);
    }

    public void inputParser(String cmd){
        Alert alert;
        if(client.isConnected()){
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]) {
                    //add cases here that need info from the client worker
                    case "ICBONE":
                        ArrayList<String> temp = new ArrayList<>();
                        for(int i = 1; i < respArgs.length; i++){
                            temp.add(respArgs[i]);
                        }
                        choiceBoxCategorytoRemove.setItems(FXCollections.observableArrayList(temp));
                        choiceBoxCategoryList.setItems(FXCollections.observableArrayList(temp));
                        break;
                    case "CUSTLIST":
                        ArrayList<String> temp2 = new ArrayList<>();
                        for(int i = 1; i<respArgs.length; i++){
                            temp2.add(respArgs[i]);
                        }
                        choiceBoxCustomerList.setItems(FXCollections.observableArrayList(temp2));
                        break;
                    case "GCOR":
                        ArrayList<String> temp3 = new ArrayList<>();
                        for(int i = 1; i<respArgs.length; i++){
                            temp3.add(respArgs[i]);
                        }
                        listCustomerOrders.getItems().clear();
                        listCustomerOrders.setItems(FXCollections.observableArrayList(temp3));
                        break;
                    case "DATE":
                        ArrayList<String> temp4 = new ArrayList<>();
                        for(int i = 1; i<respArgs.length; i++){
                            temp4.add(respArgs[i]);
                        }
                        listFinalizedOdersbyDate.getItems().clear();
                        listFinalizedOdersbyDate.setItems(FXCollections.observableArrayList(temp4));
                        break;
                    default:
                        System.out.print("No Case Found");
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
    public void AddNewUser(javafx.event.ActionEvent actionEvent) {
        String name = txtNewUserName.getText();
        String email = txtNewUserEmail.getText();
        String password = txtNewUserPassword.getText();
        String isAdmin = "F";
        if (txtNewUserStatus.isSelected()){
            isAdmin = "T";
        }
        String cmd = "CNU|" + name + "|" + email + "|" + password + "|" + isAdmin;

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
        String removeCatFromProd = "RCFP|";
        removeCatFromProd += txtIDToAddRemoveCategory.getText() + "|" + choiceBoxCategorytoRemove.getValue();
        showAlert(removeCatFromProd);

    }

    public void AddCategoryToProduct() {
        String addCatToProd = "ACTP|" + txtIDToAddRemoveCategory.getText() + "|" + choiceBoxCategoryList.getValue();
        showAlert(addCatToProd);

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
        String cmd = "GCOR|";
        cmd += choiceBoxCustomerList.getValue();
        inputParser(cmd);
    }

    public void GetFinalizedOrders() {
        String startStr = datePickerStartListOrders.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endStr = datePickerEndListOrders.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String cmd = "DATE|" + startStr + "|" + endStr;
        inputParser(cmd);
    }

    //TODO :   Not sure difference. One exits client one should
    //     :   save store file.
    public void TerminateServerAndSave() {
        String cmd = "T";
        showAlert(cmd);
    }

    public void Exit() {

    }

    public void login(ActionEvent actionEvent) {

    }
}



