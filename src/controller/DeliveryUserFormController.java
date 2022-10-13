package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Delivery;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.DelievryTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DeliveryUserFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblDeliverys;
    public TableColumn colDeliveryId;
    public TableColumn colDriverId;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colStatus;
    public TableColumn colDeliveryPrice;
    public JFXComboBox<String>cmbOrderId;
    public JFXComboBox<String>cmbDriverId;
    public JFXTextField txtDeliveryId;
    public JFXDatePicker txtDate;
    public JFXTimePicker txtTme;
    public JFXComboBox<String>cmbStatus;
    public JFXTextField txtDeliveryPrice;
    public JFXTextField txtDelId;
    public JFXTextField txtDelPrice;
    public JFXComboBox<String>cmbDrId;
    public JFXComboBox<String>cmbOrId;
    public JFXComboBox<String>cmbDeliveryStatus;
    public JFXTextField txtDte;
    public JFXTextField txtTime;
    public JFXButton btnAddDelivery;
    public JFXButton btnUpdateDelivery;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern deliveryIdPattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Z\\d]{4,}$");
    Pattern deliveryPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    LinkedHashMap<JFXTextField, Pattern> map1 = new LinkedHashMap<>();
    Pattern delIdPattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Z\\d]{4,}$");
    Pattern delPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");

    public void initialize(){
        btnAddDelivery.setDisable(true);
        btnUpdateDelivery.setDisable(true);
        storeValidations();

        colDeliveryId.setCellValueFactory(new PropertyValueFactory("deliveryId"));
        colDriverId.setCellValueFactory(new PropertyValueFactory("driverId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colTime.setCellValueFactory(new PropertyValueFactory("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colDeliveryPrice.setCellValueFactory(new PropertyValueFactory("deliveryPrice"));

        cmbStatus.getItems().add("On Delivery");
        cmbStatus.getItems().add("Complete");
        cmbStatus.getItems().add("Cancel");

        cmbDeliveryStatus.getItems().add("On Delivery");
        cmbDeliveryStatus.getItems().add("Complete");
        cmbDeliveryStatus.getItems().add("Cancel");

        setDriversIds1();
        setDriverIds2();
        setOrderIds1();
        setOrderIds2();

        try {
            loadAllDelivery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setAutoIds();

        loadDateAndTime();
    }

    private void storeValidations() {
        btnAddDelivery.setDisable(true);
        btnUpdateDelivery.setDisable(true);
        map.put(txtDeliveryId, deliveryIdPattern);
        map.put(txtDeliveryPrice, deliveryPricePattern);
        map1.put(txtDelId, delIdPattern);
        map1.put(txtDelPrice, delPricePattern);


    }

    private void loadAllDelivery() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Delivery");
        ObservableList<Delivery> obList = FXCollections.observableArrayList();

        setAutoIds();

        while (result.next()){
            obList.add(
                    new Delivery(
                            result.getString("DelCode"),
                            result.getString("DR_id"),
                            result.getString("orderId"),
                            result.getString("date"),
                            result.getString("time"),
                            result.getString("status"),
                            result.getDouble("price")
                    )
            );
        }
        tblDeliverys.setItems(obList);
    }

    private void clearAllTexts() {
        txtDeliveryId.clear();
        cmbDriverId.setValue(null);
        cmbOrderId.setValue(null);
        cmbStatus.setValue(null);
        txtDeliveryPrice.clear();

    }
    private void clearAllTexts2() {
        txtDelId.clear();
        cmbDrId.setValue(null);
        cmbOrId.setValue(null);
        txtDte.clear();
        txtTime.clear();
        cmbDeliveryStatus.setValue(null);
        txtDelPrice.clear();

    }

    private void setOrderIds2() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    OrderCrudController.getOrderId()
            );
            cmbOrId.setItems(sIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setOrderIds1() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    OrderCrudController.getOrderId()
            );
            cmbOrderId.setItems(sIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDriverIds2() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    DriverCrudController.getDriverId()
            );
            cmbDrId.setItems(sIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDriversIds1() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableArrayList(
                    DriverCrudController.getDriverId()
            );
            cmbDriverId.setItems(sIdObList);
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

    public void generateDate(){
        this.txtDate.setPromptText(LocalDate.now().toString());
    }
    public void generateTime(){
        this.txtTme.setPromptText(LocalTime.now().toString());
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

    public void btnAddDeliveryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Delivery d = new Delivery(txtDeliveryId.getText(),cmbDriverId.getValue(),cmbOrderId.getValue(),String.valueOf(txtDate.getValue()),String.valueOf(txtTme.getValue()),cmbStatus.getValue(),Double.parseDouble(txtDeliveryPrice.getText()));

        try {
            if(CrudUtil.execute("INSERT INTO Delivery VALUES (?,?,?,?,?,?,?)",d.getDeliveryId(),d.getDriverId(),d.getOrderId(),d.getDate(),d.getTime(),d.getStatus(),d.getDeliveryPrice())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        clearAllTexts();
        loadAllDelivery();
        setAutoIds();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Delivery WHERE DelCode = ?",txtDelId.getText());
            if (result.next()) {

                cmbDrId.setValue(result.getString(2));
                cmbOrId.setValue(result.getString(3));
                txtDte.setText(result.getString(4));
                txtTime.setText(result.getString(5));
                cmbDeliveryStatus.setValue(result.getString(6));
                txtDelPrice.setText(result.getString(7));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result!..").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnUpdateDeliveryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Delivery d = new Delivery(txtDelId.getText(),cmbDrId.getValue(),cmbOrId.getValue(),txtDte.getText(),txtTime.getText(),cmbDeliveryStatus.getValue(),Double.parseDouble(txtDelPrice.getText()));


        try{
            boolean isUpdated = CrudUtil.execute("UPDATE Delivery SET DR_id = ?, orderId = ?, date = ?, time = ?, status = ?, price = ? WHERE DelCode = ?",d.getDriverId(),d.getOrderId(),d.getDate(),d.getTime(),d.getStatus(),d.getDeliveryPrice(),d.getDeliveryId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();

            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }


        }catch (SQLException | ClassNotFoundException e){

        }
        clearAllTexts2();
        loadAllDelivery();
        setAutoIds();
    }

    private void setAutoIds(){
        txtDeliveryId.setText(GenerateAutoId.autoId("SELECT DelCode FROM Delivery ORDER BY DelCode DESC LIMIT 1",1, 2,"DL01"));
    }

    public void textFieldOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(map, btnAddDelivery);
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

    public void textFieldsOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(map1,btnUpdateDelivery);
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

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException {
        String deliveryId = txtDeliveryId.getText();
        String driverId = cmbDriverId.getValue();
        String orderId = cmbOrderId.getValue();
        double deliveryPrice = Double.parseDouble(txtDeliveryPrice.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("deliveryId", deliveryId);
        paramMap.put("driverId", driverId);
        paramMap.put("orderId", orderId);
        paramMap.put("deliveryPrice", deliveryPrice);

        JasperReport compileReport= (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/views/report/DelBill.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,paramMap,new JREmptyDataSource(1));
        JasperViewer.viewReport(jasperPrint,false);


    }
}
