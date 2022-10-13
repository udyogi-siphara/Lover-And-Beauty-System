package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Customer;
import model.User;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SettingFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtNewUserName;
    public JFXPasswordField txtNewPassword;
    public JFXPasswordField txtConfirmPassowrd;
    public JFXPasswordField txtOldPassword;
    public JFXTextField pwdNewPassword;
    public JFXTextField pwdConfirmPassowrd;
    public JFXTextField pwdOldPassword;
    public JFXButton btnUpdate;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern newUserNamePattern = Pattern.compile("^[A-z0-9]{6,10}$");
    Pattern oldPasswordPattern = Pattern.compile("[A-z0-9]{8,}");
    Pattern newPasswordPattern = Pattern.compile("[A-z0-9]{8,}");
    Pattern confirmPasswordPattern = Pattern.compile("[A-z0-9]{8,}");

    public void initialize(){
        loadDateAndTime();
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtNewUserName, newUserNamePattern);
        map.put(pwdOldPassword, oldPasswordPattern);
        map.put(pwdNewPassword, newPasswordPattern);
        map.put(pwdConfirmPassowrd, confirmPasswordPattern);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try{
            if(pwdNewPassword.getText().equals(pwdConfirmPassowrd.getText())){

                boolean isUpdated = CrudUtil.execute("UPDATE users set Username=?, passwordId=?  WHERE passwordId=?",txtNewUserName.getText(), pwdNewPassword.getText(), pwdOldPassword.getText() );
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated.").showAndWait();

                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again!").showAndWait();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Your Password is wrong.").showAndWait();

            }



        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtNewUserName.clear();
        txtOldPassword.clear();
        txtNewPassword.clear();
        txtConfirmPassowrd.clear();

    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(map, btnUpdate);
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
