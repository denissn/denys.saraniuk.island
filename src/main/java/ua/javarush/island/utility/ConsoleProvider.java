package ua.javarush.island.utility;

public class ConsoleProvider {
    private static class LazyHolder {
        static final ConsoleProvider INSTANCE = new ConsoleProvider();
    }

    public static ConsoleProvider getInstance() {
        return ConsoleProvider.LazyHolder.INSTANCE;
    }

    @SuppressWarnings("java:S106")
    public void println(Object message){
        System.out.println(message);
    }
}
