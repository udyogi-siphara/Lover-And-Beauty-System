package controller;

import model.Order;
import model.OrderDetail;
import util.CrudUtil;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailCrudController {
    public boolean saveOrderDetail(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        for (OrderDetail det : details) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?)",
                    det.getOrderDetail_id(), det.getItem_code(),det.getTotal());
        }
        return true;
    }


}
