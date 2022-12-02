public class ConsoleLogger implements LoggerInterface {
    public void log(String str) {
        System.out.println(str);
    }
}
