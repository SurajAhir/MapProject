package com.example.map.ServicesData;

import java.util.List;

public class Categories {
List<Prices>prices;

    public List<Prices> getPrices() {
        return prices;
    }

    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }

    public Categories(List<Prices> prices) {
        this.prices = prices;
    }
}
