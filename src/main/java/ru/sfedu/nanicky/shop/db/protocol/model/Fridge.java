package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Fridge extends Product {
    private int volume;
    private String color;
    private int power;
    private boolean noFrost = false;

    public Fridge() {}

    public Fridge(long id, String name, double weight, double price, int volume, String color, int power, boolean noFrost) {
        this(id, name, weight, price, volume, color, power);
        this.noFrost = noFrost;
    }
    public Fridge(long id, String name, double weight, double price, int volume, String color, int power) {
        super(id, name, weight, price);
        this.volume = volume;
        this.color = color;
        this.power = power;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isNoFrost() {
        return noFrost;
    }

    public void setNoFrost(boolean noFrost) {
        this.noFrost = noFrost;
    }
}
