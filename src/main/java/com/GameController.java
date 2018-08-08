package com;

import com.gameEntity.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * EngineType of main game process and calculations
 */
public class GameController {

    private static int[] citiesPopulation;
    private ArrayList<City> cityList;
    private List<Truck> cpuTruckList;
    private Player player = new Player();

    public GameController() {
        cityList = GameProperty.cityInit();
        cpuTruckList = generateCpuTrucks();
        player = new Player();
    }

    /**
     * Get list of all possible routes from city, where stay track.
     */
    public List getAllRoutes(ArrayList<City> cities, Truck truck) {
        ArrayList<Route> routes = new ArrayList<>();
        Route route;
        int amount, price, trailerCapacity = truck.getTrailerCapacity();

        City firstCity = truck.getCityStay();
        for (City secondCity : cities) {
            if (!secondCity.equals(firstCity)) {
                if (truck.isResourceType()) {
                    if (firstCity.getResourceProduce().getType() != secondCity.getFabric().getType()) {
                        continue;
                    } else {
                        route = new Route();
                        route.setIsResource(true);
                        amount = firstCity.getResourceProduce().getAmount() > trailerCapacity ? trailerCapacity : firstCity.getResourceProduce().getAmount();
                        route.setResourceType(firstCity.getResourceProduce().getType());
                        // price by need
                        price = firstCity.getResourceProduce().getAmount() > 100 ? 2 : 1;
                        price *= secondCity.getFabric().getNeedResource() < 300 ? 2 : 1;
                    }
                } else {
                    route = new Route();
                    route.setIsResource(false);

                    //40 produce goods always stay in the city
                    amount = firstCity.getProducedGoods() - 40 > 0 ? firstCity.getProducedGoods() -40 : 0;
                    amount = amount > trailerCapacity ? trailerCapacity : amount;
                    route.setResourceType(firstCity.getFabric().getType());
                    price = firstCity.getProducedGoods() > 100 ? 2 : 1;
                    price *= secondCity.getGoodsByType(route.getResourceType()) < 20 ? 2 : 1;
                }

                route.setAmount(amount);
                route.setDestination(secondCity);
                route.setDistance(getDistanceBetweenCities(firstCity, secondCity));
                price *= route.getDistance() * amount / 100;
                route.setPrice(price);

                routes.add(route);
            }
        }
        return routes;
    }

    /**
     * Count distance between 2 city
     */
    public int getDistanceBetweenCities(City from, City to) {

        //get cities coordinate
        int cityAX = from.getCoordinate() / 1000;
        int cityAY = from.getCoordinate() % 1000;
        int cityBX = to.getCoordinate() / 1000;
        int cityBY = to.getCoordinate() % 1000;

        return (int) (Math.sqrt(Math.pow(cityAX - cityBX, 2) + Math.pow(cityAY - cityBY, 2)));
    }

    /**
     * Count next cycle of game
     *
     * @param cityList   - cities
     * @param trucksList - all trucks
     */
    public void nextIteration(ArrayList<City> cityList, List<Truck> trucksList) {

        uploadCPUTrailers(trucksList, cityList, GameProperty.numberOfPlayers);

        for (Truck truck : trucksList) {
            if (truck.getRoute() != null) {
                Route route = truck.getRoute();
                truck.setCityStay(route.getDestination());
                truck.unloading();
            }
        }

        for (City city : cityList) {
            city.getResourceProduce().produceResource();
            if (city.getProducedGoods() < Fabric.getSTOCK()) {
                city.changeAmountCertainGoods(city.getFabric().getType(), city.getFabric().produceGoods());
            }
            consumptionGoods(city);
        }
    }

    /**
     * Consumption goods from city
     */
    private void consumptionGoods(City city) {
        int population = city.getPopulation() + GameProperty.CONSUPTORGROUP;
        int consumption = population / GameProperty.CONSUPTORGROUP;
        Goods cityGoods = city.getGoods();
        int bread = cityGoods.getBread();
        int detail = cityGoods.getDetail();
        int furniture = cityGoods.getFurniture();

        bread -= consumption;
        if (bread < 0) {
            population -= GameProperty.CONSUPTORGROUP;
            bread = 0;
        }

        detail -= consumption;
        if (detail < 0) {
            population -= GameProperty.CONSUPTORGROUP;
            detail = 0;
        }

        furniture -= consumption;
        if (furniture < 0) {
            population -= GameProperty.CONSUPTORGROUP;
            furniture = 0;
        }
        cityGoods.setBread(bread);
        cityGoods.setDetail(detail);
        cityGoods.setFurniture(furniture);

        if (population < GameProperty.CONSUPTORGROUP)
            population = 0;

        city.setPopulation(population);
    }

    /**
     * Create 10 default computer truck for game
     *
     * @return ArrayList of default trucks resource and goods type
     */
    private List<Truck> generateCpuTrucks() {
        ArrayList<Truck> cpuTruckList = new ArrayList<>();
        boolean resourceType = true;
        for (int i = 0; i < 10; i++) {
            Truck truck = new Truck("CPU" + i, cityList.get(i));
            truck.setIsResourceType(resourceType);
            resourceType = !resourceType;

            //source need *10 most
            if (truck.isResourceType()) {
                truck.setEngineType(EngineType.Z730);
                truck.setTrailerType(TrailerType.WIELTON);
            }
            cpuTruckList.add(truck);
        }
        return cpuTruckList;
    }

    /**
     * Set for all computer trailer best routes
     *
     * @param trucks  - list of all truck in game
     * @param players - how much players in game
     */
    private void uploadCPUTrailers(List<Truck> trucks, ArrayList<City> cityList, int players) {

        for (int i = 0; i < trucks.size() - players; i++) {
            Truck truck = trucks.get(i);
            List<Route> routeList = getAllRoutes(cityList, truck);

            if (routeList.size() > 0) {
                routeList.sort(Comparator.comparingInt(Route::getPrice).reversed());//todo make just single max price
                Route bestRoute = routeList.get(0);
                truck.loadingTrailer(bestRoute);
            }
        }
    }

    /**
     * Check game result
     * Init massive of population in start. Check percent by this massive.
     *
     * @return int progress population in percent or -100 if one of city has 0 population
     */
    public static int gameResult(List<City> cityList) {

        int result = 0;

        if (citiesPopulation == null) {
            citiesPopulation = new int[cityList.size()];
            int index = 0;
            for (City city : cityList) {
                citiesPopulation[index++] = city.getPopulation();
            }
        } else {
            int index = 0;
            double preResult;
            for (City city : cityList) {
                if (city.getPopulation() == 0) {
                    System.out.println("City " + city.getName() + " have " + city.getPopulation() + " population!");
                    return -100; //game over with lose
                }
                preResult = (city.getPopulation() - citiesPopulation[index]) / (double) citiesPopulation[index] * 10;
                result += preResult > 10 ? 10 : preResult;
                index++;
            }
        }
        return result;
    }

    /**
     * Update truck engine
     */
    public String updateTruck(EngineType engineType) {
        if (engineType.getPrice() > player.getMoney()) {
            return "You have not enough money";
        } else {
            player.getTruck().setEngineType(engineType);
            player.getTruck().setMileage(0);
            player.setMoney(player.getMoney() - engineType.getPrice());
            return "You success update engine for " + engineType.name();
        }
    }

    /**
     *  Update truck trailer or change it type
     */
    public String updateTruck(TrailerType trailerType) {
        if (trailerType == player.getTruck().getTrailerType()) {
            player.getTruck().setIsResourceType(!player.getTruck().isResourceType());
            return "You change trailer type";
        }
        if (trailerType.getPrice() > player.getMoney()) {
            return "You have not enough money";
        } else {
            player.getTruck().setTrailerType(trailerType);
            player.setMoney(player.getMoney() - trailerType.getPrice());
            return "You success update engine for " + trailerType.name();
        }
    }

    public void cashMoneyForRoute(Route route) {
        player.setMoney(player.getMoney() + route.getPrice());
    }

    /**
     * @return percent of game progress equals condition, and console message, course
     */
    public int checkGameResult() {

        int result = GameController.gameResult(cityList);
        if (result < -90) {
            System.out.println("Sorry game over.");
        } else if (result > 99) {
            System.out.println("Wow! It's great win!");
        } else {
            System.out.println("Game progress " + result + "%");
        }
        return result;
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public List<Truck> getCpuTruckList() {
        return cpuTruckList;
    }

    public Player getPlayer() {
        return player;
    }

    public static int[] getCitiesPopulation() {
        return citiesPopulation;
    }
}
