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
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.CustomerTM;
import views.tm.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierUserFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblSuppliers;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colSupplierAddress;
    public TableColumn colSupplierNic;
    public TableColumn colSupplierPhone;
    public TableColumn colButton;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtSupplierNic;
    public JFXTextField txtSupplierPhone;
    public JFXTextField txtSupId;
    public JFXTextField txtSupName;
    public JFXTextField txtSupAddress;
    public JFXTextField txtSupNic;
    public JFXTextField txtSupPhone;
    public JFXButton btnUpdateSupplier;
    public JFXButton btnAddSupplier;

    LinkedHashMap<JFXTextField, Pattern> sup = new LinkedHashMap<>();
    Pattern supplierIdPattern = Pattern.compile("^(S)[0-9]{3}$");
    Pattern supplierNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern supplierAddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern supplierNicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern supplierPhonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");
    LinkedHashMap<JFXTextField, Pattern> sup1 = new LinkedHashMap<>();
    Pattern supIdPattern = Pattern.compile("^(S)[0-9]{3}$");
    Pattern supNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern supAddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern supNicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern supPhonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");

    public void initialize(){
        btnAddSupplier.setDisable(true);
        btnUpdateSupplier.setDisable(true);
        storeValidations();
        colSupplierId.setCellValueFactory(new PropertyValueFactory("SupplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory("SupplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory("SupplierAddress"));
        colSupplierNic.setCellValueFactory(new PropertyValueFactory("SupplierNic"));
        colSupplierPhone.setCellValueFactory(new PropertyValueFactory("SupplierPhone"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

        loadDateAndTime();

        try {
            loadAllSupplier();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setAutoIds();

    }

    private void storeValidations() {
        btnAddSupplier.setDisable(true);
        btnUpdateSupplier.setDisable(true);
        sup.put(txtSupplierId, supplierIdPattern);
        sup.put(txtSupplierName, supplierNamePattern);
        sup.put(txtSupplierAddress, supplierAddressPattern);
        sup.put(txtSupplierNic, supplierNicPattern);
        sup.put(txtSupplierPhone, supplierPhonePattern );
        sup1.put(txtSupId, supIdPattern);
        sup1.put(txtSupName, supNamePattern);
        sup1.put(txtSupAddress, supAddressPattern);
        sup1.put(txtSupNic, supNicPattern);
        sup1.put(txtSupPhone, supPhonePattern);
    }

    private void loadAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn=new Button("Delete");
            btn.setOnAction(event -> {
                SupplierTM selectedItem= (SupplierTM) tblSuppliers.getSelectionModel().getSelectedItem();
                txtSupplierId.setText(selectedItem.getSupplierId());


                deleteSupplier();

                try {
                    loadAllSupplier();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setAutoIds();
            });
            obList.add(
                    new SupplierTM(
                            result.getString("Sup_id"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("NIC"),
                            result.getString("phone_number"),
                            btn
                    )
            );
        }
        tblSuppliers.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierNic.clear();
        txtSupplierPhone.clear();
        txtSupplierId.requestFocus();

    }
    private void clearAllTexts2() {
        txtSupId.clear();
        txtSupName.clear();
        txtSupAddress.clear();
        txtSupNic.clear();
        txtSupPhone.clear();
        txtSupId.requestFocus();


    }

    private void deleteSupplier() {
        try{

            if(CrudUtil.execute("DELETE FROM Supplier WHERE Cus_id=?",txtSupplierId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").showAndWait();
                loadAllSupplier();


            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

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

    public void btnAddSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier s = new Supplier(txtSupplierId.getText(),txtSupplierName.getText(),txtSupplierAddress.getText(),txtSupplierNic.getText(),txtSupplierPhone.getText());

        try {
            if(CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?,?)",s.getSupplierId(),s.getSupplierName(),s.getSupplierAddress(),s.getSupplierNic(),s.getSupplierPhone())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").showAndWait();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        clearAllTexts();
        loadAllSupplier();
        setAutoIds();
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE Sup_id = ?",txtSupId.getText());
            if (result.next()) {
                txtSupName.setText(result.getString(2));
                txtSupAddress.setText(result.getString(3));
                txtSupNic.setText(result.getString(4));
                txtSupPhone.setText(result.getString(5));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result!..").showAndWait();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Supplier s = new Supplier(txtSupId.getText(),txtSupName.getText(),txtSupAddress.getText(),txtSupNic.getText(),txtSupPhone.getText());


        try{
            boolean isUpdated = CrudUtil.execute("UPDATE  Supplier SET name = ?, address = ?, NIC = ?, phone_number = ? WHERE Cus_id = ?",s.getSupplierName(),s.getSupplierAddress(),s.getSupplierNic(),s.getSupplierPhone(),s.getSupplierId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").showAndWait();

            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").showAndWait();
            }


        }catch (SQLException | ClassNotFoundException e){

        }
        clearAllTexts2();
        loadAllSupplier();
        setAutoIds();
    }
    private void setAutoIds(){
        txtSupplierId.setText(GenerateAutoId.autoId("SELECT sup_id FROM Supplier ORDER BY sup_id DESC LIMIT 1",1, 2,"S001"));
    }

    public void textFieldsOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(sup1,btnUpdateSupplier);
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

    public void textFieldOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(sup, btnAddSupplier);
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
}
