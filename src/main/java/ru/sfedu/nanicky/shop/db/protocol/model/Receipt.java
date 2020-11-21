package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        if (!super.equals(o)) return false;
        Receipt receipt = (Receipt) o;
        return Double.compare(receipt.getTotalPrice(), getTotalPrice()) == 0 &&
                Objects.equals(getProductsAndPrices(), receipt.getProductsAndPrices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductsAndPrices(), getTotalPrice());
    }

    @Override
    public String toString() {
        return "Receipt: \n"  +
                "id=" + id + "\n" +
                "productsAndPrices:\n" + productsAndPrices + "\n" +
                "totalPrice=" + totalPrice + "\n";
    }
}
