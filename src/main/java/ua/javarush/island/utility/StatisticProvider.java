package ua.javarush.island.utility;

import ua.javarush.island.entities.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticProvider {
    private StatisticProvider(){

    }

    private static class LazyHolder {
        static final StatisticProvider INSTANCE = new StatisticProvider();
    }

    public static StatisticProvider getInstance() {
        return StatisticProvider.LazyHolder.INSTANCE;
    }

    public void printStatisticArea(Area area) { //TODO need refactoring
        Map<String, Integer> map = new TreeMap<>();
        Map<String, StringBuilder> mapSb = new TreeMap<>();
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                if (!mapSb.containsKey("!loc")) {
                    mapSb.put("!loc", new StringBuilder("------ " + i + "," + j + " ------"));
                } else {
                    mapSb.put("!loc", mapSb.get("!loc").append(" ").append("------ ").append(i).append(",").append(j).append(" ------"));
                }
                int length = mapSb.get("!loc").length();
                Map<Class<? extends Entity>, List<Entity>> entities = locations[i][j].getEntities();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : entities.entrySet()) {
                    String key = entry.getKey().getSimpleName();
                    int value = entry.getValue().size();
                    if (!mapSb.containsKey(key)) {
                        mapSb.put(key, new StringBuilder(key + " " + value));
                    } else {
                        mapSb.put(key, mapSb.get(key).append(" ").append(key).append(" ").append(value));
                    }
                    if (!map.containsKey(key)) {
                        map.put(key, value);
                    } else {
                        map.put(key, map.get(key) + value);
                    }
                    while (mapSb.get(key).length() < length) {
                        mapSb.put(key, mapSb.get(key).append(" "));
                    }
                }
            }
            mapSb.forEach((k, v) -> System.out.println(v));
            mapSb.clear();
        }
        System.out.println("*".repeat(42));
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

}
