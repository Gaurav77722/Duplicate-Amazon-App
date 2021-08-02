package com.example.project02;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02.db.AppDatabase;

@Entity(tableName = AppDatabase.TABLE2_NAME_)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int itemId;

    @ColumnInfo(name = "product_name")
    private String product_name;

    @ColumnInfo(name = "product_price")
    private int product_price;

    @ColumnInfo(name = "product_location")
    private String product_location;

    @ColumnInfo(name = "product_quantity")
    private int product_quantity;

    @ColumnInfo(name = "product_details")
    private String product_details;

    private int itemUserId;

    public int getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(int itemUserId) {
        this.itemUserId = itemUserId;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_location() {
        return product_location;
    }

    public void setProduct_location(String product_location) {
        this.product_location = product_location;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    @Override
    public String toString() {
        return "Product name: " + product_name + '\n' +
                "Product price: $" + product_price + '\n' +
                "Product quantity: " + product_quantity + "\n"+
                "Shipping from: " + product_location + '\n' +
                "Product details: " + product_details + '\n' +
                "=====================================\n\n";
    }
}
