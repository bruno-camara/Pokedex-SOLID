package utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLogUtility {
    public static void logHtmlToFile(String filePath, OutputHTMLGeneratorInterface generator) throws IOException {
        Files.write(Paths.get(filePath), generator.generateHtml().getBytes(StandardCharsets.UTF_8));
        System.out.println("To printando");
    }
}
