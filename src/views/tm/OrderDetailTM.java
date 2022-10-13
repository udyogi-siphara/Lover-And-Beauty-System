package views.tm;

public class OrderDetailTM {
    private String orderDetail_id;
    private String item_code;
    private double total;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String orderDetail_id, String item_code, double total) {
        this.orderDetail_id = orderDetail_id;
        this.item_code = item_code;
        this.total = total;
    }

    public String getOrderDetail_id() {
        return orderDetail_id;
    }

    public void setOrderDetail_id(String orderDetail_id) {
        this.orderDetail_id = orderDetail_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "orderDetail_id='" + orderDetail_id + '\'' +
                ", item_code='" + item_code + '\'' +
                ", total=" + total +
                '}';
    }
}
