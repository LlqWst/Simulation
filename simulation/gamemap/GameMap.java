package simulation.gamemap;

import simulation.entity.*;

import java.util.*;

public class GameMap {

    private final static int DEFAULT_WIDTH = 8;
    private final static int DEFAULT_HEIGHT = 12;
    private final int maxRow;
    private final int maxColumn;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public GameMap() {
        this.maxRow = DEFAULT_WIDTH;
        this.maxColumn = DEFAULT_HEIGHT;
    }

    public GameMap(int maxRow, int maxColumn) {
        if (maxRow <= 0 || maxColumn <= 0) {
            throw new IllegalArgumentException("maxRow and maxColumn must be greater than 0");
        }
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        if(entity == null || !isValidCoordinate(coordinates)){
            throw new IllegalArgumentException();
        }
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isEmpty(coordinates) || !isValidCoordinate(coordinates)) {
            throw new IllegalArgumentException();
        }
        entities.remove(coordinates);
    }

    public List<Entity> getAllEntities(){
        return new ArrayList<>(entities.values());
    }

    public Coordinates getCoordinates (Entity entity){
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue().equals(entity)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException();
    }

    public Entity getEntity(Coordinates coordinates) {
        if (!isEmpty(coordinates) && isValidCoordinate(coordinates)) {
            return entities.get(coordinates);
        }
        throw new IllegalArgumentException();
    }

    public boolean isValidCoordinate(Coordinates coordinates){
        return coordinates.row() >= 0
                && coordinates.column() >= 0
                && coordinates.row() <= getMaxRow() - 1
                && coordinates.column() <= getMaxColumn() - 1;
    }

    public boolean isEmpty(Coordinates coordinates) {
        return isValidCoordinate(coordinates)
                && entities.get(coordinates) == null;
    }

}
