package ua.javarush.island.utility;

import java.util.Scanner;

public class ConsoleProvider {

    private final Scanner scanner = new Scanner(System.in);
    public String read(){
        return scanner.nextLine();
    }
    @SuppressWarnings("java:S106")
    public void print(Object message){
        System.out.print(message);
    }

    @SuppressWarnings("java:S106")
    public void println(Object message){
        System.out.println(message);
    }
}
