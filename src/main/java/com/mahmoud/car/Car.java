package com.mahmoud.car;

import java.math.BigDecimal;
import java.util.UUID;

public class Car {

    private UUID id;
    private String regNumber;
    private BigDecimal rentalPricePerDay;
    private Brand brand;
    private boolean isElectric;
    private boolean isAvailable;

    public Car(UUID id, String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric, boolean isAvailable) {
        this.id = id;
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
        this.isAvailable = isAvailable;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public Brand getBrand() {
        return brand;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", rentalPricePerDay=" + rentalPricePerDay +
                ", brand=" + brand +
                ", isElectric=" + isElectric +
                ", isAvailable=" + isAvailable +
                "}";
    }
}
