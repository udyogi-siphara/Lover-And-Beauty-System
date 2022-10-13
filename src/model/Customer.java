package model;

public class Customer {
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerNic;
    private String CustomerPhone;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String customerAddress, String customerNic, String customerPhone) {
        CustomerId = customerId;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerNic = customerNic;
        CustomerPhone = customerPhone;
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

    @Override
    public String toString() {
        return "Customer{" +
                "CustomerId='" + CustomerId + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerAddress='" + CustomerAddress + '\'' +
                ", CustomerNic='" + CustomerNic + '\'' +
                ", CustomerPhone='" + CustomerPhone + '\'' +
                '}';
    }
}
