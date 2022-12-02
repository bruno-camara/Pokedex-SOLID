public class Main {
    public static void main(String[] args) {

        FileLogger fileLogger = new FileLogger("/tmp/logs.txt");

        UserGoodExample myUser = new UserGoodExample("bob", fileLogger);

        myUser.sayHello();

        myUser.setLogger(new ConsoleLogger());

        myUser.sayHello();

        System.out.println("Hello world!");



    }
}