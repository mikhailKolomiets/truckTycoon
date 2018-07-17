package com.gameEntity;

import com.GameProperty;
import org.apache.commons.lang3.RandomUtils;

/**
 * Main entity in game. Have population those reduce goods. Have resource producing and fabric for create goods.
 */
public class City {
    private String name;

    /**
     * Represent int amount of city position in map (XXXYYY)
     * F.e: 125312 -> x = 125, y = 312
     */
    private int coordinate;
    private int population;
    private ResourceProduce resourceProduce;
    private Fabric fabric;
    private Goods goods;

    /**
     * Create default city with coordinate XXXYYY, population 1-2 M and 100 each goods
     */
    public City(String name, int coordinate) {
        this.name = name;
        this.coordinate = coordinate;

        population = RandomUtils.nextInt(GameProperty.POPULATIONFROM, GameProperty.POPULATIONTO);
        resourceProduce = new ResourceProduce();
        fabric = new Fabric();

        if(fabric.getType() == resourceProduce.getType()) {
            fabric.setNeedResource(resourceProduce.getAmountHolder());
        }

        goods = new Goods(100, 100, 100);
    }

    /**
     * @return amount of recourse produce in the fabric
     */
    public int getProducedGoods() {
        return getGoodsByType(fabric.getType());
    }

    /**
     * @return amount of goods by him type
     */
    public int getGoodsByType(ResourceType type) {
        switch (type) {
            case WHEAT:
                return goods.getBread();
            case WOOD:
                return goods.getFurniture();
            case IRON:
                return goods.getDetail();
            default:
                return 0;
        }
    }

    /**
     * @return information String about this city
     */
    public String getInfoAbout() {

        return name + ", population: " + population + ", fabric:" + " ("
                + fabric.getType().name().toLowerCase() + "(" + fabric.getNeedResource() + ") -> " + fabric.getType().getProduceName() + "(" + getProducedGoods()
                + ")" +
                "), produce resource: " + resourceProduce.getAmount() + " " + resourceProduce.getType().name().toLowerCase()
                + ", Goods: bread - " + goods.getBread() + ", detail - " + goods.getDetail() + ", furniture - " + goods.getFurniture();
    }

    /**
     * Change amount of goods in city stock by his type
     */
    public void changeAmountCertainGoods(ResourceType type, int amount) {
        switch (type) {
            case WHEAT:
                goods.setBread(goods.getBread() + amount);
                break;
            case WOOD:
                goods.setFurniture(goods.getFurniture() + amount);
                break;
            case IRON:
                goods.setDetail(goods.getDetail() + amount);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public ResourceProduce getResourceProduce() {
        return resourceProduce;
    }

    public void setResourceProduce(ResourceProduce resourceProduce) {
        this.resourceProduce = resourceProduce;
    }

    public Fabric getFabric() {
        return fabric;
    }

    public void setFabric(Fabric fabric) {
        this.fabric = fabric;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
