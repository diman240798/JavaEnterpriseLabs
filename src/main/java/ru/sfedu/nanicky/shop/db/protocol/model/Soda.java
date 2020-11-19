package ru.sfedu.nanicky.shop.db.protocol.model;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Soda extends Product {
    private String flavour;
    private boolean sparkled = false;

    public Soda() {}

    public Soda(long id, String name, double weight, double price, String category, String flavour, boolean sparkled) {
        this(id, name, weight, price, category, flavour);
        this.sparkled = sparkled;
    }

    public Soda(long id, String name, double wight, double price, String category, String flavour) {
        super(id, name, wight, price, category);
        this.flavour = flavour;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public boolean isSparkled() {
        return sparkled;
    }

    public void setSparkled(boolean sparkled) {
        this.sparkled = sparkled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Soda)) return false;
        if (!super.equals(o)) return false;
        Soda soda = (Soda) o;
        return isSparkled() == soda.isSparkled() &&
                Objects.equals(getFlavour(), soda.getFlavour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFlavour(), isSparkled());
    }

    @Override
    public String toString() {
        return "Soda{" +
                "flavour='" + flavour + '\'' +
                ", sparkled=" + sparkled +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", id=" + id +
                '}';
    }
}
