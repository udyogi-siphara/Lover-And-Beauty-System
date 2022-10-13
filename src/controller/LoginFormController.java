package controller;


import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class LoginFormController {
    public JFXComboBox<String>cmbSelectType;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public AnchorPane LoginContext;
    public Label lblSignup;
    public JFXButton btnLogin;
    public JFXTextField txtPassword;
    public FontAwesomeIconView icnEye;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
      Pattern usernamePattern = Pattern.compile("^[A-z0-9]{6,10}$");
      LinkedHashMap<JFXPasswordField, Pattern> map1 = new LinkedHashMap<>();
      Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");

    public void initialize(){
        txtPassword.setVisible(false);

        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            pwdPassword.setText(newValue);
        });

        storeValidation();
        cmbSelectType.getItems().addAll("Manager", "User");
        btnLogin.setDisable(true);
//        cmbSelectType.setStyle

        try {
            ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Users").executeQuery();
            while (rst.next()) {
                if (rst.getString(5).equalsIgnoreCase("Manager")) {
                    //lblSignup.setDisable(true);
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        cmbSelectType.getItems().add("Manager");
//        cmbSelectType.getItems().add("User");
    }

    private void storeValidation() {
        map.put(txtUserName, usernamePattern);
        map1.put(pwdPassword, passwordPattern);

    }
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String uType = cmbSelectType.getSelectionModel().getSelectedItem();
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Users  WHERE UserType=?");
        stm.setObject(1, uType);
        ResultSet rst = stm.executeQuery();
        if (!cmbSelectType.getSelectionModel().isEmpty()) {
            while (rst.next()) {
                if (cmbSelectType.getValue().equalsIgnoreCase("Manager")) {
                    if (rst.getString(5).equals(cmbSelectType.getSelectionModel().getSelectedItem())) {
                        if (pwdPassword.getText().equals(rst.getString(4)) && txtUserName.getText().equals(rst.getString(2))) {
                            Stage stage= (Stage)LoginContext.getScene().getWindow();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/HomeForm.fxml"))));
                            stage.centerOnScreen();

//                          Notifications information = NotificationBuilder.notifyMassage("CONFIRMATION", "Log in successful");
//                          information.showInformation();

                            return;
                        }
                    }
                }
                if (cmbSelectType.getValue().equalsIgnoreCase("User")) {
                    if (rst.getString(5).equals(cmbSelectType.getSelectionModel().getSelectedItem())) {
                        if (pwdPassword.getText().equals(rst.getString(4)) && txtUserName.getText().equals(rst.getString(2))) {
                            Stage stage= (Stage)LoginContext.getScene().getWindow();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/HomeUserForm.fxml"))));
                            stage.centerOnScreen();

//                          Notifications information = NotificationBuilder.notifyMassage("CONFIRMATION", "Log in successful");
//                          information.showInformation();

                            return;
                        }
                    }
                }

            }
            Notifications error = NotificationBuilder.notifyMassage("ERROR", "Username,Password or select type is not matched.Please try again");
            error.showError();
        } else {
            Notifications warning = NotificationBuilder.notifyMassage("WARNING", "Please select type");
            warning.showWarning();
        }

//        String type = cmbSelectType.getSelectionModel().getSelectedItem().toString();
//        if (type.equals("Admin")) {
//            if(txtUserName.getText().equals("a") && pwdPassword.getText().equals("a")){
//                Stage stage= (Stage)LoginContext.getScene().getWindow();
//                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/HomeForm.fxml"))));
//                stage.centerOnScreen();
//
//            }
//
//        }else if(type.equals("User")){
//            if(txtUserName.getText().equals("u") && pwdPassword.getText().equals("u")){
//                Stage stage= (Stage)LoginContext.getScene().getWindow();
//                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/HomeUserForm.fxml"))));
//                stage.centerOnScreen();
//
//            }
//        }

    }
    public void lblSignup (MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../views/SignupForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoginContext.getChildren().clear();
        LoginContext.getChildren().add(load);
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextFields(map, btnLogin);
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

    public void passwordFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXPasswordField(map1, btnLogin);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXPasswordField) {
                JFXPasswordField errorText = (JFXPasswordField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Notifications confirmation = NotificationBuilder.notifyMassage("CONFIRMATION", "All the text fields are filled successfully.");
                confirmation.showConfirm();
            }
        }
    }

    public void eyeClickOnAction(MouseEvent mouseEvent) {
        if(icnEye.getGlyphName().equals("EYE_SLASH")){ // must show password
            icnEye.setGlyphName("EYE");

            txtPassword.setText(pwdPassword.getText()); //copy PwdPassword data to  txtPW
            pwdPassword.setVisible(false);  //PWField hidden
            txtPassword.setVisible(true);   //txtField Shown

        }else if(icnEye.getGlyphName().equals("EYE")){  // must hide  password
            icnEye.setGlyphName("EYE_SLASH");

            pwdPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false); //txtField hide
            pwdPassword.setVisible(true);  //PWField shown

        }

    }
}
