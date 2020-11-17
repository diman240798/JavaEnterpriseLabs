package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Receipt extends IdEntity {
    private String productsAndPrices;
    private double totalPrice;

    public Receipt() {}

    public Receipt(long id, String productsAndPrices, double totalPrice) {
        setId(id);
        this.productsAndPrices = productsAndPrices;
        this.totalPrice = totalPrice;
    }

    public String getProductsAndPrices() {
        return productsAndPrices;
    }

    public void setProductsAndPrices(String productsAndPrices) {
        this.productsAndPrices = productsAndPrices;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
