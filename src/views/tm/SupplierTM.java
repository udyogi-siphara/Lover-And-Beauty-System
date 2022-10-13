package views.tm;

import javafx.scene.control.Button;

public class SupplierTM {
    private String SupplierId;
    private String SupplierName;
    private String SupplierAddress;
    private String SupplierNic;
    private String SupplierPhone;
    private Button btn;

    public SupplierTM(String supplierId) {
        SupplierId = supplierId;
    }

    public SupplierTM(String supplierId, String supplierName, String supplierAddress, String supplierNic, String supplierPhone, Button btn) {
        SupplierId = supplierId;
        SupplierName = supplierName;
        SupplierAddress = supplierAddress;
        SupplierNic = supplierNic;
        SupplierPhone = supplierPhone;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "SupplierId='" + SupplierId + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", SupplierAddress='" + SupplierAddress + '\'' +
                ", SupplierNic='" + SupplierNic + '\'' +
                ", SupplierPhone='" + SupplierPhone + '\'' +
                ", btn=" + btn +
                '}';
    }
}
