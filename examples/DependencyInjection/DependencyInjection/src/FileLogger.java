import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLogger implements LoggerInterface {
    private String filePath;

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }


    public void log(String str) {
        try {
            Files.write(Paths.get(this.filePath), str.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
