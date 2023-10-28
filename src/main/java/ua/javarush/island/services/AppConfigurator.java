package ua.javarush.island.services;

import org.reflections.Reflections;
import ua.javarush.island.entities.Entity;
import ua.javarush.island.entities.abstractions.annotations.Config;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.entities.entitiesLiving.animals.Animal;
import ua.javarush.island.entities.entitiesLiving.animals.predators.Wolf;
import ua.javarush.island.map.Area;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AppConfigurator {

    private static final EntityLivingPrototypeFactory entityLivingPrototypeFactory = EntityLivingPrototypeFactory.getInstance();

    private static class LazyHolder {
        static final AppConfigurator INSTANCE = new AppConfigurator();
    }

    public static AppConfigurator getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void init() {
        registerEntities();
        initializeArea();
    }

    private void registerEntities() {
        Set<? extends EntityLiving> entityLivingSet = new HashSet<>();
        Reflections reflections = new Reflections("ua.javarush.island.entities.entitiesLiving");
        Set<Class<? extends EntityLiving>> collect = reflections.getSubTypesOf(EntityLiving.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .collect(Collectors.toSet());
        for (Class<?> temp : collect) {
            System.out.println(temp.getSimpleName());
        }

    }

    private void initializeArea() {
        Area area = new Area(100, 20);

    }
}
