package com.cloud.assignment2;


import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product {

    @Column(name = "name")
    @Id
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "availability")
    private boolean availability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
