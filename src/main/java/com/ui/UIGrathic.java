package com.ui;

import com.GameController;
import com.GameProperty;
import com.gameEntity.City;
import com.gameEntity.EngineType;
import com.gameEntity.Route;
import com.gameEntity.TrailerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Graphic interface.
 */
public class UIGrathic extends JFrame {

    private final int CITYPOINTRADIUS = 20;
    private GameController gameController;
    private ArrayList<City> cities;
    private List<Route> routeList;
    private String leftInfo;
    private String rigthInfo;
    private boolean byeEngine, byeTrailer;
    private int gameResult;


    UIGrathic(GameController gameController) {
        super("Truck Tycoon version " + GameProperty.VERSION);
        setSize(800, 600);
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gameController = gameController;
        cities = gameController.getCityList();
        uploadAndGoByMouseClick();
        updateTruck();
        keyboardControl();
        leftInfo = "";
    }

    @Override
    public void paint(Graphics g) {
        Color playerColor = new Color(0x2E3FC4);
        Color citiesColor = new Color(0x0FC477);
        Color loseColor = new Color(0xC41B1B);
        Color winColor = new Color(0xB6C40A);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(citiesColor);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        Point coordinate;
        int cityResultNumber = 0;
        int population;
        for (City city : cities) {
            coordinate = cityPoint(city);
            if (gameController.getPlayer().getTruck().getCityStay().getName().equals(city.getName())) {
                g.setColor(playerColor);
            }
            population = GameController.getCitiesPopulation()[cityResultNumber++];
            population = (int) ((city.getPopulation() - population) / (double) population * 100);
            g.fillOval(coordinate.x, coordinate.y, CITYPOINTRADIUS, CITYPOINTRADIUS);
            g.drawString(city.getName() + "(" + population + " %)", coordinate.x - 11, coordinate.y);
            g.setColor(citiesColor);
        }

        if (leftInfo != null) {
            String[] text = leftInfo.split(";");
            int higthCorrect = this.getHeight() - text.length * 10;
            for (String message : text) {
                g.drawString(message, 0, higthCorrect);
                higthCorrect += 10;
            }
        }

        if (rigthInfo != null) {
            g.drawString(rigthInfo, this.getWidth() / 2, this.getHeight() - 10);
        }

        g.drawString("Money: " + gameController.getPlayer().getMoney() + " $ Truck: " +
                gameController.getPlayer().getTruck().getEngineType() + " Trailer: " +
                gameController.getPlayer().getTruck().getTrailerType(), 450, 10);

        if(gameResult < -99) {
            g.setColor(loseColor);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 32));
            g.drawString("You lose, sorry", this.getWidth()/2 - 200, this.getHeight()/2 - 20);
        }else if(gameResult > 99) {
            g.setColor(winColor);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 32));
            g.drawString("You won!!!", this.getWidth()/2 - 200, this.getHeight()/2 - 20);
        }
    }

    /**
     * Check click for certain city point on map, if ok -> invoke method for uploading etc...
     */
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

                        gameResult = gameController.checkGameResult();
                        rigthInfo += ". Game progress: " + gameResult + " %.";
                    }
                }
            });
        }
    }

    /**
     * Update by screen position update list
     */
    private void updateTruck() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() < 100 && e.getY() > 500){
                    checkUpdateTruck(e.getY());
                    byeEngine = false;
                    byeTrailer = false;
                }
            }
        }) ;
    }

    /**
     * upload player trailer and go to the next iteration
     */
    private String uploadAndGo(City city) {

        if (routeList == null) {
            return "No route. Find press 'R'";
        }
        for (Route route : routeList) {
            if (route.getDestination().getName().equals(city.getName())) {
                gameController.getPlayer().getTruck().setRoute(route);
                gameController.getPlayer().getTruck().loadingTrailer(route);
                gameController.nextIteration(cities, gameController.getCpuTruckList());
                gameController.cashMoneyForRoute(route);
                routeList = null;
                return "You go to the " + city.getName();
            }
        }
        return "Where is no route for " + city.getName();
    }

    private void checkUpdateTruck(int yPos) {
        int startCheck = 520;
        if (byeEngine)
        for(EngineType engineType : EngineType.values()) {
            if (yPos > startCheck && yPos < startCheck + 10) {
                rigthInfo = gameController.updateTruck(engineType);
            }
            startCheck += 10;
        }
        startCheck = 550;
        if (byeTrailer)
            for(TrailerType trailerType : TrailerType.values()) {
                if (yPos > startCheck && yPos < startCheck + 10) {
                    rigthInfo = gameController.updateTruck(trailerType);
                }
                startCheck += 10;
            }
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
                    case KeyEvent.VK_U:

                        leftInfo = "";
                        for (EngineType engineType : EngineType.values()) {
                            leftInfo += engineType + " " + engineType.getPrice() + "$;";
                            byeEngine = true;
                        }
                        break;
                    case KeyEvent.VK_T:

                        leftInfo = "";
                        for (TrailerType trailerType : TrailerType.values()) {
                            leftInfo += trailerType + " " + trailerType.getPrice() + "$;";
                            byeTrailer = true;
                        }
                        break;

                    case KeyEvent.VK_I:
                        leftInfo = "";
                        rigthInfo = "";
                        for (City city : cities) {
                            leftInfo += city.getInfoAbout() + ";";
                        }
                        break;
                }
            }
        });
    }

    public int getGameResult() {
        return gameResult;
    }
}
