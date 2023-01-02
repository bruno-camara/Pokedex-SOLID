package com.pokedex.utilities;

public class ConsoleLogUtility {
    public static void logTextToConsole(OutputGeneratorInterface generator) {
        System.out.println(generator.generateText());
    }
}
