package com.gameEntity;

/**
 * Fabric entity. May produce certain product from certain type resource.
 */
public class Fabric {
    private AmountHolder needResource;

    /**
     * AmountHolder of the resource for produce goods then create fabric
     */
    private static final int INITAMOUNT = 1000;

    /**
     * Possible power fabric (produced goods per iteration)
     */
    private static final int MAXPRODUCE = 25;

    /**
     * AmountHolder of resources need for create 1 product
     */
    private static final int RESFOR = 10;

    /**
     * Size of stock (If stock amount less when produced goods - > fabric stop produce)
     */
    private static final int STOCK = 200;

    /**
     * Type of fabric
     */
    private ResourceType type;

    public Fabric() {
        needResource = new AmountHolder();
        needResource.setAmount(INITAMOUNT);
        type = ResourceType.getRandom();
    }

    /**
     * Produce goods from need resource
     * @return int amount produced goods for cities
     */
    public int produceGoods() {
        int producedAmount;
        if (needResource.getAmount() >= MAXPRODUCE * RESFOR) {
            needResource.add(-MAXPRODUCE * RESFOR);
            return MAXPRODUCE;
        } else {
            producedAmount = needResource.getAmount() / RESFOR;
            needResource.add(-producedAmount * RESFOR);
        }
        return producedAmount;
    }

    public int getNeedResource() {
        return needResource.getAmount();
    }

    public void setNeedResource(Integer needResource) {
        this.needResource.setAmount(needResource);
    }

    /**
     * Overload method for set certain AmountHolder
     */
    public void setNeedResource(AmountHolder amountHolder) {
        needResource = amountHolder;
    }

    public ResourceType getType() {
        return type;
    }

    public static int getSTOCK() {
        return STOCK;
    }
}
