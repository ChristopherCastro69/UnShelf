package com.example.unshelf.model.entitiesdraft;

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
                entity = SellerDraft.class,
                parentColumns = "seller_id",
                childColumns = "seller_id",
                onDelete = ForeignKey.CASCADE
        ))

public class StoresDraft implements Serializable {
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
    private List<ProductsDraft> products;

    @ColumnInfo(name = "is_verified")
    private Integer status;


    public StoresDraft(Integer sellerID, String storeName, Long rating, List<ProductsDraft> products, Integer status) {

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

    public List<ProductsDraft> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsDraft> products) {
        this.products = products;
    }

    public Integer isStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
