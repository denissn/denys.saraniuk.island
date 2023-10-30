package ua.javarush.island;


import ua.javarush.island.map.Area;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.EntityLivingFactory;
import ua.javarush.island.utility.StatisticProvider;

public class IslandApplication {
    public static void main(String[] args) {
        Area area = AppConfigurator.getInstance().init();

        //TODO start life cycle

        //TODO the code below is used for testing (delete after tests)
        StatisticProvider.getInstance().printStatisticArea(area);
        EntityLivingFactory.getInstance().printDefaultEntities();
    }
}
