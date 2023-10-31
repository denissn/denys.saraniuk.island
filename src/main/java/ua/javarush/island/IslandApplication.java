package ua.javarush.island;


import ua.javarush.island.map.Area;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.ConsoleProvider;
import ua.javarush.island.utility.EntityFactory;
import ua.javarush.island.utility.StatisticProvider;

public class IslandApplication {
    public static void main(String[] args) {
        ConsoleProvider consoleProvider = new ConsoleProvider();
        EntityFactory entityFactory = new EntityFactory();
        Area area = new AppConfigurator(entityFactory, consoleProvider).init();

        //TODO start life cycle

        //TODO the code below is used for testing (delete after tests)
        new StatisticProvider().printStatisticArea(area);
        System.out.println("*** Location 0,0 types ***");
        area.getLocations()[0][0].getEntities().forEach((k,v)-> System.out.println(v.get(0)));

    }
}
