package com.example.unshelf.model.entitiesdraft;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products")
public class ProductsDraft implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "product_id")
    private int productID;


    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "quantity")
    private Long quantity;

    @ColumnInfo(name = "categories")
    private String categories;

    public ProductsDraft(int productID, Long quantity) {
        this.productID = productID;

        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
