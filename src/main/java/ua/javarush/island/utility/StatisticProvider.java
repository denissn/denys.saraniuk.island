package ua.javarush.island.utility;

import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticProvider {

    private final ConsoleProvider consoleProvider;

    public StatisticProvider(ConsoleProvider consoleProvider) {
        this.consoleProvider = consoleProvider;
    }

    public void printArea(Area area) {
        Map<String, Integer> map = new TreeMap<>();
        Location[][] locations = area.getLocations();
        for (Location[] locationX : locations) {
            for (Location location : locationX) {
                Map<Class<? extends Entity>, List<Entity>> entities = location.getEntities();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : entities.entrySet()) {
                    String key = entry.getKey().getSimpleName();
                    int value = entry.getValue().size();
                    if (!map.containsKey(key)) {
                        map.put(key, value);
                    } else {
                        map.put(key, map.get(key) + value);
                    }
                }
            }
        }
        consoleProvider.println("*** Area statistic ***");
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            consoleProvider.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public void printByLocations(Area area) {
        Map<String, StringBuilder> mapSb = new TreeMap<>();
        Map<Class<? extends Entity>, Entity> map = EntityFactory.getEntities();
        mapSb.put("!loc", new StringBuilder());
        for (Map.Entry<Class<? extends Entity>, Entity> item : map.entrySet()) {
            mapSb.put(item.getValue().getIcon(), new StringBuilder());
        }
        Location[][] locations = area.getLocations();
        for (int j = 0; j < locations[0].length; j++) {//have replaced j and i loops for correct island view
            for (int i = 0; i < locations.length; i++) {
                Map<Class<? extends Entity>, List<Entity>> entities = locations[i][j].getEntities();
                if (!mapSb.containsKey("!loc")) {
                    mapSb.put("!loc", new StringBuilder("|- " + i + "," + j + " -|"));
                } else {
                    mapSb.put("!loc", mapSb.get("!loc").append(" ").append("|- ").append(i).append(",").append(j).append(" -|"));
                }
                int length = mapSb.get("!loc").length();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entity : entities.entrySet()) {
                    String key;
                    if (!entity.getValue().isEmpty()) {
                        try {
                            key = (String) entity.getKey().getMethod("getIcon").invoke(entity.getValue().get(0));
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        int value = entity.getValue().size();
                        mapSb.put(key, mapSb.get(key).append(" ").append(key).append(" ").append(value));
                    }
                }
                for (Map.Entry<String, StringBuilder> map2 : mapSb.entrySet()) {
                    while (map2.getValue().length() < length) {
                        map2.getValue().append(" ");
                    }
                }
            }
            mapSb.forEach((k, v) -> consoleProvider.println(v));
            for (Map.Entry<String, StringBuilder> map2 : mapSb.entrySet()) {
                map2.getValue().setLength(0);
            }
        }
    }

}
