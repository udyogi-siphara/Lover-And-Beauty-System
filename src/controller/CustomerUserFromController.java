package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Customer;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.CustomerTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerUserFromController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusNic;
    public TableColumn colCusPhone;
    public TableColumn colButton;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNic;
    public JFXTextField txtCustomerPhone;
    public JFXTextField txtCusId;
    public JFXTextField txtCusName;
    public JFXTextField txtCusAddress;
    public JFXTextField txtCusNic;
    public JFXTextField txtCusPhone;
    public JFXButton btnAddCustomer;
    public JFXButton btnUpdateCustomer;

    LinkedHashMap<JFXTextField, Pattern> cus = new LinkedHashMap<>();
    Pattern customerIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern customerNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");
    LinkedHashMap<JFXTextField, Pattern> cus1 = new LinkedHashMap<>();
    Pattern cusIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern cusNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern cusAddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern cusNicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern cusPhonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");


    public void initialize(){
        btnAddCustomer.setDisable(true);
        btnUpdateCustomer.setDisable(true);
        storeValidations();

        loadDateAndTime();

        colCusId.setCellValueFactory(new PropertyValueFactory("CustomerId"));
        colCusName.setCellValueFactory(new PropertyValueFactory("CustomerName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory("CustomerAddress"));
        colCusNic.setCellValueFactory(new PropertyValueFactory("CustomerNic"));
        colCusPhone.setCellValueFactory(new PropertyValueFactory("CustomerPhone"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

        try {
            loadAllCustomer();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setAutoIds();
    }

    private void storeValidations() {
        btnAddCustomer.setDisable(true);
        btnUpdateCustomer.setDisable(true);
        cus.put(txtCustomerId, customerIdPattern);
        cus.put(txtCustomerName, customerNamePattern);
        cus.put(txtCustomerAddress, addressPattern);
        cus.put(txtCustomerNic, nicPattern);
        cus.put(txtCustomerPhone, phonePattern );
        cus1.put(txtCusId, cusIdPattern);
        cus1.put(txtCusName, cusNamePattern);
        cus1.put(txtCusAddress, cusAddressPattern);
        cus1.put(txtCusNic, cusNicPattern);
        cus1.put(txtCusPhone, cusPhonePattern);
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+":"+currentTime.getMinute()+":"+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("HomeUserForm");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUi("OrderUserForm");
    }

    public void btnDelievryOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DeliveryUserForm");
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SettingsUserForm");
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../views/LogoutUserForm.fxml","Log out");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerUserFrom");
    }

    private void setUi(String URI) throws IOException {
        Stage stage = (Stage) HomeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+URI+".fxml"))));
    }

    public void lordWidow(String location,String title) throws IOException {

        FXMLLoader loader =new FXMLLoader(getClass().getResource(location));
        Parent root=loader.load();
        Scene scene =new Scene(root);
        LogoutUserFormController controller =loader.getController();

        controller.getAllData(HomeContext);

        Stage stage =new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.show();


    }

    private void loadAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn=new Button("Delete");
            btn.setOnAction(event -> {
                CustomerTM selectedItem= (CustomerTM) tblCustomer.getSelectionModel().getSelectedItem();
                txtCusId.setText(selectedItem.getCustomerId());


                deleteCustomer();

                try {
                    loadAllCustomer();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setAutoIds();
            });
            obList.add(
                    new CustomerTM(
                            result.getString("Cus_id"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("NIC"),
                            result.getString("phone_number"),
                            btn
                    )
            );
        }
        tblCustomer.setItems(obList);
        clearAllTexts();
    }
    private void deleteCustomer() {
        try{

            if(CrudUtil.execute("DELETE FROM Customer WHERE Cus_id=?",txtCusId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").showAndWait();
                loadAllCustomer();


            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerNic.clear();
        txtCustomerPhone.clear();
        txtCustomerId.requestFocus();


    }
    private void clearAllTexts2() {
        txtCusId.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusNic.clear();
        txtCusPhone.clear();
        txtCusId.requestFocus();


    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c = new Customer(txtCustomerId.getText(),txtCustomerName.getText(),txtCustomerAddress.getText(),txtCustomerNic.getText(),txtCustomerPhone.getText());

        try {
            if(CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getCustomerId(),c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerPhone())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").showAndWait();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        clearAllTexts();
        loadAllCustomer();
        setAutoIds();
    }

    public void txtSearchCustomerOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE Cus_id = ?",txtCusId.getText());
            if (result.next()) {
                txtCusName.setText(result.getString(2));
                txtCusAddress.setText(result.getString(3));
                txtCusNic.setText(result.getString(4));
                txtCusPhone.setText(result.getString(5));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result!..").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnCustomerUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c = new Customer(txtCusId.getText(),txtCusName.getText(),txtCusAddress.getText(),txtCusNic.getText(),txtCusPhone.getText());


        try{
            boolean isUpdated = CrudUtil.execute("UPDATE  Customer SET name = ?, address = ?, NIC = ?, phone_number = ? WHERE Cus_id = ?",c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerPhone(),c.getCustomerId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").showAndWait();

            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").showAndWait();
            }


        }catch (SQLException | ClassNotFoundException e){

        }
        clearAllTexts2();
        loadAllCustomer();
        setAutoIds();
    }
    private void setAutoIds(){
        txtCustomerId.setText(GenerateAutoId.autoId("SELECT Cus_id FROM Customer ORDER BY Cus_id DESC LIMIT 1",1, 2,"C001"));
    }

    public void textFieldOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(cus, btnAddCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                System.out.println(errorText);
//                System.out.println(keyEvent.getCode());
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Notifications confirmation = NotificationBuilder.notifyMassage("CONFIRMATION", "All the text fields are filled successfully.");
                confirmation.showConfirm();
            }
        }
    }

    public void textFieldsOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(cus1,btnUpdateCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                System.out.println(errorText);
//                System.out.println(keyEvent.getCode());
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Notifications confirmation = NotificationBuilder.notifyMassage("CONFIRMATION", "Your Request Ok.");
                confirmation.showConfirm();
            }
        }
    }
}
