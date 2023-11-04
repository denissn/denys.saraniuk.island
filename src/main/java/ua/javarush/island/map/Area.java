package ua.javarush.island.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.annotation.Config;

@Setter
@Config(filePath = "src/main/resources/config/map/area.json")
public class Area {
    @JsonIgnore
    private Location[][] locations;
    private Direction directions;

    public Area(int x, int y) {
        locations = new Location[x][y];
    }

    public Location[][] getLocations() {
        return locations;
    }

    @Getter
    public enum Direction{
        NORTH(0,1),
        WEST(1,0),
        SOUTH(0,-1),
        EAST(-1,0);

        private final int x;
        private final int y;
        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
