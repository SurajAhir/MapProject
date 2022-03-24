package com.example.map.DataClasses;

import java.util.List;

public class ResponseClass {
    String status;
    int statusCode;
    UserData data;

    public ResponseClass(String status, int statusCode, UserData data) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
