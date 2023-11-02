package ua.javarush.island.utility;

import ua.javarush.island.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityFactory {

    private static final Map<Class<? extends Entity>, Entity> entities = new HashMap<>();

    public void registerEntity(Entity entity) {
        entities.put(entity.getClass(), entity);
    }

    public static Entity getEntity(Class<? extends Entity> type){
        return copyEntity(entities.get(type));
    }

    public Map<Class<? extends Entity>, Entity> getEntities() {
        return entities;
    }

    private static <T> T copyEntity(T entity) {
        Class<?> entityClass = entity.getClass();
        T newEntity;
        try {
            newEntity = (T) entityClass.getConstructor().newInstance();
            while (entityClass != null) {
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(newEntity, field.get(entity));
                }
                entityClass = entityClass.getSuperclass();
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException();
        }
        return newEntity;
    }

}
