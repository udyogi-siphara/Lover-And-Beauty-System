package model;

public class Item {
    private String itemCode;
    private String itemSupplierId;
    private String itemName;
    private double itemPrice;
    private int itemQty;

    public Item() {
    }

    public Item(String itemCode, String itemSupplierId, String itemName, double itemPrice, int itemQty) {
        this.itemCode = itemCode;
        this.itemSupplierId = itemSupplierId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
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

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemSupplierId='" + itemSupplierId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQty=" + itemQty +
                '}';
    }
}
