package com.gameEntity;

/**
 * Entity logistic bit
 */
public class Route {
    private int distance;
    private City destination;
    private int amount;
    private ResourceType resourceType;
    private boolean isResource;
    private int price;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isResource() {
        return isResource;
    }

    public void setIsResource(boolean isResource) {
        this.isResource = isResource;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
