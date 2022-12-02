public class UserGoodExample {
    private String name;
    private LoggerInterface logger;

    public UserGoodExample(String name, LoggerInterface logger) {
        this.name = name;
        this.logger = logger;
    }

    public void setLogger(LoggerInterface logger) {
        this.logger = logger;
    }

    public void sayHello() {
        this.logger.log("Hello " + this.name);
    }
}