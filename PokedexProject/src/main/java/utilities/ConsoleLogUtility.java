package utilities;

public class ConsoleLogUtility {
    public static void logTextToConsole(OutputTextGeneratorInterface generator) {
        System.out.println(generator.generateText());
    }
}
