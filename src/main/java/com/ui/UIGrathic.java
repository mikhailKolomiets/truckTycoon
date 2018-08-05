package com.ui;

import com.GameController;
import com.GameProperty;
import com.gameEntity.City;
import com.gameEntity.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Mihail Kolomiets on 05.08.18.
 */
public class UIGrathic extends JFrame {

    private final int CITYPOINTRADIUS = 20;
    private GameController gameController;
    private ArrayList<City> cities;
    private List<Route> routeList;
    private String leftInfo;
    private String rigthInfo;


    UIGrathic(GameController gameController) {
        super("Truck Tycoon version " + GameProperty.VERSION);
        setSize(800, 600);
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameController = gameController;

        cities = gameController.getCityList();
        uploadAndGoByMouseClick();
        keyboardControl();
    }

    @Override
    public void paint(Graphics g) {
        Color playerColor = new Color(0x2E3FC4);
        Color citiesColor = new Color(0x0FC477);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(citiesColor);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        Point coordinate;
        for (City city : cities) {
            coordinate = cityPoint(city);
            if (gameController.getPlayer().getTruck().getCityStay().getName().equals(city.getName())) {
                g.setColor(playerColor);
            }
            g.fillOval(coordinate.x, coordinate.y, CITYPOINTRADIUS, CITYPOINTRADIUS);
            g.drawString(city.getName(), coordinate.x - 11, coordinate.y);
            //getInfoAboutCityByMouseClick(coordinate.x, coordinate.y, city.getName());
            g.setColor(citiesColor);
        }

        if (leftInfo != null) {
            String[] text = leftInfo.split(";");
            int higthCorrect = this.getHeight() - text.length * 10;
            for(String message : text) {
            g.drawString(message, 0, higthCorrect);
            higthCorrect -= 10;
            }
        }

        if (rigthInfo != null) {
            g.drawString(rigthInfo, this.getWidth() / 2, this.getHeight() - 10);
        }
    }

    private void uploadAndGoByMouseClick() {
        for (City city : cities) {
            Point point = cityPoint(city);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    leftInfo = "";
                    if (e.getX() < point.x + CITYPOINTRADIUS &&
                            e.getX() > point.x &&
                            e.getY() < point.y + CITYPOINTRADIUS &&
                            e.getY() > point.y) {
                        rigthInfo = uploadAndGo(city);

                    }
                        //System.out.println(city);
                }
            });
        }
    }

    private String uploadAndGo(City city) {
        for (Route route : routeList) {
            if(route.getDestination().getName().equals(city.getName())) {
                gameController.getPlayer().getTruck().setRoute(route);
                gameController.nextIteration(cities, gameController.getCpuTruckList());
                return  "You go to the " + city.getName();
            }
        }
        return "Where is no route for " + city.getName();
    }

    /**
     * Get city point in game map by him coordinate
     *
     * @return
     */
    private Point cityPoint(City city) {
        int x = city.getCoordinate() / 2000 + 100;
        int y = (500 - city.getCoordinate() % 1000) / 2 + 200;
        return new Point(x, y);
    }

    /**
     * Control for keyboard
     */
    private void keyboardControl() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_R:
                        routeList = gameController.getAllRoutes(cities, gameController.getPlayer().getTruck());

                        leftInfo = "";
                        for (Route route : routeList) {
                            leftInfo += route.getDestination().getName() + " " + route.getAmount() + " " +
                                    route.getPrice() + ";";
                        }
                        break;
                }
            }
        });
    }
}
