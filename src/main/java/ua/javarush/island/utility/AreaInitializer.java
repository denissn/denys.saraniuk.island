package ua.javarush.island.utility;

import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AreaInitializer {

    private final int maxAreaSizeX = 100;
    private final int maxAreaSizeY = 20;
    private final ConsoleProvider consoleProvider;

    public AreaInitializer(ConsoleProvider consoleProvider) {
        this.consoleProvider = consoleProvider;
    }

    public Area initializeArea(EntityFactory entityFactory) {
        Area area = getAreaSize();
        Location[][] locations = area.getLocations();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Map<Class<? extends Entity>, Entity> entities = entityFactory.getEntities();
                locations[i][j] = new Location(i, j);
                for (Map.Entry<Class<? extends Entity>, Entity> entityItem : entities.entrySet()) {
                    int amountMax = entityItem.getValue().getAmountMax();
                    int countEntitiesByType = random.nextInt(amountMax);
                    for (int k = 0; k < countEntitiesByType; k++) {
                        locations[i][j].addEntity(entityFactory.getEntityClass(entityItem.getKey()));
                    }
                }
            }
        }
        return area;
    }

    public Area getAreaSize() {
        int x = getAreaSizeX();
        int y = getAreaSizeY();
        return new Area(x, y);
    }

    private int getAreaSizeX() {
        return getAreaDimension("X", maxAreaSizeX);
    }

    private int getAreaSizeY() {
        return getAreaDimension("Y", maxAreaSizeY);
    }

    private int getAreaDimension(String dimension, int maxSizeDimension) {
        consoleProvider.print("Please, enter Island " + dimension + " size (1:" + maxSizeDimension + "):");
        String inputLine = consoleProvider.read();
        if (inputLine != null) {
            try {
                int size = Integer.parseInt(inputLine);
                if (size > 0 && size <= maxSizeDimension) {
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
