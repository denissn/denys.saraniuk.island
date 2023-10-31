package ua.javarush.island.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import ua.javarush.island.abstraction.annotation.Config;

@Setter
@Config(filePath = "src/main/resources/config/map/area.json")
public class Area {
    @JsonIgnore
    private Location[][] locations;

    public Area(int x, int y) {
        locations = new Location[x][y];
    }

    public Location[][] getLocations() {
        return locations;
    }

}
