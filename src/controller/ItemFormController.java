package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.Employee;
import model.Item;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.EmployeeTM;
import views.tm.ItemTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblItems;
    public TableColumn colItemCode;
    public TableColumn colItemSupplierId;
    public TableColumn colItemName;
    public TableColumn colItemPrice;
    public TableColumn colItemQty;
    public TableColumn colButton;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXComboBox<String>cmbItemSupplierId;
    public TextField txtItemPrice;
    public TextField txtItemQty;
    public JFXTextField txtItmCode;
    public JFXTextField txtItmName;
    public JFXTextField txtItmPrice;
    public JFXTextField txtItmQty;
    public JFXComboBox<String>cmbItmSupplierId;
    public JFXButton btnAddItem;
    public JFXButton btnUpdateItem;

    LinkedHashMap<JFXTextField, Pattern> itm = new LinkedHashMap<>();
    Pattern ItemIdPattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern ItemNamePattern = Pattern.compile("^[A-z ]{4,50}$");
    LinkedHashMap<TextField, Pattern> itm1 = new LinkedHashMap<>();
    Pattern ItemPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    Pattern ItemQtyPattern = Pattern.compile("^[0-9]{2}$");
    LinkedHashMap<JFXTextField, Pattern> itm2 = new LinkedHashMap<>();
    Pattern ItmIdPattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern ItmNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern ItmPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    Pattern ItmQtyPattern = Pattern.compile("^[0-9]{2}$");

    public void initialize(){
        btnAddItem.setDisable(true);
        btnUpdateItem.setDisable(true);
        storeValidations();

        colItemCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        colItemSupplierId.setCellValueFactory(new PropertyValueFactory("itemSupplierId"));
        colItemName.setCellValueFactory(new PropertyValueFactory("itemName"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory("itemPrice"));
        colItemQty.setCellValueFactory(new PropertyValueFactory("itemQty"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

       setSupplierIds();
       setSupplierIds2();
       loadDateAndTime();

        try {
            loadAllItem();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setAutoIds();
    }

    private void storeValidations() {
        btnAddItem.setDisable(true);
        btnUpdateItem.setDisable(true);
        itm.put(txtItemCode, ItemIdPattern);
        itm.put(txtItemName, ItemNamePattern);
        itm1.put(txtItemPrice, ItemPricePattern);
        itm1.put(txtItemQty, ItemQtyPattern);
        itm2.put(txtItmCode, ItmIdPattern);
        itm2.put(txtItmName, ItmNamePattern);
        itm2.put(txtItmPrice, ItmPricePattern);
        itm2.put(txtItmQty, ItmQtyPattern);
    }

    private void loadAllItem() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn=new Button("Delete");
            btn.setOnAction(event -> {
                ItemTM selectedItem= (ItemTM) tblItems.getSelectionModel().getSelectedItem();
                txtItemCode.setText(selectedItem.getItemCode());


                deleteItem();

                try {
                    loadAllItem();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                setAutoIds();

            });
            obList.add(
                    new ItemTM(
                            result.getString("code"),
                            result.getString("supId"),
                            result.getString("name"),
                            result.getDouble("price"),
                            result.getInt("qty"),
                            btn
                    )
            );
        }
        tblItems.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {
        txtItemCode.clear();
        cmbItemSupplierId.setValue(null);
        txtItemName.clear();
        txtItemPrice.clear();
        txtItemQty.clear();

    }
    private void clearAllTexts2() {
        txtItmCode.clear();
        cmbItmSupplierId.setValue(null);
        txtItmName.clear();
        txtItmPrice.clear();
        txtItmQty.clear();

    }

    private void deleteItem() {
        try{

            if(CrudUtil.execute("DELETE FROM Item WHERE code=?",txtItemCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").showAndWait();
                loadAllItem();


            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void setSupplierIds(){
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

    private void setSupplierIds2(){
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    SupplierCrudController.getSupplierId()
            );
            cmbItmSupplierId.setItems(sIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        setUi("HomeForm");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUi("OrderForm");
    }

    public void btnDelievryOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DeliveryForm");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplierForm");
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ItemForm");
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeForm");
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SettingForm");
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../views/LogoutForm.fxml","Log out");
    }

    public void btnIncomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IncomeReportsForm");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    private void setUi(String URI) throws IOException {
        Stage stage = (Stage) HomeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+URI+".fxml"))));
    }

    public void lordWidow(String location,String title) throws IOException {

        FXMLLoader loader =new FXMLLoader(getClass().getResource(location));
        Parent root=loader.load();
        Scene scene =new Scene(root);
        LogoutFormController controller =loader.getController();

        controller.getAllData(HomeContext);

        Stage stage =new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.show();


    }


    public void btnAddItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        loadAllItem();
        setAutoIds();

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE code = ?",txtItmCode.getText());
            if (result.next()) {

                cmbItmSupplierId.setValue(result.getString(2));
                txtItmName.setText(result.getString(3));
                txtItmPrice.setText(result.getString(4));
                txtItmQty.setText(result.getString(5));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result!..").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnUpdateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i = new Item(txtItmCode.getText(),cmbItmSupplierId.getValue(),txtItmName.getText(),Double.parseDouble(txtItmPrice.getText()),Integer.parseInt(txtItmQty.getText()));


        try{
            boolean isUpdated = CrudUtil.execute("UPDATE Item SET supId = ?, name = ?, price = ?, qty = ? WHERE code = ?",i.getItemSupplierId(),i.getItemName(),i.getItemPrice(),i.getItemQty(),i.getItemCode());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();

            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }


        }catch (SQLException | ClassNotFoundException e){

        }
        clearAllTexts2();
        loadAllItem();
        setAutoIds();
    }
    private void setAutoIds(){
        txtItemCode.setText(GenerateAutoId.autoId("SELECT code FROM Item ORDER BY code DESC LIMIT 1",1, 2,"I001"));
    }


    public void textFiledOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(itm, btnAddItem);
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

    public void textNormalOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateTextField(itm1,btnAddItem);
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
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
        Object response = ValidationUtil.validateJFXTextField(itm2,btnUpdateItem);
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
