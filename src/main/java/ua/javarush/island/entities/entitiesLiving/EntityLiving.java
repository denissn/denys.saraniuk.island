package ua.javarush.island.entities.entitiesLiving;

import lombok.ToString;
import ua.javarush.island.entities.Entity;
import ua.javarush.island.entities.abstractions.interfaces.Reproductive;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class EntityLiving extends Entity implements Reproductive {

    @Override
    public EntityLiving reproduce() {
        try {
            return copy(this);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T copy(T entity) throws IllegalAccessException, InstantiationException {
        Class<?> aClass = entity.getClass();
        T newEntity = (T) entity.getClass().newInstance();

        while (aClass != null) {
            copyFields(entity, newEntity, aClass);
            aClass = aClass.getSuperclass();
        }

        return newEntity;
    }

    private <T> T copyFields(T entity, T newEntity, Class<?> aClass) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();
        for (Field field : aClass.getDeclaredFields()) {
            fields.add(field);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(newEntity, field.get(entity));
        }
        return newEntity;
    }
}
