package com.zurich.battery;

public class BatteryModel {
    String health;
    String level;
    String plugged;
    boolean present;
    String status;
    int temperature;
    int voltage;

    public BatteryModel(String str, String str2, String str3, String str4, int i, int i2, boolean z) {
        this.level = str;
        this.plugged = str2;
        this.status = str3;
        this.health = str4;
        this.temperature = i;
        this.voltage = i2;
        this.present = z;
    }

    public String getPlugged() {
        return this.plugged;
    }

    public void setPlugged(String str) {
        this.plugged = str;
    }

    public float getTemperature() {
        return (float) this.temperature;
    }

    public void setTemperature(int i) {
        this.temperature = i;
    }

    public float getVoltage() {
        return (float) this.voltage;
    }

    public void setVoltage(int i) {
        this.voltage = i;
    }

    public boolean isPresent() {
        return this.present;
    }

    public void setPresent(boolean z) {
        this.present = z;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getHealth() {
        return this.health;
    }

    public void setHealth(String str) {
        this.health = str;
    }
}
