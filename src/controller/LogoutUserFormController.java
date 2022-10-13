package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LogoutUserFormController {

    public AnchorPane LogoutContext;
    public AnchorPane HomeContext;
    public AnchorPane LogoutUserMsgContext;
    public JFXButton btnYes;

    public void btnYesOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HomeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/LoginForm.fxml"))));
        stage.centerOnScreen();

        Stage stage1 = (Stage) btnYes.getScene().getWindow();
        stage1.close();
    }

    public void btnNoOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) HomeContext.getScene().getWindow();
        stage.close();
    }

    public void getAllData(AnchorPane homeContext) {
        this.HomeContext = homeContext;
    }
}
