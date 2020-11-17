package ru.sfedu.nanicky.shop.db.protocol.model;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Soda extends Product {
    private String flavour;
    private boolean sparkled = true;

    public Soda() {}

    public Soda(long id, String name, double weight, double price, String flavour, boolean sparkled) {
        this(id, name, weight, price, flavour);
        this.sparkled = sparkled;
    }

    public Soda(long id, String name, double wight, double price, String flavour) {
        super(id, name, wight, price);
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
}
