package simulation.gameMap;

import simulation.entity.*;
import simulation.entity.creature.*;

import java.util.*;

public class GameMap {

    private final int MAX_ROW;
    private final int MAX_COLUMN;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public GameMap() {
        this.MAX_COLUMN = 12;
        this.MAX_ROW = 8;
    }

    public GameMap(int maxColumn, int maxRow) {
        this.MAX_COLUMN = maxColumn;
        this.MAX_ROW = maxRow;
    }

    public int getMaxRow() {
        return MAX_ROW;
    }

    public int getMaxColumn() {
        return MAX_COLUMN;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isEmpty(coordinates)) {
            throw new IllegalArgumentException();
        }
        entities.remove(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        if (isEmpty(coordinates)) {
            throw new IllegalArgumentException();
        }
        return entities.get(coordinates);
    }

    public int getCountEntity(Class<? extends Entity> clazz) {
        int counter = 0;
        for (Entity entity : entities.values()) {
            if (clazz == entity.getClass()) {
                counter++;
            }
        }
        return counter;
    }

    public boolean isEmpty(Coordinates coordinates) {
        return entities.get(coordinates) == null;
    }

    public HashMap<Coordinates, Creature> getCreatures() {
        HashMap<Coordinates, Creature> creatures = new HashMap<>();
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue() instanceof Creature creature) {
                creatures.put(entry.getKey(), creature);
            }
        }
        return creatures;
    }

    public boolean isCoordinatesContain(Coordinates coordinates, Class<? extends Entity> clazz) {
        return !isEmpty(coordinates) && getEntity(coordinates).getClass() == clazz;
    }

    public boolean isContains(Class<? extends Entity> clazz) {
        return entities.values().stream().anyMatch(value -> value.getClass() == clazz);
    }

    public boolean isAlive(Coordinates coordinates, int id) {
        if (getEntity(coordinates) instanceof Creature creature) {
            return creature.getId() == id;
        }
        return false;
    }

    public Coordinates getRandomEmptyCoordinates() {
        List<Coordinates> allEmptyCoordinates = new ArrayList<>();
        for (int row = 0; row < getMaxRow(); row++) {
            for (int column = 0; column < getMaxColumn(); column++) {
                Coordinates coordinates = new Coordinates(row, column);
                if (isEmpty(coordinates)) {
                    allEmptyCoordinates.add(coordinates);
                }
            }
        }
        Random random = new Random();
        int randomCoordinates = random.nextInt(allEmptyCoordinates.size());
        return allEmptyCoordinates.get(randomCoordinates);
    }
}
