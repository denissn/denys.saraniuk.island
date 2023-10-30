package ua.javarush.island.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import ua.javarush.island.entities.abstractions.annotations.Config;

@Setter
@Config(filePath = "src/main/resources/config/map/area.json")
public class Area {
    @JsonIgnore
    private Location[][] locations;
    private int x;
    private int y;

    private Area() {
    }

    public Location[][] getLocations() {
        if (locations == null) {
            init();
        }
        return locations;
    }

    private Location[][] init() {
        locations = new Location[x][y];
        return locations;
    }

}
