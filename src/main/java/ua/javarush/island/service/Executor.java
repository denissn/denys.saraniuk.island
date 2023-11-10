package ua.javarush.island.service;

import ua.javarush.island.map.Area;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.ConsoleProvider;
import ua.javarush.island.utility.EntityFactory;
import ua.javarush.island.utility.StatisticProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    private final ConsoleProvider consoleProvider = new ConsoleProvider();
    private final StatisticProvider statisticProvider = new StatisticProvider();
    private final EntityFactory entityFactory = new EntityFactory();
    private final ExecutorService executorService = Executors.newWorkStealingPool();
    private final TaskHandler taskHandler = new TaskHandler();

    public void startSimulation() {

        consoleProvider.println("--- init game ---");
        int numberSimulationDays = getNumberSimulationDays();
        Area area = new AppConfigurator(entityFactory, consoleProvider).init();

        consoleProvider.println("--- start simulation ---");
        statisticProvider.printArea(area);
        statisticProvider.printByLocations(area);
        long start = System.currentTimeMillis();

        for (int i = 1; i <= numberSimulationDays; i++) {
            consoleProvider.println("************ day " + i + " ************");
            taskHandler.eatAllInArea(area, executorService);
            taskHandler.loveAllInArea(area, executorService);
            taskHandler.moveAllInArea(area, executorService);
            statisticProvider.printArea(area);
        }

        long end = System.currentTimeMillis();
        consoleProvider.println("--- end simulation ---");
        statisticProvider.printByLocations(area);
        statisticProvider.printArea(area);
        consoleProvider.println("Total time: " + (end - start) * 1.0 / 1000 + " s");
        executorService.shutdown();
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
