package controller;

import Notification.NotificationBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SignupFormController {
    public Text lblLogin;
    public AnchorPane SignUpContext;
    public JFXTextField txtName;
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;
    public JFXComboBox<String>cmbSelectType;
    public JFXButton btnSignup;
    public JFXPasswordField pwdPassword;
    public JFXPasswordField pwdConfirmPassword;
    public JFXTextField txtPass;
    public JFXTextField txtConfPass;
    public FontAwesomeIconView icnEye;
    public FontAwesomeIconView icnConEye;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern NamePattern = Pattern.compile("^[A-z]{3,}$");
    Pattern userNamePattern = Pattern.compile("^[A-z0-9]{6,10}$");
    Pattern emailPattern = Pattern.compile("[a-z0-9]{3,}@(gmail|yahoo).com");

    LinkedHashMap<JFXPasswordField, Pattern> map1 = new LinkedHashMap<>();
    Pattern passwordPattern = Pattern.compile("[A-z0-9]{8,}");
    Pattern confirmPasswordPattern = Pattern.compile("[A-z0-9]{8,}");

    public void initialize() {
        txtPass.setVisible(false);

        txtPass.textProperty().addListener((observable, oldValue, newValue) -> {
            pwdPassword.setText(newValue);
        });

        txtConfPass.setVisible(false);

        txtConfPass.textProperty().addListener((observable, oldValue, newValue) -> {
            pwdConfirmPassword.setText(newValue);
        });

        btnSignup.setDisable(true);
        cmbSelectType.getItems().addAll("Manager","User");
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtName, NamePattern);
        map.put(txtUserName, userNamePattern);
        map.put(txtEmail, emailPattern);
        map1.put(pwdPassword, passwordPattern);
        map1.put(pwdConfirmPassword, confirmPasswordPattern);
    }

    public void lblLogin(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../views/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        SignUpContext.getChildren().clear();
        SignUpContext.getChildren().add(load);
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        if (pwdPassword.getText().equals(pwdConfirmPassword.getText())) {
            if (!cmbSelectType.getSelectionModel().isEmpty()) {
                User user = new User(txtName.getText(), txtUserName.getText(),txtEmail.getText(),pwdPassword.getText(), cmbSelectType.getSelectionModel().getSelectedItem());
                try {
                    if (UserCrudController.signupUser(user)) {
                        txtName.clear();
                        txtUserName.clear();
                        txtEmail.clear();
                        pwdPassword.clear();
                        pwdConfirmPassword.clear();
                        cmbSelectType.getSelectionModel().clearSelection();
                        Notifications information = NotificationBuilder.notifyMassage("CONFIRMATION", "Saved Successfully");
                        information.showInformation();

                    } else {

                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    Notifications error = NotificationBuilder.notifyMassage("ERROR", "User name is exists.Please try again");
                    error.showError();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Notifications error = NotificationBuilder.notifyMassage("ERROR", "Please select a user type");
                error.showError();
            }
        } else {
            Notifications error = NotificationBuilder.notifyMassage("ERROR", "Password is not matched,Please try again");
            error.showError();
        }
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextFields(map, btnSignup);
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
        Object response = ValidationUtil.validateJFXPasswordField(map1, btnSignup);
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

            txtPass.setText(pwdPassword.getText()); //copy PwdPassword data to  txtPW
            pwdPassword.setVisible(false);  //PWField hidden
            txtPass.setVisible(true);   //txtField Shown

        }else if(icnEye.getGlyphName().equals("EYE")){  // must hide  password
            icnEye.setGlyphName("EYE_SLASH");

            pwdPassword.setText(txtPass.getText());
            txtPass.setVisible(false); //txtField hide
            pwdPassword.setVisible(true);  //PWField shown

        }

    }

    public void eyeClickConfirmOnAction(MouseEvent mouseEvent) {
        if(icnConEye.getGlyphName().equals("EYE_SLASH")){ // must show password
            icnConEye.setGlyphName("EYE");

            txtConfPass.setText(pwdConfirmPassword.getText()); //copy PwdPassword data to  txtPW
            pwdConfirmPassword.setVisible(false);  //PWField hidden
            txtConfPass.setVisible(true);   //txtField Shown

        }else if(icnConEye.getGlyphName().equals("EYE")){  // must hide  password
            icnConEye.setGlyphName("EYE_SLASH");

            pwdConfirmPassword.setText(txtConfPass.getText());
            txtConfPass.setVisible(false); //txtField hide
            pwdConfirmPassword.setVisible(true);  //PWField shown

        }

    }
}
