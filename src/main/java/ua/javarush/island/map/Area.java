package ua.javarush.island.map;

import lombok.Getter;
import lombok.Setter;


@Setter
public class Area {
    @Getter
    private Location[][] locations;

    public Area(int x, int y) {
        locations = new Location[x][y];
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
