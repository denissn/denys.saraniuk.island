package ua.javarush.island.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.javarush.island.entities.abstractions.annotations.Config;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private ConfigLoader(){

    }

    private static class LazyHolder {
        static final ConfigLoader INSTANCE = new ConfigLoader();
    }

    public static ConfigLoader getInstance() {
        return ConfigLoader.LazyHolder.INSTANCE;
    }

    public <T> T getObject(Class<T> aClass) {
        if (!aClass.isAnnotationPresent(Config.class)) {
            throw new IllegalArgumentException();
        }
        File filePath = getConfigFilePath(aClass);
        return loadObject(filePath, aClass);
    }

    private File getConfigFilePath(Class<?> aClass){
        Config config = aClass.getAnnotation(Config.class);
        return new File(config.filePath());
    }

    private <T> T loadObject(File filePath, Class<T> aClass){
        ObjectMapper objectMapper = new ObjectMapper();
        T object;
        try {
            object = objectMapper.readValue(filePath, aClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

}
