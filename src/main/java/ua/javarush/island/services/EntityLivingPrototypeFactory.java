package ua.javarush.island.services;

import ua.javarush.island.entities.Entity;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;

import java.util.HashMap;
import java.util.Map;

public class EntityLivingPrototypeFactory {

    private final Map<Class<? extends EntityLiving>, EntityLiving> prototypes = new HashMap<>();

    private EntityLivingPrototypeFactory(){

    }

    private static class LazyHolder {
        static final EntityLivingPrototypeFactory INSTANCE = new EntityLivingPrototypeFactory();
    }

    public static EntityLivingPrototypeFactory getInstance() {
        return EntityLivingPrototypeFactory.LazyHolder.INSTANCE;
    }

    public void registerPrototype(EntityLiving prototype) {
        prototypes.put(prototype.getClass(), prototype);
    }

    public EntityLiving create (Class<? extends EntityLiving> type){
        return prototypes.get(type).reproduce();

    }

}
