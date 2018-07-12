package com.console;

import java.util.Scanner;

/**
 * From control in console
 */
public enum ConsoleCommand {
    H("h", "help"),
    PLAYERINFO("i", "info about you"),
    ROTES("r", "list available rotes"),
    CITIESINFO("ci", "information about all cities"),
    UPDATE("u", "update truck"),
    N("n", "next iteration"),
    Q("q", "quit");
    private String consoleValue;
    private String info;

    ConsoleCommand(String consoleValue, String info) {
        this.consoleValue = consoleValue;
        this.info = info;
    }

    /**
     * Convert console input into ConsoleCommand
     */
    public ConsoleCommand getCommandFromConsole(Scanner scanner) {

        String input = scanner.nextLine();

        for (ConsoleCommand consoleCommand : ConsoleCommand.values()) {
            if(input.equals(consoleCommand.consoleValue))
                return consoleCommand;
        }
        return H;
    }

    public String getInfoAboutCommands() {
        String infoAbout = "";
        for (ConsoleCommand consoleCommand : ConsoleCommand.values()) {
            infoAbout += consoleCommand.consoleValue + " - " + consoleCommand.info + ", ";
        }
        return infoAbout.substring(0, infoAbout.length() - 2);
    }

}
