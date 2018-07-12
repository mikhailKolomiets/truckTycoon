package com.gameEntity;

/**
 * Different type of trailer for truck
 */
public enum TrailerType {
    ATM(30, 0),
    KRONE(50, 500),
    SHMITS(70, 1000),
    WIELTON(100, 2000);

    private int capacity;
    private int price;

    TrailerType(int capacity, int price) {
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }
}