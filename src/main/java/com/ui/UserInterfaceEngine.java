package com.ui;

import com.GameController;
import com.GameProperty;
import com.gameEntity.City;
import com.gameEntity.Player;
import com.gameEntity.Truck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 03.08.18.
 */
public class UserInterfaceEngine {

    private ArrayList<City> sityes;
    private GameController gameController;
    private ArrayList<City> cityList;
    private Truck playerTruck;
    private Player player;
    private List<Truck> truckList;
    public UserInterfaceEngine() {
        sityes = GameProperty.cityInit();
        gameController = new GameController();
        playerTruck = new Truck();
        cityList = gameController.getCityList();
        player = gameController.getPlayer();
        truckList = gameController.getCpuTruckList();
        playerTruck.setCityStay(cityList.get(0));
        player.setTruck(playerTruck);
        truckList.add(playerTruck);

    }

    public void play() {
        UIGrathic grathic = new UIGrathic(gameController);
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            grathic.repaint();
        }


    }
}
