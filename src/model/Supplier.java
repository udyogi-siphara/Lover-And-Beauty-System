package model;

public class Supplier {
    private String SupplierId;
    private String SupplierName;
    private String SupplierAddress;
    private String SupplierNic;
    private String SupplierPhone;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String supplierAddress, String supplierNic, String supplierPhone) {
        SupplierId = supplierId;
        SupplierName = supplierName;
        SupplierAddress = supplierAddress;
        SupplierNic = supplierNic;
        SupplierPhone = supplierPhone;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getSupplierAddress() {
        return SupplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        SupplierAddress = supplierAddress;
    }

    public String getSupplierNic() {
        return SupplierNic;
    }

    public void setSupplierNic(String supplierNic) {
        SupplierNic = supplierNic;
    }

    public String getSupplierPhone() {
        return SupplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        SupplierPhone = supplierPhone;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SupplierId='" + SupplierId + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", SupplierAddress='" + SupplierAddress + '\'' +
                ", SupplierNic='" + SupplierNic + '\'' +
                ", SupplierPhone='" + SupplierPhone + '\'' +
                '}';
    }
}
