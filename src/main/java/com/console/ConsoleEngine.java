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

    public void play() {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        ConsolePrintUtil consolePrintUtil = new ConsolePrintUtil();

        ArrayList<City> cityList = gameController.getCityList();
        Player player = gameController.getPlayer();
        List<Truck> truckList = gameController.getCpuTruckList();

        Truck playerTruck = new Truck();
        playerTruck.setCityStay(cityList.get(0));
        player.setTruck(playerTruck);

        System.out.println("Hello in the console game Transport Tycoon v " + GameProperty.VERSION + "\n" + ConsoleCommand.H.getInfoAboutCommands()
                + "\nYou in the " + playerTruck.getCityStay().getName());

        boolean quit = true;
        int gameProgress;
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
                    gameProgress = gameController.checkGameResult();
                    if (gameProgress > -100 & gameProgress < 100) {
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

}
