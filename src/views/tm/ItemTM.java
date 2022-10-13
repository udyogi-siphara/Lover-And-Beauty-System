package views.tm;

import javafx.scene.control.Button;

public class ItemTM {
    private String itemCode;
    private String itemSupplierId;
    private String itemName;
    private double itemPrice;
    private int itemQty;
    private Button btn;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String itemSupplierId, String itemName, double itemPrice, int itemQty, Button btn) {
        this.itemCode = itemCode;
        this.itemSupplierId = itemSupplierId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.btn = btn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemSupplierId() {
        return itemSupplierId;
    }

    public void setItemSupplierId(String itemSupplierId) {
        this.itemSupplierId = itemSupplierId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemCode='" + itemCode + '\'' +
                ", itemSupplierId='" + itemSupplierId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQty=" + itemQty +
                ", btn=" + btn +
                '}';
    }
}
