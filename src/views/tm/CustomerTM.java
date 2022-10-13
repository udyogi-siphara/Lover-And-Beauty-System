package views.tm;

import javafx.scene.control.Button;

public class CustomerTM {
     private String CustomerId;
     private String CustomerName;
     private String CustomerAddress;
     private String CustomerNic;
     private String CustomerPhone;
     private Button btn;

    public CustomerTM() {
    }

    public CustomerTM(String customerId, String customerName, String customerAddress, String customerNic, String customerPhone, Button btn) {
        CustomerId = customerId;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerNic = customerNic;
        CustomerPhone = customerPhone;
        this.btn = btn;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerNic() {
        return CustomerNic;
    }

    public void setCustomerNic(String customerNic) {
        CustomerNic = customerNic;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "CustomerId='" + CustomerId + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerAddress='" + CustomerAddress + '\'' +
                ", CustomerNic='" + CustomerNic + '\'' +
                ", CustomerPhone='" + CustomerPhone + '\'' +
                ", btn=" + btn +
                '}';
    }
}
