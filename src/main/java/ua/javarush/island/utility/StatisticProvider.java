package ua.javarush.island.utility;

import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticProvider {

    public void printStatisticsArea(Area area) {
        printByLocations(area);
        //printArea(area);
    }

    private void printArea(Area area) {
        Map<String, Integer> map = new TreeMap<>();
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Map<Class<? extends Entity>, List<Entity>> entities = locations[i][j].getEntities();
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
        System.out.println("*** Area statistic ***");
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    private void printByLocations(Area area) {
        System.out.println("*** by Location statistic ***");
        Map<String, StringBuilder> mapSb = new TreeMap<>();
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
                    String key; //= entity.getKey().getSimpleName();
                    if (!entity.getValue().isEmpty()) {
                        try {
                            key = (String) entity.getKey().getMethod("getIcon").invoke(entity.getValue().get(0));
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        int value = entity.getValue().size();
                        if (!mapSb.containsKey(key)) {
                            mapSb.put(key, new StringBuilder(key + " " + value));
                        } else {
                            mapSb.put(key, mapSb.get(key).append(" ").append(key).append(" ").append(value));
                        }
                        while (mapSb.get(key).length() < length) {
                            mapSb.put(key, mapSb.get(key).append(" "));
                        }
                    }
                }
            }
            mapSb.forEach((k, v) -> System.out.println(v));
            mapSb.clear();
        }
    }

}
