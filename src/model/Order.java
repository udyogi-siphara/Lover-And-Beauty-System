package model;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private String orderCustomerId;
    private String orderCustomerName;
    private String description;
    private int orderQty;
    private double orderUnitPrice;
    private Date date;
    private ArrayList<OrderDetail>items;

    public Order(String orderId, String orderCustomerId, String orderCustomerName, String description, int orderQty, double orderUnitPrice, Date date, ArrayList<OrderDetail> items) {
        this.orderId = orderId;
        this.orderCustomerId = orderCustomerId;
        this.orderCustomerName = orderCustomerName;
        this.description = description;
        this.orderQty = orderQty;
        this.orderUnitPrice = orderUnitPrice;
        this.date = date;
        this.items = items;
    }

    public ArrayList<OrderDetail> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetail> items) {
        this.items = items;
    }

    public Order() {
    }

    public Order(String orderId, String orderCustomerId, String orderCustomerName, String description, int orderQty, double orderUnitPrice, Date date) {
        this.orderId = orderId;
        this.orderCustomerId = orderCustomerId;
        this.orderCustomerName = orderCustomerName;
        this.description = description;
        this.orderQty = orderQty;
        this.orderUnitPrice = orderUnitPrice;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public String getOrderCustomerName() {
        return orderCustomerName;
    }

    public void setOrderCustomerName(String orderCustomerName) {
        this.orderCustomerName = orderCustomerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getOrderUnitPrice() {
        return orderUnitPrice;
    }

    public void setOrderUnitPrice(double orderUnitPrice) {
        this.orderUnitPrice = orderUnitPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderCustomerId='" + orderCustomerId + '\'' +
                ", orderCustomerName='" + orderCustomerName + '\'' +
                ", description='" + description + '\'' +
                ", orderQty=" + orderQty +
                ", orderUnitPrice=" + orderUnitPrice +
                ", date=" + date +
                '}';
    }
}
