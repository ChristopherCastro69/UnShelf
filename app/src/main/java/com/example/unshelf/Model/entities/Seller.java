package com.example.unshelf.Model.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "sellers")
public class Seller implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "seller_id")
    private int sellerID;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone_number")
    private Long phoneNumber;
    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "store_name")
    private String storeName;

    @ColumnInfo(name = "address")
    private String address;

    // Add other fields as needed

    // Constructor
    public Seller(String email, String password, Long phoneNumber, String fullName, String storeName, String address) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.storeName = storeName;
        this.address = address;
    }

//Getters and Setters

    public void setEmail(String Email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
