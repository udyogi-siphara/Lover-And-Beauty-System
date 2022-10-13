package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Order;
import model.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public static ArrayList<String> getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT order_id FROM orders");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Order getOrder(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM orders WHERE order_id=?", id);
        if (result.next()){
            return new Order(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getDouble(6),
                    result.getDate(7)
            );
        }
        return null;
    }

    public static double getAllIncome() throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT total FROM OrderDetail");
        double tot=0;
        while(result.next()){
            double d=result.getDouble(1);
            tot+=d;

        }
        return tot;

    }


}
