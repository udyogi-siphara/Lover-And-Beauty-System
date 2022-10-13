package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import db.DBConnection;
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
import model.Item;
import model.Order;
import model.OrderDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.CustomerTM;
import views.tm.OrderDetailTM;
import views.tm.OrderTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class OrderFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItem;
    public TextField txtPrice;
    public TextField txtQty;
    public TableView tblOrders;
    public TableColumn colOrderId;
    public TableColumn colOrderDescription;
    public TableColumn colOrderAmount;
    public TableColumn colButton;
    public JFXTextField txtOrderId;
    public TextField txtOrderCustomerName;
    public TextField txtTotal;
    public TextField txtExchange;
    public TableColumn colCustomerId;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colOrderCustomerName;
    public TableColumn colDescription;
    public JFXTextField txtGetAmount;
    public JFXButton btnAddToCart;

    LinkedHashMap<JFXTextField, Pattern> odr = new LinkedHashMap<>();
    Pattern orderIdPattern = Pattern.compile("^(O)[0-9]{3}$");
    LinkedHashMap<TextField, Pattern> odr1 = new LinkedHashMap<>();
    Pattern orderQtyPattern = Pattern.compile("^[0-9]{1,}$");


    public void initialize() {
        btnAddToCart.setDisable(true);
        storeValidations();

        colOrderId.setCellValueFactory(new PropertyValueFactory("OrderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("orderCustomerId"));
        colOrderCustomerName.setCellValueFactory(new PropertyValueFactory("orderCustomerName"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("orderQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory("orderUnitPrice"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

        loadDateAndTime();
        setCustomerIds();
        setItemCode();


        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerDetails(newValue);
        });

        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemDetails(newValue);

            ResultSet result = null;
            try {
                result = CrudUtil.execute("SELECT * FROM item WHERE code=?", newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                if (result.next()) {
                    txtPrice.setText(String.valueOf(result.getDouble(4)));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        setAutoIds();


        txtGetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!txtGetAmount.getText().equals("")) {

                if (!txtTotal.getText().equals("") && !txtGetAmount.getText().equals("")) {
                    double tC = Double.parseDouble(txtTotal.getText());
                    int amount = Integer.parseInt(txtGetAmount.getText());

                    txtExchange.setText(String.valueOf(amount - tC));
                }


            }
        });
    }

    private void storeValidations() {
        btnAddToCart.setDisable(true);
        odr.put(txtOrderId, orderIdPattern);
        odr1.put(txtQty, orderQtyPattern);

    }

    private void setItemDetails(String selectedItemName) {
        try {
            Item i = ItemCrudController.getItem(selectedItemName);
            if (i != null) {
                txtPrice.setText(String.valueOf(i.getItemPrice()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtOrderCustomerName.setText(c.getCustomerName());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerId()
            );
            cmbCustomerId.setItems(cIdObList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemCode() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    ItemCrudController.getItemCode()
            );
            cmbItem.setItems(cIdObList);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
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
        lordWidow("../views/LogoutForm.fxml", "Log out");
    }

    public void btnIncomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IncomeReportsForm");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void btnAddCusIdOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow2("../views/AddCustomerForm.fxml", "Add Customer");
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow3("../views/AddItemForm.fxml", "Add Item");
    }

    private void setUi(String URI) throws IOException {
        Stage stage = (Stage) HomeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/" + URI + ".fxml"))));
    }

    public void lordWidow(String location, String title) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        LogoutFormController controller = loader.getController();

        controller.getAllData(HomeContext);

        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }

    public void lordWidow2(String location, String title) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AddCustomerFormController controller = loader.getController();

        controller.getAllData(HomeContext);

        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }

    public void lordWidow3(String location, String title) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AddItemFormController controller = loader.getController();

        controller.getAllData(HomeContext);

        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.show();


    }

    ObservableList<OrderTM> tmList = FXCollections.observableArrayList();


    public void btnAddToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalCost = unitPrice * qty;


        Button btn = new Button("Delete");
        OrderTM tm = new OrderTM(
                txtOrderId.getText(),
                cmbCustomerId.getValue(),
                txtOrderCustomerName.getText(),
                cmbItem.getValue(),
                qty,
                unitPrice,
                totalCost,
                btn
        );


        int isExists = isExists(tm);
        if (isExists == -1) {
            btn.setOnAction(e -> {
                tmList.remove(tm);
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!..").show();

                calculateTotal();
                //clearAllTexts();
            });
            tmList.add(tm);
            calculateTotal();

        } else {

            OrderTM temp = tmList.get(isExists);
            int nq = (temp.getOrderQty()) + Integer.parseInt(txtQty.getText());
            double pr = nq * Double.parseDouble(txtPrice.getText());

            OrderTM newTm = new OrderTM(temp.getOrderId(), temp.getOrderCustomerId(), temp.getOrderCustomerName(), temp.getDescription(), nq, pr, pr, temp.getBtn());
            tmList.remove(isExists);
            tmList.add(newTm);
        }
        tblOrders.setItems(tmList);
        calculateTotal();


    }


    private void clearAllTexts() {
        txtOrderId.clear();
        cmbCustomerId.setValue(null);
        txtOrderCustomerName.clear();
        cmbItem.setValue(null);
        txtQty.clear();
        txtPrice.clear();

    }

    private void setAutoIds() {
        txtOrderId.setText(GenerateAutoId.autoId("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1", 1, 2, "O001"));
    }


    private int isExists(OrderTM tm) {
        for (int i = 0; i < tmList.size(); i++) {
            if (tm.getDescription().equalsIgnoreCase(tmList.get(i).getDescription())) {
                return i;
            }
        }
        return -1;
    }

    private void calculateTotal() {
        double total = 0;
        for (OrderTM tm : tmList) {
            total += tm.getTotal();
        }
        txtTotal.setText(String.valueOf(total));

    }


    public void btnPayOnAction(ActionEvent actionEvent) throws SQLException {
        txtOrderId.setText(null);
        setAutoIds();
        String orderId = txtOrderId.getText();
        double total = Double.parseDouble(txtTotal.getText());
        double exchange = Double.parseDouble(txtExchange.getText());

        HashMap paramMap = new HashMap();

        paramMap.put("orderId", orderId);
        paramMap.put("amount", total);
        paramMap.put("bal", exchange);


        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/report/OrdersBill.jasper"));
            ObservableList<OrderTM> tableRecords = tblOrders.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JRBeanArrayDataSource(tblOrders.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
        clearAllTexts();
        setAutoIds();

    }

    public void textNormalFieldOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateTextField(odr1, btnAddToCart);
        if (keyEvent.getCode() == KeyCode.ENTER) {
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

    public void textFiledOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(odr, btnAddToCart);
        if (keyEvent.getCode() == KeyCode.ENTER) {
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

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ParseException {
        ArrayList<OrderDetail> items = new ArrayList<>();
        for (OrderTM temp : tmList) {
            items.add(new OrderDetail(txtOrderId.getText(),temp.getDescription(),Double.valueOf(temp.getTotal())));
        }

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());


        Order or = new Order(txtOrderId.getText(),cmbCustomerId.getValue(),txtOrderCustomerName.getText(),cmbItem.getValue(),Integer.parseInt(txtQty.getText()),Double.parseDouble(txtPrice.getText()),date,items);
        if (placeOrder(or)){
            new Alert(Alert.AlertType.CONFIRMATION,"Your Order is Succesfull!").show();
            setAutoIds();
            clearAllTexts();

        }else {
            new Alert(Alert.AlertType.WARNING,"Your Order Fail").show();
        }


    }

    private boolean placeOrder(Order od) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` VALUES(?,?,?,?,?,?,?) ");
            stm.setObject(1, od.getOrderId());
            stm.setObject(2, od.getOrderCustomerId());
            stm.setObject(3, od.getOrderCustomerName());
            stm.setObject(4, od.getDescription());
            stm.setObject(5, od.getOrderQty());
            stm.setObject(6, od.getOrderUnitPrice());
            stm.setObject(7, od.getDate());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetails(od.getOrderId(), od.getItems())) {
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        for (OrderDetail temp : details
        ) {
            if (CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?)", orderId, temp.getItem_code(), temp.getTotal())) {

            } else {
                return false;
            }
        }
        return true;

    }
}
