package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Computer extends Product {
    private String processorName;
    private int processorPower;
    private String graphicsName;
    private int graphicsVolume;
    private boolean integratedWifi = false;
    private boolean integratedBluetooth = false;

    public Computer() {}

    public Computer(long id, String name, double weight, double price, String processorName, int processorPower, String graphicsName, int graphicsVolume, boolean integratedWifi, boolean integratedBluetooth) {
        this(id, name, weight, price, processorName, processorPower, graphicsName, graphicsVolume);
        this.integratedWifi = integratedWifi;
        this.integratedBluetooth = integratedBluetooth;
    }
    public Computer(long id, String name, double weight, double price, String processorName, int processorPower, String graphicsName, int graphicsVolume) {
        super(id, name, weight, price);
        this.processorName = processorName;
        this.processorPower = processorPower;
        this.graphicsName = graphicsName;
        this.graphicsVolume = graphicsVolume;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public int getProcessorPower() {
        return processorPower;
    }

    public void setProcessorPower(int processorPower) {
        this.processorPower = processorPower;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public int getGraphicsVolume() {
        return graphicsVolume;
    }

    public void setGraphicsVolume(int graphicsVolume) {
        this.graphicsVolume = graphicsVolume;
    }

    public boolean isIntegratedWifi() {
        return integratedWifi;
    }

    public void setIntegratedWifi(boolean integratedWifi) {
        this.integratedWifi = integratedWifi;
    }

    public boolean isIntegratedBluetooth() {
        return integratedBluetooth;
    }

    public void setIntegratedBluetooth(boolean integratedBluetooth) {
        this.integratedBluetooth = integratedBluetooth;
    }
}
