package ua.javarush.island.map;

public class Area {
    private final Location[][] area;

    public Area(int x, int y) {
        area = new Location[x][y];
    }

    public Location[][] getArea() {
        return area;
    }
}
