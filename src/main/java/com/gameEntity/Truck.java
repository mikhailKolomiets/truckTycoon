package com.gameEntity;

/**
 * Truck pull resource or goods between cities by himself trailer
 */
public class Truck {
    private String name;

    /**
     * How much kilometers go with trailer
     */
    private int mileage;


    private EngineType engineType;
    private TrailerType trailerType;
    private boolean isResourceType;

    /**
     * Where is truck stay
     */
    private City cityStay;
    private Route route;

    /**
     * Amount of trailer loading
     */
    private int cargo;

    /**
     * Amount of each kilometers when trailer lost 1% of his capacity
     */
    private static final int DAMEGES = 1000;

    /**
     * Minimal possible amount of trailer capacity
     */
    private static final int MINCAPACITY = 5;

    public Truck() {
        name = "Default truck";
        engineType = EngineType.HS350;
        trailerType = TrailerType.ATM;
    }

    public Truck(String name, City cityStay) {
        this();
        this.name = name;
        this.cityStay = cityStay;
    }

    /**
     * @return amount of trailer capacity by mileage(each 10000 km trailer lose 1% capacity) but no less 5.
     * Engine c't pull mass above then cargo max
     */
    public int getTrailerCapacity() {
        int capacity = trailerType.getCapacity() < engineType.getCargoMax() ?
                trailerType.getCapacity() : engineType.getCargoMax();
        capacity = (int) (Math.ceil(capacity - capacity * 0.01 * mileage / DAMEGES));
        return capacity > MINCAPACITY ? capacity : MINCAPACITY;
    }

    /**
     * Load trailer for go in the route
     */
    public void loadingTrailer(Route route) {

        if (isResourceType()) {
            ResourceProduce cityResource = cityStay.getResourceProduce();
            cityResource.setAmount(cityResource.getAmount() - route.getAmount());
        } else {
            cityStay.changeAmountCertainGoods(route.getResourceType(), -route.getAmount());
        }
        cargo = route.getAmount();
        this.route = route;
    }

    /**
     * Unload trailer in the destination City for arriwing to it
     */
    public void unloading() {
        cityStay = route.getDestination();

        if (isResourceType()) {
            Fabric cityFabric = cityStay.getFabric();
            cityFabric.setNeedResource(cityFabric.getNeedResource() + cargo);
        } else {
            cityStay.changeAmountCertainGoods(route.getResourceType(), cargo);
        }

        mileage += route.getDistance();
        System.out.println(name + " unload " + cargo + " " + (isResourceType() ? route.getResourceType().name().toLowerCase() : route.getResourceType().getProduceName()) +
        " to the " + cityStay.getName());
        route = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public TrailerType getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(TrailerType trailerType) {
        this.trailerType = trailerType;
    }

    public boolean isResourceType() {
        return isResourceType;
    }

    public void setIsResourceType(boolean type) {
        isResourceType = type;
    }

    public City getCityStay() {
        return cityStay;
    }

    public void setCityStay(City cityStay) {
        this.cityStay = cityStay;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
}
