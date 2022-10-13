package controller;

import model.Customer;
import model.Driver;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverCrudController {
    public static ArrayList<String> getDriverId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT driver_id FROM Driver");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Driver getDriver(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Driver WHERE driver_id=?", id);
        if (result.next()){
            return new Driver(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }
}
