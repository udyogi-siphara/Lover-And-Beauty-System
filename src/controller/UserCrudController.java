package controller;

import db.DBConnection;
import model.Customer;
import model.User;
import util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserCrudController {
//    public static ArrayList<String> getUserName() throws SQLException, ClassNotFoundException {
//        ResultSet result = CrudUtil.execute("SELECT UserName FROM User");
//        ArrayList<String> ids = new ArrayList<>();
//        while (result.next()){
//            ids.add(result.getString(2));
//        }
//        return ids;
//    }
    public static boolean signupUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Users` VALUES (?,?,?,?,?)");
        stm.setObject(1, user.getName());
        stm.setObject(2, user.getUserName());
        stm.setObject(3, user.getEmail());
        stm.setObject(4, user.getPassword());
        stm.setObject(5, user.getUserType());
        return stm.executeUpdate() > 0;
    }
//    public static User getUser(String id) throws SQLException, ClassNotFoundException {
//        ResultSet result= CrudUtil.execute("SELECT * FROM Users WHERE UserName=?", id);
//        if (result.next()){
//            return new User(
//                    result.getString(1),
//                    result.getString(2),
//                    result.getString(3),
//                    result.getString(4),
//                    result.getString(5)
//
//            );
//        }
//        return null;
//    }
}
