package com.console;

import com.GameController;
import com.gameEntity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Any operation for print into the console
 */
public class ConsolePrintUtil {

    /**
     * Print for console information about route List
     */
    public void printRoutes(List<Route> routeList) {
        int number = 1;
        String item;

        for (Route route : routeList) {
            if(route.isResource()) {
                item = route.getResourceType().name().toLowerCase();
            } else {
                item = route.getResourceType().getProduceName();
            }

            System.out.println(number++ + " to " + route.getDestination().getName() + " - " + item + "(" + route.getAmount()
                    + ") - " + route.getDistance() + "km - " + route.getPrice() + "$");
        }
    }

    public void loadTruck(Player player, List<Route> routeList, Scanner scanner) {
        if (routeList.size() > 0) {
            Truck truck = player.getTruck();
            System.out.println("For loading trailer select route number:");
            try {
                int routeInex = Integer.valueOf(scanner.nextLine()) - 1;
                if (routeInex >= routeList.size() | routeInex < 0) {
                    routeInex = 0;
                }

                Route route = routeList.get(routeInex);
                truck.loadingTrailer(route);

                // imagine, the cities have so many money and its pay d't trouble they
                player.setMoney(player.getMoney() + route.getPrice());
                System.out.println(truck.getName() + " success loading trailer.");
            } catch (NumberFormatException nfe) {
                System.out.println("Abort loading trailer trailer");
            }
        } else {
            System.out.println("Sorry, no rotes");
        }
    }

    /**
     * Console buying trailer or engine for truck
     *
     * @param truck   - any truck
     * @param player  - costumer
     * @param scanner - console helper
     */
    public void updateTruck(Truck truck, Player player, Scanner scanner) {
        int index = 0;
        HashMap<Integer, Enum> shop = new HashMap<>();

        for (EngineType engine : EngineType.values()) {
            if (engine.getPrice() <= player.getMoney() & engine != truck.getEngineType()) {
                System.out.println(++index + " " + engine.name() + " may pull " + engine.getCargoMax() + " amount of cargo");
                shop.put(index, engine);
            }
        }
        for (TrailerType trailerType : TrailerType.values()) {
            if (trailerType.getPrice() <= player.getMoney() & trailerType != truck.getTrailerType()) {
                System.out.println(++index + " " + trailerType.name() + " maximum size " + trailerType.getCapacity());
                shop.put(index, trailerType);
            }
        }

        if (shop.size() == 0) {
            System.out.println("You have't money for update, sorry");
        } else {
            try {
                System.out.println("Select number for update, or 0 to choice type");
                int keyToBye = Integer.valueOf(scanner.nextLine());
                if (keyToBye == 0) {
                    truck.setIsResourceType(!truck.isResourceType());
                    System.out.println("You update you truck for " + (truck.isResourceType() ? "resource type" :
                            "goods type"));
                } else {
                    Enum item = shop.get(keyToBye);
                    if (item instanceof EngineType) {
                        EngineType bueEngine = (EngineType) item;
                        truck.setEngineType(bueEngine);
                        player.setMoney(player.getMoney() - bueEngine.getPrice());
                        System.out.println("You success update engine to " + bueEngine.name());
                    } else {
                        TrailerType bueTrailer = (TrailerType) item;
                        truck.setTrailerType(bueTrailer);
                        player.setMoney(player.getMoney() - bueTrailer.getPrice());
                        truck.setMileage(0);
                        System.out.println("You success buy trailer " + bueTrailer.name());
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Is d't to fen-chuy for updating");
            }
        }

    }

    /**
     * Console information about player
     */
    public void playerInfoFromConsole(Player player) {
        Truck truck = player.getTruck();
        System.out.println(player.getName() + ". Money " + player.getMoney() + ". Truck: " + truck.getName() +
                (truck.isResourceType() ? " - resource Type " : " - goods type ") + ". Engine: " + truck.getEngineType().name() +
                "(" + truck.getMileage() + "), trailer: " + truck.getTrailerType() + " (" + truck.getTrailerCapacity() + ")");
    }

}
