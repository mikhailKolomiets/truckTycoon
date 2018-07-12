package com.gameEntity;

/**
 * Goods entity
 */
public class Goods {
    private int bread;
    private int furniture;
    private int detail;

    public void addGoods(Goods goods) {
        bread += goods.getBread();
        furniture += goods.getFurniture();
        detail += goods.getDetail();
    }

    public Goods(int bread, int furniture, int detail) {
        this.bread = bread;
        this.furniture = furniture;
        this.detail = detail;
    }

    public int getBread() {
        return bread;
    }

    public void setBread(int bread) {
        this.bread = bread;
    }

    public int getFurniture() {
        return furniture;
    }

    public void setFurniture(int furniture) {
        this.furniture = furniture;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }
}
