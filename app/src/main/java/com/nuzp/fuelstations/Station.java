package com.nuzp.fuelstations;

import java.io.Serializable;

public class Station implements Serializable {
    private int station_id, brand_id;
    private String description, address, phone;

    public Station(int station_id, int brand_id, String description, String address, String phone) {
        this.station_id = station_id;
        this.brand_id = brand_id;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }

    public int getStation_id() {
        return station_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
