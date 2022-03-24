package com.example.map.DataClasses;

public class ResponseErrorClass {
    String status;
    int statusCode;
    UserData data[];
    String error;

    public ResponseErrorClass(String status, int statusCode, UserData[] data, String error) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.error = error;
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

    public UserData[] getData() {
        return data;
    }

    public void setData(UserData[] data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
