package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET qty=qty-? WHERE code=?", qty,itemCode);
    }


    public static ArrayList<String> getItemCode() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT code FROM Item");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static ObservableList<String> getItemName() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT DISTINCT name  FROM Item");
        ObservableList<String> ptObList = FXCollections.observableArrayList();

        while (result.next()) {
            ptObList.add(result.getString(1));
        }
        return ptObList;
    }


    public static Item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Item WHERE code=?", id);
        if (result.next()){
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)

            );
        }
        return null;
    }
}
