package com.example.map.ServicesData;

public class Prices {
    String _id,title;
    int amount;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Prices(String _id, String title, int amount) {
        this._id = _id;
        this.title = title;
        this.amount = amount;
    }
}
