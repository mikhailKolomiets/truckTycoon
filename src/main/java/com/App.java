package com;

import com.console.ConsoleEngine;
import com.ui.UserInterfaceEngine;

/**
 * Created by Mihail Kolomiets on 08.07.18.
 */
public class App {
    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("ui")) {
            new UserInterfaceEngine().play();
        } else {
            new ConsoleEngine().play();
        }
    }
}
