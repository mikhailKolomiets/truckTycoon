package com.gameEntity;

/**
 * Present engine name with cargoMax
 */
public enum EngineType {
    HS350(30, 0),
    RS400(40, 100),
    RS480(50, 250),
    FS500(55, 400),
    FS550(65, 1000),
    FS650(85, 2500),
    Z730(100, 4000);

    /**
     * Maximum available take mass of cargo
     */
    private int cargoMax;
    private int price;

    EngineType(int cargoMax, int price) {
        this.cargoMax = cargoMax;
        this.price = price;
    }

    public int getCargoMax() {
        return cargoMax;
    }

    public void setCargoMax(int cargoMax) {
        this.cargoMax = cargoMax;
    }

    public int getPrice() {
        return price;
    }
}