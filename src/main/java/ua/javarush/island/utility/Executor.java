package ua.javarush.island.utility;

import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Executor {
    public void startGame() {
        ConsoleProvider consoleProvider = new ConsoleProvider();
        System.out.println("--- init game ---");
        EntityFactory entityFactory = new EntityFactory();
        Area area = new AppConfigurator(entityFactory, consoleProvider).init();

        //TODO the code below is used for testing (delete after tests)
        StatisticProvider statisticProvider = new StatisticProvider();
        statisticProvider.printStatisticsArea(area);
        //System.out.println("*** Location 0,0 types ***");
        //area.getLocations()[0][0].getEntities().forEach((k, v) -> System.out.println(v.get(0)));

        //TODO start life cycle
        System.out.println("--- start game ---");
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Location location = locations[i][j];
                Map<Class<? extends Entity>, List<Entity>> entitiesMap = location.getEntities();
                /*System.out.println("--- all eat ---");
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesMap.entrySet()) {
                    entitiesList.getValue().stream().filter(Animal.class::isInstance).forEach(a -> ((Animal) a).eat(location));
                }*/
                System.out.println("--- all love ---");
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesMap.entrySet()) {
                    ListIterator<Entity> iterator = entitiesList.getValue().listIterator();
                    while (iterator.hasNext()){
                        Entity entity = iterator.next();
                        Entity newEntity = entity.reproduce(location);
                        if (newEntity != null) {
                            iterator.add(newEntity);
                        }
                    }
                }
                /*System.out.println("--- all move ---");
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesMap.entrySet()) {
                    entitiesList.getValue().stream().filter(Animal.class::isInstance).forEach(a -> ((Animal) a).move(location));
                }*/
            }
        }
        statisticProvider.printStatisticsArea(area);

    }
}
