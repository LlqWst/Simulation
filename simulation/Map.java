package simulation;

import simulation.entity.*;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.entity.staticObjects.Grass;
import simulation.entity.staticObjects.Rock;
import simulation.entity.staticObjects.Tree;

import java.util.HashMap;

public class Map {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 10;
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();


    public Map() {
        entities.put(new Coordinates(5, 9), new Grass());
        entities.put(new Coordinates(0, 5), new Herbivore());
        entities.put(new Coordinates(9, 4), new Herbivore());
        entities.put(new Coordinates(8, 8), new Grass());
        entities.put(new Coordinates(5, 8), new Grass());
        entities.put(new Coordinates(5, 4), new Grass());
        entities.put(new Coordinates(5, 3), new Grass());
        entities.put(new Coordinates(6, 8), new Grass());
        entities.put(new Coordinates(4, 0), new Grass());
        entities.put(new Coordinates(2, 3), new Herbivore());
        entities.put(new Coordinates(2, 2), new Tree());
        entities.put(new Coordinates(1, 4), new Tree());
        entities.put(new Coordinates(1, 3), new Tree());
        entities.put(new Coordinates(0, 3), new Tree());
        entities.put(new Coordinates(1, 1), new Herbivore());
        entities.put(new Coordinates(7, 7), new Herbivore());
        entities.put(new Coordinates(0, 1), new Herbivore());
        entities.put(new Coordinates(1, 5), new Tree());
        entities.put(new Coordinates(1, 5), new Tree());
        entities.put(new Coordinates(1, 6), new Tree());
        entities.put(new Coordinates(1, 7), new Tree());
        entities.put(new Coordinates(0, 9), new Grass());
        entities.put(new Coordinates(0, 0), new Grass());
        entities.put(new Coordinates(3, 4), new Rock());
        entities.put(new Coordinates(3, 6), new Rock());
        entities.put(new Coordinates(2, 5), new Predator());
    }

    public int getMaxRow() {
        return MAX_ROW;
    }

    public int getMaxColumn() {
        return MAX_COLUMN;
    }

    public void setEntities(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public Entity getEntities(Coordinates coordinates) {

        return entities.get(coordinates);
    }

    public boolean isEmpty(Coordinates coordinates) {

        return entities.get(coordinates) == null;
    }

    public HashMap<Coordinates, Entity> getAllEntities() {
        return entities;
    }

}
