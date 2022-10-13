package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddItemFormController {


    public AnchorPane HomeContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtItemPrice;
    public JFXTextField txtItemQty;
    public JFXComboBox<String>cmbItemSupplierId;
    public JFXButton btnAddItem;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern itemCodePattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Z\\d]{4,}$");
    Pattern itemNamePattern = Pattern.compile("^[A-z ]{3,}$");
    Pattern itemPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    Pattern itemQtyPattern = Pattern.compile("^[0-9]{2}$");


    public void initialize(){
        storeValidations();
        setSupplierIds();

        setAutoIds();
    }

    private void storeValidations() {
        map.put(txtItemCode, itemCodePattern);
        map.put(txtItemName, itemNamePattern);
        map.put(txtItemPrice, itemPricePattern);
        map.put(txtItemQty, itemQtyPattern);

    }

    private void setSupplierIds() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    SupplierCrudController.getSupplierId()
            );
            cmbItemSupplierId.setItems(sIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getAllData(AnchorPane homeContext) {
        this.HomeContext = homeContext;
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        Item i = new Item(txtItemCode.getText(),txtItemName.getText(),cmbItemSupplierId.getValue(),Double.parseDouble(txtItemPrice.getText()),Integer.parseInt(txtItemQty.getText()));

        try {
            if(CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?)",i.getItemCode(),i.getItemName(),i.getItemSupplierId(),i.getItemPrice(),i.getItemQty())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearAllTexts();
        setAutoIds();

    }

    private void clearAllTexts() {
        txtItemCode.clear();
        cmbItemSupplierId.setValue(null);
        txtItemName.clear();
        txtItemPrice.clear();
        txtItemQty.clear();

    }

    private void setAutoIds(){
        txtItemCode.setText(GenerateAutoId.autoId("SELECT code FROM Item ORDER BY code DESC LIMIT 1",1, 2,"I001"));
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextFields(map, btnAddItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Notifications confirmation = NotificationBuilder.notifyMassage("CONFIRMATION", "All the text fields are filled successfully.");
                confirmation.showConfirm();
            }
        }
    }
}
