package ua.javarush.island.utility;

import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Executor {
    public void startGame() {
        ConsoleProvider consoleProvider = new ConsoleProvider();
        StatisticProvider statisticProvider = new StatisticProvider();
        consoleProvider.println("--- init game ---");
        EntityFactory entityFactory = new EntityFactory();
        Area area = new AppConfigurator(entityFactory, consoleProvider).init();

        //TODO the code below is used for testing (delete after tests)

        statisticProvider.printByLocations(area);
        //System.out.println("*** Location 0,0 types ***");
        //area.getLocations()[0][0].getEntities().forEach((k, v) -> System.out.println(v.get(0)));

        //TODO start life cycle
        consoleProvider.println("--- start game ---");
        for (int i = 0; i < 100; i++) {
            consoleProvider.println("****************************** day " + i + " *********************************************");
            eat(area);
            statisticProvider.printByLocations(area);
            love(area);
            statisticProvider.printByLocations(area);
            move(area);
            statisticProvider.printByLocations(area);
            //statisticProvider.printArea(area);
        }
    }

    private void eat(Area area) {
        System.out.println("--- all eat ---");
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Location location = locations[i][j];
                Map<Class<? extends Entity>, List<Entity>> entitiesPrototype = location.getEntities();
                //how to get random entity for eat??
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesPrototype.entrySet()) {
                    List<Entity> animals = entitiesList.getValue().stream().filter(Eating.class::isInstance).collect(Collectors.toList());
                    ListIterator<Entity> iterator = animals.listIterator();
                    while (iterator.hasNext()) {
                        Eating animal = (Eating) iterator.next();
                        animal.eat(location);
                    }
                }
            }
        }
    }

    private void love(Area area) {
        Location[][] locations = area.getLocations();
        System.out.println("--- all fall in love ---");
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Location location = locations[i][j];
                Map<Class<? extends Entity>, List<Entity>> entitiesPrototype = location.getEntities();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entities : entitiesPrototype.entrySet()) {
                    ListIterator<Entity> iterator = entities.getValue().listIterator();
                    while (iterator.hasNext()) {
                        Entity entity = iterator.next();
                        Entity newEntity = entity.reproduce(location);
                        if (newEntity != null) {
                            iterator.add(newEntity);
                        }
                    }
                }
                entitiesPrototype.entrySet().stream().filter(b-> !b.getValue().isEmpty()).forEach(e-> e.getValue().forEach(a->a.setReproduced(false)));
            }
        }
    }

    private void move(Area area) {
        System.out.println("--- all move ---");
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Location location = locations[i][j];
                Map<Class<? extends Entity>, List<Entity>> entitiesPrototype = location.getEntities();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesPrototype.entrySet()) {
                    List<Entity> collect = entitiesList.getValue().stream().filter(Movable.class::isInstance).collect(Collectors.toList());
                    ListIterator<Entity> iterator = collect.listIterator();
                    while (iterator.hasNext()){
                        ((Movable)iterator.next()).move(area, location);
                    }
                }
            }
        }
    }

}
