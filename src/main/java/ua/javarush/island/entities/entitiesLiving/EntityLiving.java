package ua.javarush.island.entities.entitiesLiving;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

/*
    private <T> T copyObject(T object) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T) mapper.readValue(mapper.writeValueAsString(object), object.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
*/

    private <T> T copy(T object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> objectClass = object.getClass();
        T newObject = (T) objectClass.getConstructor().newInstance();
        while (objectClass != null) {
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                field.set(newObject, field.get(object));
            }
            objectClass = objectClass.getSuperclass();
        }
        return newObject;
    }
}
