package ua.javarush.island.service;

import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.ConsoleProvider;
import ua.javarush.island.utility.EntityFactory;
import ua.javarush.island.utility.StatisticProvider;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

public class Executor {
    private final ConsoleProvider consoleProvider = new ConsoleProvider();
    private final StatisticProvider statisticProvider = new StatisticProvider();
    private final EntityFactory entityFactory = new EntityFactory();

    public void startSimulation() {

        consoleProvider.println("--- init game ---");
        Area area = new AppConfigurator(entityFactory, consoleProvider).init();
        int numberSimulationDays = getNumberSimulationDays();

        consoleProvider.println("--- start simulation ---");
        statisticProvider.printArea(area);
        statisticProvider.printByLocations(area);
        long start = System.currentTimeMillis();

        for (int i = 1; i <= numberSimulationDays; i++) {
            consoleProvider.println("*************** day " + i + " ***************");
            eatAllInArea(area);
            loveAllInArea(area);
            moveAllInArea(area);
            //statisticProvider.printByLocations(area);
            statisticProvider.printArea(area);
        }

        long end = System.currentTimeMillis();
        consoleProvider.println("--- end simulation ---");
        statisticProvider.printByLocations(area);
        consoleProvider.println("Total time: " + (end - start) * 1.0 / 1000 + " s");
        statisticProvider.printArea(area);
    }

    private void eatAllInArea(Area area) {
        Location[][] locations = area.getLocations();
        for (Location[] locationX : locations) {
            for (Location location : locationX) {
                Map<Class<? extends Entity>, List<Entity>> entitiesPrototype = location.getEntities();
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

    private void loveAllInArea(Area area) {
        Location[][] locations = area.getLocations();
        for (Location[] locationX : locations) {
            for (Location location : locationX) {
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
                setAllReadyForLove(entitiesPrototype);
            }
        }
    }

    private void setAllReadyForLove(Map<Class<? extends Entity>, List<Entity>> entitiesPrototype){
        entitiesPrototype.entrySet().stream().filter(b -> !b.getValue().isEmpty()).forEach(e -> e.getValue().forEach(a -> a.setReproduced(false)));
    }

    private void moveAllInArea(Area area) {
        Location[][] locations = area.getLocations();
        for (Location[] locationX : locations) {
            for (Location location : locationX) {
                Map<Class<? extends Entity>, List<Entity>> entitiesPrototype = location.getEntities();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entitiesList : entitiesPrototype.entrySet()) {
                    List<Entity> collect = entitiesList.getValue().stream().filter(Movable.class::isInstance).collect(Collectors.toList());
                    ListIterator<Entity> iterator = collect.listIterator();
                    while (iterator.hasNext()) {
                        ((Movable) iterator.next()).move(area, location);
                    }
                }
            }
        }
    }

    private int getNumberSimulationDays() {
        int maxNumberSimulationDays = 10000;
        consoleProvider.print("Please, enter number simulation days (1:" + maxNumberSimulationDays + "): ");
        String inputLine = consoleProvider.read();
        if (inputLine != null) {
            try {
                int size = Integer.parseInt(inputLine);
                if (size > 0 && size <= maxNumberSimulationDays) {
                    return size;
                }
                throw new IllegalArgumentException("Invalid input size. Check input data. ");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Input is not a number. Check input data. ");
            }
        }
        throw new IllegalArgumentException("Invalid input data. Check input data. ");
    }

}
