package ua.javarush.island.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.javarush.island.abstraction.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ConfigLoader {

    public <T> T getObject(Class<T> aClass) {
        if (!aClass.isAnnotationPresent(Config.class)) {
            throw new IllegalArgumentException();
        }
        String filePath = getConfigFilePath(aClass);
        return loadObject(filePath, aClass);
    }

    private String getConfigFilePath(Class<?> aClass) {
        Config config = aClass.getAnnotation(Config.class);
        return config.filePath();
    }

    private <T> T loadObject(String filePath, Class<T> aClass) {
        T object = null;
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            byte[] bytes = Objects.requireNonNull(inputStream).readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                object = objectMapper.readValue(new String(bytes), aClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return object;
    }

}
