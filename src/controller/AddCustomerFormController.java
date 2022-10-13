package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCustomerFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtCustomerNic;
    public JFXTextField txtCustomerPhone;
    public AnchorPane HomeContext;
    public JFXButton btnAddCustomer;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern customerIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern customerNamePattern = Pattern.compile("^[A-z ]{3,}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9, ]{3,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");

    public void initialize(){
        storeValidations();
        setAutoIds();
    }
    private void storeValidations() {
        map.put(txtCustomerId, customerIdPattern);
        map.put(txtCustomerName, customerNamePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtCustomerNic, nicPattern);
        map.put(txtCustomerPhone, phonePattern );

    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        Customer c = new Customer(txtCustomerId.getText(),txtCustomerName.getText(),txtAddress.getText(),txtCustomerNic.getText(),txtCustomerPhone.getText());

        try {
            if(CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getCustomerId(),c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerPhone())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").showAndWait();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        clearAllTexts();
        setAutoIds();
    }

    private void setAutoIds() {
        txtCustomerId.setText(GenerateAutoId.autoId("SELECT Cus_id FROM Customer ORDER BY Cus_id DESC LIMIT 1",1, 2,"C001"));
    }

    private void clearAllTexts() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtAddress.clear();
        txtCustomerNic.clear();
        txtCustomerPhone.clear();
        txtCustomerId.requestFocus();
    }
    public void getAllData(AnchorPane homeContext) {
        this.HomeContext = homeContext;
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextFields(map, btnAddCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                System.out.println(errorText);
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Notifications confirmation = NotificationBuilder.notifyMassage("CONFIRMATION", "All the text fields are filled successfully.");
                confirmation.showConfirm();
            }
        }
    }
}
