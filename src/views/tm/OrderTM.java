package views.tm;

import javafx.scene.control.Button;

public class OrderTM {
    private String orderId;
    private String orderCustomerId;
    private String orderCustomerName;
    private String description;
    private int orderQty;
    private double orderUnitPrice;
    private double total;
    private Button btn;

    public OrderTM() {
    }

    public OrderTM(String orderId, String orderCustomerId, String orderCustomerName, String description, int orderQty, double orderUnitPrice, double total, Button btn) {
        this.orderId = orderId;
        this.orderCustomerId = orderCustomerId;
        this.orderCustomerName = orderCustomerName;
        this.description = description;
        this.orderQty = orderQty;
        this.orderUnitPrice = orderUnitPrice;
        this.total = total;
        this.btn = btn;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderCustomerId='" + orderCustomerId + '\'' +
                ", orderCustomerName='" + orderCustomerName + '\'' +
                ", description='" + description + '\'' +
                ", orderQty=" + orderQty +
                ", orderUnitPrice=" + orderUnitPrice +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
