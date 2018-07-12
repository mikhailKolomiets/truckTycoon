package com.gameEntity;

import org.apache.commons.lang3.RandomUtils;

/**
 * Some entity like wheat field, forest or iron mind for produce resource
 */
public class ResourceProduce {
    private Integer amount;
    private ResourceType type;

    /**
     * Initiate producer with some amount of resource and random type
     */
    public ResourceProduce() {
        amount = 0;
        produceResource();
        type = ResourceType.getRandom();
    }

    /**
     * Produce for 100 to 300 resources
     */
    public void produceResource () {
        amount = amount + RandomUtils.nextInt(100, 300);
    }

    public int getAmount() {
        return amount;
    }

    public Integer getIntegerAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ResourceType getType() {
        return type;
    }
}
