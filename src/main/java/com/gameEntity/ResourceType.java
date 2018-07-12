package com.gameEntity;

import org.apache.commons.lang3.RandomUtils;

/**
 * Represent resource with goods by it produce
 */
public enum ResourceType {
    WHEAT("bread"),
    WOOD("furniture"),
    IRON("detail");

    private String produceName;

    ResourceType(String produceName) {
        this.produceName = produceName;
    }

    public String getProduceName() {
        return produceName;
    }

    /**
     * @return one of issue resource type by randomize
     */
    public static ResourceType getRandom() {
        return ResourceType.values()[RandomUtils.nextInt(0,3)];
    }
}
