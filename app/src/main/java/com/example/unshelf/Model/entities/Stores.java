package com.example.unshelf.Model.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "stores",
        foreignKeys = @ForeignKey(
                entity = Seller.class,
                parentColumns = "seller_id",
                childColumns = "seller_id",
                onDelete = ForeignKey.CASCADE
        ))

public class Stores implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "store_id")
    private int storeID;

    @ColumnInfo(name = "seller_id")
    private Integer sellerID;

    @ColumnInfo(name = "store_name")
    private String storeName;

    @ColumnInfo(name = "rating")
    private Long rating;

    @Relation(parentColumn = "store_id", entityColumn = "store_id")
    private List<Products> products;

    @ColumnInfo(name = "is_verified")
    private Integer status;


    public Stores( Integer sellerID, String storeName, Long rating, List<Products> products, Integer status) {

        this.sellerID = sellerID;
        this.storeName = storeName;
        this.rating = rating;
        this.products = products;
        this.status = status;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public Integer getSellerID() {
        return sellerID;
    }

    public void setSellerID(Integer sellerID) {
        this.sellerID = sellerID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public Integer isStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
