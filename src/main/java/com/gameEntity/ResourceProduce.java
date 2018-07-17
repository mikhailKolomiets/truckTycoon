package com.gameEntity;

import org.apache.commons.lang3.RandomUtils;

/**
 * Some entity like wheat field, forest or iron mind for produce resource
 */
public class ResourceProduce {
    private AmountHolder amountHolder;
    private ResourceType type;

    /**
     * Minimum produced resource for iteration
     */
    private static final int PRODUCEFOR = 100;

    /**
     * Maximum produced resource for iteration
     */
    private static final int PRODUCETO = 300;

    /**
     * Size of stock (maximum available amount of produced resource)
     */
    private static final int STOCK = 1000;

    /**
     * Initiate producer with some amountHolder of resource and random type
     */
    public ResourceProduce() {
        amountHolder = new AmountHolder();
        produceResource();
        type = ResourceType.getRandom();
    }

    /**
     * Produce for 100 to 300 resources
     */
    public void produceResource () {

        int produced = RandomUtils.nextInt(PRODUCEFOR, PRODUCETO);

        if (produced > STOCK) {
            produced = STOCK;
        }

        amountHolder.add(produced);
    }

    public AmountHolder getAmountHolder() {
        return amountHolder;
    }

    public void setAmountHolder(AmountHolder amountHolder) {
        this.amountHolder = amountHolder;
    }

    public int getAmount() {
        return amountHolder.getAmount();
    }

    public void setAmount(int amount) {
        amountHolder.setAmount(amount);
    }

    public ResourceType getType() {
        return type;
    }
}

class AmountHolder {
    private int amount;

    public void add(int digit) {
        amount += digit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
