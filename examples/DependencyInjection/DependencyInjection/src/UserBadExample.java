public class UserBadExample {
    private String name;
    private ConsoleLogger consoleLogger;

    public UserBadExample(String name) {
        this.name = name;
        this.consoleLogger = new ConsoleLogger();
    }

    public void sayHello() {
        consoleLogger.log("Hello " + this.name);
    }
}
