package com;

import com.gameEntity.City;
import java.util.ArrayList;

/**
 * Game data
 */
public class GameProperty {

    /**
     * Count players in game
     */
    public static int numberOfPlayers = 1;

    public static final String VERSION = "2.0";

    /**
     * Minimal people living in the city from create him
     */
    public final static int POPULATIONFROM = 1000000;

    /**
     * Maximum people living in the city from create him
     */
    public final static int POPULATIONTO = 2000000;

    /**
     * Amount of people those consumption each goods
     */
    public static final int CONSUPTORGROUP = 100000;

    /**
     * Ukraine map
     */
    public static ArrayList<City> cityInit() {
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

}
