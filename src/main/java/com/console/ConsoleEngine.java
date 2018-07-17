package com.console;

import com.GameController;
import com.GameProperty;
import com.gameEntity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console version of game
 */
public class ConsoleEngine {

    private ArrayList<City> cityList = cityInit();
    private List<Truck> truckList = cpuTrucks();
    private Scanner scanner = new Scanner(System.in);
    private GameController gameController = new GameController();
    private ConsolePrintUtil consolePrintUtil = new ConsolePrintUtil();
    private Player player = new Player();

    public void play() {
        Truck playerTruck = new Truck();
        playerTruck.setCityStay(cityList.get(0));
        player.setTruck(playerTruck);

        System.out.println("Hello in the console game Transport Tycoon v " + GameProperty.VERSION + "\n" + ConsoleCommand.H.getInfoAboutCommands()
                + "\nYou in the " + playerTruck.getCityStay().getName());

        boolean quit = true;
        GameController.gameResult(cityList);

        truckList.add(playerTruck);
        while (quit) {


            switch (ConsoleCommand.H.getCommandFromConsole(scanner)) {
                case H:
                    System.out.println(ConsoleCommand.H.getInfoAboutCommands());
                    break;
                //print to console list of routes and select route (loadingTrailer trailer)
                case ROTES:
                    List<Route> routeList = gameController.getAllRoutes(cityList, playerTruck);
                    consolePrintUtil.printRoutes(routeList);
                    consolePrintUtil.loadTruck(player, routeList, scanner);
                    break;
                //print to console information about all cityList
                case CITIESINFO:
                    for (City city : cityList) {
                        System.out.println(city.getInfoAbout());
                    }
                    break;
                //update truck
                case UPDATE:
                    consolePrintUtil.updateTruck(playerTruck, player, scanner);
                    break;
                //info about player
                case PLAYERINFO:
                    consolePrintUtil.playerInfoFromConsole(player);
                    break;
                //go to next game day
                case N:
                    gameController.nextIteration(cityList, truckList);
                    if (!ConsolePrintUtil.checkGameResult(cityList)) {
                        System.out.println("Welcome to the " + playerTruck.getCityStay().getName());
                    } else {
                        quit = false;
                    }
                    break;
                case Q:
                    quit = false;
                    break;
            }
        }
    }

    /**
     * Ukraine map
     */
    private ArrayList<City> cityInit() {
        ArrayList<City> initSityes = new ArrayList<>();
        initSityes.add(new City("Kharkiv", 800650));
        initSityes.add(new City("Poltava", 700550));
        initSityes.add(new City("Kyiv", 550800));
        initSityes.add(new City("Lviv", 50500));
        initSityes.add(new City("Odessa", 550001));
        initSityes.add(new City("IvanoFrankovsk", 100300));
        initSityes.add(new City("Dnepropetrovsk", 720250));
        initSityes.add(new City("Donetsk", 950175));
        initSityes.add(new City("Zhitomyr", 350770));
        initSityes.add(new City("KrivoyRog", 700150));
        return initSityes;
    }

    /**
     * Create 10 default computer truck for game
     *
     * @return ArrayList of default trucks resource and goods type
     */
    private List<Truck> cpuTrucks() {
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

}
