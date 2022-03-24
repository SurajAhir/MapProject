package com.example.map.FirebaseWork;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class firebaseUserData implements Serializable {
    String fname,lname,address,country,state,pincode,district,emailId,password,image,backgroundImage;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public firebaseUserData() {
    }

    public firebaseUserData(String fname, String lname, String address, String country, String state, String pincode, String district, String emailId, String password, String image) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.country = country;
        this.state = state;
        this.pincode = pincode;
        this.district = district;
        this.emailId = emailId;
        this.password = password;
        this.image = image;
    }

    public firebaseUserData(String fname, String lname, String address, String country, String state, String pincode, String district, String emailId, String password, String image, String backgroundImage) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.country = country;
        this.state = state;
        this.pincode = pincode;
        this.district = district;
        this.emailId = emailId;
        this.password = password;
        this.image = image;
        this.backgroundImage = backgroundImage;
    }
}
