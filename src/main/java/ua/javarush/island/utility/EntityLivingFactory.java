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

    public EntityLiving getEntityLiving(Class<? extends EntityLiving> type){
        return entitiesLiving.get(type).reproduce();
    }

    public Map<Class<? extends EntityLiving>, EntityLiving> getEntitiesLiving() {
        return entitiesLiving;
    }

    public void printDefaultEntities() { //TODO the method is used for testing (delete after tests)
        System.out.println("-".repeat(42));
        entitiesLiving.forEach((k, v) -> System.out.println(getEntityLiving(k)));
    }
}
