package ua.javarush.island.utility;

import ua.javarush.island.entities.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticProvider {
    private StatisticProvider() {

    }

    private static class LazyHolder {
        static final StatisticProvider INSTANCE = new StatisticProvider();
    }

    public static StatisticProvider getInstance() {
        return StatisticProvider.LazyHolder.INSTANCE;
    }

    public void printStatisticArea(Area area) {
        printLocationsStatistic(area);
        printAreaStatistic(area);
    }

    private void printAreaStatistic(Area area){
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
        System.out.println("*".repeat(42));
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    private void printLocationsStatistic(Area area) {
        System.out.println("*".repeat(42));
        Map<String, StringBuilder> mapSb = new TreeMap<>();
        Location[][] locations = area.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Map<Class<? extends Entity>, List<Entity>> entities = locations[i][j].getEntities();
                /*if (!mapSb.containsKey("!loc")) {
                    mapSb.put("!loc", new StringBuilder("|----- " + i + "," + j + " -----|"));
                } else {
                    mapSb.put("!loc", mapSb.get("!loc").append(" ").append("|----- ").append(i).append(",").append(j).append(" -----|"));
                }*/
                if (!mapSb.containsKey("!loc")) {
                    mapSb.put("!loc", new StringBuilder("|- " + i + "," + j + " -|"));
                } else {
                    mapSb.put("!loc", mapSb.get("!loc").append(" ").append("|- ").append(i).append(",").append(j).append(" -|"));
                }
                int length = mapSb.get("!loc").length();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : entities.entrySet()) {
                    //String key = entry.getKey().getSimpleName();
                    String key = null;
                    try {
                        key = (String) entry.getKey().getField("icon").get(entry.getValue().get(0));
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    int value = entry.getValue().size();
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
            mapSb.forEach((k, v) -> System.out.println(v));
            mapSb.clear();
        }
    }

}
