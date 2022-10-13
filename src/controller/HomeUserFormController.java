package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class HomeUserFormController {
    public AnchorPane HomeContext;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotalEarning;
    public Label lblTotalItem;
    public Label lblTotalOrder;
    public LineChart LineChartOrder;

    public void initialize() throws SQLException, ClassNotFoundException {
        setLineChartOrder();
        loadDateAndTime();

        lblTotalEarning.setText(String.valueOf(OrderCrudController.getAllIncome()));
        setOrderCount();
        setItemCount();
    }

    private void setOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT COUNT(order_id) FROM Orders");

        if(result.next()){
            lblTotalOrder.setText(String.valueOf(result.getInt(1)));
        }
    }
    private void setItemCount() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT COUNT(code) FROM item");

        if(result.next()){
            lblTotalItem.setText(String.valueOf(result.getInt(1)));
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

    private void setLineChartOrder() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT date,cost FROM orders");

        double janCost=0;
        double febCost=0;
        double marCost=0;
        double aprCost=0;
        double mayCost=0;
        double junCost=0;
        double julCost=0;
        double aguCost=0;
        double sepCost=0;
        double octCost=0;
        double novCost=0;
        double decCost=0;

        while(result.next()){
            java.sql.Date dt=result.getDate(1);
            LocalDate localDate = dt.toLocalDate();

            if(localDate.getMonthValue()==1){
                janCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==2){
                febCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==3){
                marCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==4){
                aprCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==5){
                mayCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==6){
                junCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==7){
                julCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==8){
                aguCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==9){
                sepCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==10){
                octCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==11){
                novCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==12){
                decCost+=result.getDouble(2);
            }


        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);

        //lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series series = new XYChart.Series();
        series.setName("Month");

        series.getData().add(new XYChart.Data("Jan", janCost));
        series.getData().add(new XYChart.Data("Feb", febCost));
        series.getData().add(new XYChart.Data("Mar", marCost));
        series.getData().add(new XYChart.Data("Apr", aprCost));
        series.getData().add(new XYChart.Data("May", mayCost));
        series.getData().add(new XYChart.Data("Jun", junCost));
        series.getData().add(new XYChart.Data("Jul", julCost));
        series.getData().add(new XYChart.Data("Aug", aguCost));
        series.getData().add(new XYChart.Data("Sep", sepCost));
        series.getData().add(new XYChart.Data("Oct", octCost));
        series.getData().add(new XYChart.Data("Nov", novCost));
        series.getData().add(new XYChart.Data("Dec", decCost));


        // Scene scene = new Scene(lineChart, 800, 600);
        LineChartOrder.getData().add(series);
    }


}
