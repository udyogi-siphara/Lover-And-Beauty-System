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
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;
import util.ValidationUtil;
import views.tm.EmployeeTM;
import views.tm.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblEmployees;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmployeeNic;
    public TableColumn colEmployeePhone;
    public TableColumn colEmployeePosition;
    public TableColumn colButton;
    public JFXTextField txtEmployeeId;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeAddress;
    public JFXTextField txtEmployeeNic;
    public JFXTextField txtEmployeePhone;
    public JFXComboBox<String>cmbEmployeePosition;
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpNic;
    public JFXTextField txtEmpPhone;
    public JFXComboBox cmbEmpPosition;
    public JFXButton btnAddEmployee;
    public JFXButton btnUpdateEmployee;

    LinkedHashMap<JFXTextField, Pattern> emp = new LinkedHashMap<>();
    Pattern employeeIdPattern = Pattern.compile("^(E)[0-9]{3}$");
    Pattern employeeNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern employeeAddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern employeeNicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern employeePhonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");
    LinkedHashMap<JFXTextField, Pattern> emp1 = new LinkedHashMap<>();
    Pattern empIdPattern = Pattern.compile("^(E)[0-9]{3}$");
    Pattern empNamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern empAddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern empNicPattern = Pattern.compile("^[0-9]{12}$");
    Pattern empPhonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");

    public void initialize(){
        btnAddEmployee.setDisable(true);
        btnUpdateEmployee.setDisable(true);
        storeValidations();
        cmbEmployeePosition.getItems().add("Manager");
        cmbEmployeePosition.getItems().add("Cashier");
        cmbEmployeePosition.getItems().add("Driver");

        cmbEmpPosition.getItems().add("Manager");
        cmbEmpPosition.getItems().add("Cashier");
        cmbEmpPosition.getItems().add("Driver");


        colEmployeeId.setCellValueFactory(new PropertyValueFactory("EmployeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory("EmployeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory("EmployeeAddress"));
        colEmployeeNic.setCellValueFactory(new PropertyValueFactory("EmployeeNic"));
        colEmployeePhone.setCellValueFactory(new PropertyValueFactory("EmployeePhone"));
        colEmployeePosition.setCellValueFactory(new PropertyValueFactory("EmployeePosition"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

        loadDateAndTime();

        try {
            loadAllEmployee();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setAutoIds();
    }

    private void storeValidations() {
        btnAddEmployee.setDisable(true);
        btnUpdateEmployee.setDisable(true);
        emp.put(txtEmployeeId, employeeIdPattern);
        emp.put(txtEmployeeName, employeeNamePattern);
        emp.put(txtEmployeeAddress, employeeAddressPattern);
        emp.put(txtEmployeeNic, employeeNicPattern);
        emp.put(txtEmployeePhone, employeePhonePattern );
        emp1.put(txtEmpId, empIdPattern);
        emp1.put(txtEmpName, empNamePattern);
        emp1.put(txtEmpAddress, empAddressPattern);
        emp1.put(txtEmpNic, empNicPattern);
        emp1.put(txtEmpPhone, empPhonePattern);
    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn=new Button("Delete");
            btn.setOnAction(event -> {
                EmployeeTM selectedItem= (EmployeeTM) tblEmployees.getSelectionModel().getSelectedItem();
                txtEmployeeId.setText(selectedItem.getEmployeeId());


                deleteEmployee();

                try {
                    loadAllEmployee();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setAutoIds();
            });
            obList.add(
                    new EmployeeTM(
                            result.getString("Emp_id"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("NIC"),
                            result.getString("phone_number"),
                            result.getString("role_type"),
                            btn
                    )
            );
        }
        tblEmployees.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeNic.clear();
        txtEmployeePhone.clear();
        cmbEmployeePosition.setValue(null);
        txtEmployeeId.requestFocus();
    }
    private void clearAllTexts2() {
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpNic.clear();
        txtEmpPhone.clear();
        cmbEmpPosition.setValue(null);
        txtEmpId.requestFocus();
    }

    private void deleteEmployee() {
        try{

            if(CrudUtil.execute("DELETE FROM Employee WHERE Emp_id=?",txtEmployeeId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").showAndWait();
                loadAllEmployee();


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

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee e = new Employee(txtEmployeeId.getText(),txtEmployeeName.getText(),txtEmployeeAddress.getText(),txtEmployeeNic.getText(),txtEmployeePhone.getText(),cmbEmployeePosition.getValue());

        try {
            if(CrudUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?,?)",e.getEmployeeId(),e.getEmployeeName(),e.getEmployeeAddress(),e.getEmployeeNic(),e.getEmployeePhone(),e.getEmployeePosition())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !...").showAndWait();
            }

        } catch (ClassNotFoundException | SQLException p) {
            p.printStackTrace();
            new Alert(Alert.AlertType.ERROR,p.getMessage()).showAndWait();
        }
        clearAllTexts();
        loadAllEmployee();
        setAutoIds();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Employee WHERE Emp_id = ?",txtEmpId.getText());
            if (result.next()) {
                txtEmpName.setText(result.getString(2));
                txtEmpAddress.setText(result.getString(3));
                txtEmpNic.setText(result.getString(4));
                txtEmpPhone.setText(result.getString(5));
                cmbEmpPosition.setValue(result.getString(6));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result!..").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnUpdateEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee em = new Employee(txtEmpId.getText(),txtEmpName.getText(),txtEmpAddress.getText(),txtEmpNic.getText(),txtEmpPhone.getText(), (String) cmbEmpPosition.getValue());


        try{
            boolean isUpdated = CrudUtil.execute("UPDATE  Employee SET name = ?, address = ?, NIC = ?, phone_number = ?, role_type = ? WHERE Emp_id = ?",em.getEmployeeName(),em.getEmployeeAddress(),em.getEmployeeNic(),em.getEmployeePhone(),em.getEmployeePosition(),em.getEmployeeId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();

            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }


        }catch (SQLException | ClassNotFoundException e){

        }

        clearAllTexts2();
        loadAllEmployee();
        setAutoIds();
    }

    private void setAutoIds(){
        txtEmployeeId.setText(GenerateAutoId.autoId("SELECT Emp_id FROM Employee ORDER BY Emp_id DESC LIMIT 1",1, 2,"E001"));
    }

    public void textFieldOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(emp, btnAddEmployee);
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

    public void textFiledsOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(emp1,btnUpdateEmployee);
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
