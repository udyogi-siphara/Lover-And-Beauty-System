package controller;

import model.Customer;
import model.Employee;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeCrudController {
    public static ArrayList<String> getEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT Cus_id FROM Employee");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Employee getEmployee(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Employee WHERE Emp_id=?", id);
        if (result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }
}
