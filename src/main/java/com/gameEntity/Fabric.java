package com.gameEntity;

/**
 * Fabric entity. May produce certain product from certain type resource.
 */
public class Fabric {
    private Integer amountNeedResource;

    /**
     * Type of fabric
     */
    private ResourceType type;

    public Fabric() {
        amountNeedResource = 200;
        type = ResourceType.getRandom();
    }

    /**
     * Produce goods from need resource by type of fabric (10 res - 1 goods, 50 goods max)
     * @return produced goods for cities
     */
    public int produceGoods() {
        int producedAmount;
        if (amountNeedResource >= 500) {
            amountNeedResource -= 500;
            return 50;
        } else {
            producedAmount = amountNeedResource / 10;
            amountNeedResource %= 10;
        }
        return producedAmount;
    }

    public int getAmountNeedResource() {
        return amountNeedResource;
    }

    public void setAmountNeedResource(int amountNeedResource) {
        this.amountNeedResource = amountNeedResource;
    }

    public void setAmountNeedResource(Integer amount) {
        amountNeedResource = amount;
    }
    public ResourceType getType() {
        return type;
    }

}
