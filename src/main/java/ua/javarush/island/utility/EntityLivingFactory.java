package ua.javarush.island.utility;

import ua.javarush.island.entities.entitiesLiving.EntityLiving;

import java.util.HashMap;
import java.util.Map;

public class EntityLivingFactory {

    private final Map<Class<? extends EntityLiving>, EntityLiving> entitiesLiving = new HashMap<>();

    private EntityLivingFactory(){

    }

    private static class LazyHolder {
        static final EntityLivingFactory INSTANCE = new EntityLivingFactory();
    }

    public static EntityLivingFactory getInstance() {
        return EntityLivingFactory.LazyHolder.INSTANCE;
    }

    public void registerEntity(EntityLiving entity) {
        entitiesLiving.put(entity.getClass(), entity);
    }

    public EntityLiving create (Class<? extends EntityLiving> type){
        return entitiesLiving.get(type).reproduce();
    }
}
