package ua.javarush.island;


import ua.javarush.island.entities.Entity;
import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Caterpillar;
import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Duck;
import ua.javarush.island.entities.entitiesLiving.animals.predators.Wolf;
import ua.javarush.island.entities.entitiesLiving.plants.Grass;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.ConsoleProvider;
import ua.javarush.island.utility.EntityLivingFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IslandApplication {
    public static void main(String[] args) {
        Area area = AppConfigurator.getInstance().init();

        //TODO the code below is used for testing
        ConsoleProvider consoleProvider = ConsoleProvider.getInstance();
        EntityLivingFactory entityLivingFactory = EntityLivingFactory.getInstance();
        consoleProvider.println(entityLivingFactory.getEntityLiving(Wolf.class));
        consoleProvider.println(entityLivingFactory.getEntityLiving(Duck.class));
        consoleProvider.println(entityLivingFactory.getEntityLiving(Caterpillar.class));
        consoleProvider.println(entityLivingFactory.getEntityLiving(Grass.class));

        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = getStatisticArea(area);
        System.out.println("*".repeat(42));
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    private static Map<String, Integer> getStatisticArea(Area area) {
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
        return map;
    }
}
