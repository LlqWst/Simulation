package simulation;

import simulation.entity.*;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;

import java.util.*;

public class GameMap {

    private final int MAX_ROW;
    private final int MAX_COLUMN;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public GameMap() {
        this.MAX_COLUMN = 8;
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

    public void setEntities(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public void removeEntities(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    public Entity getEntities(Coordinates coordinates) {
        if(isEmpty(coordinates)){
            throw new IllegalArgumentException();
        }
        return entities.get(coordinates);
    }

    public boolean isEmpty(Coordinates coordinates) {
        return entities.get(coordinates) == null;
    }

    public Map<Coordinates, Entity> getAllEntities() {
        return entities;
    }

    public Map<Coordinates, Integer> getCreatures(){
        Map<Coordinates, Integer> creatures = new HashMap<>();
        for(Map.Entry<Coordinates, Entity> entity : entities.entrySet()){
            if(entity.getValue() instanceof Creature cr){
                creatures.put(entity.getKey(), cr.getId());
            }
        }
        return creatures;
    }

    public boolean isHerbivore(Coordinates coordinates){
        return getEntities(coordinates) instanceof Herbivore;
    }

    public boolean isPredator(Coordinates coordinates){
        return getEntities(coordinates) instanceof Predator;
    }

    public boolean isContainsId(Coordinates coordinates, int id){
        if(getEntities(coordinates) instanceof Creature creature){
            return creature.getId() == id;
        }
        return false;
    }

    public Coordinates getRandomEmptyCoordinates(){
        List<Coordinates> allEmptyCoordinates = new ArrayList<>();
        for (int row = 0; row < getMaxRow(); row++) {
            for (int column = 0; column < getMaxColumn(); column++) {
                Coordinates coordinates = new Coordinates (row, column);
                if (isEmpty(coordinates)){
                    allEmptyCoordinates.add(coordinates);
                }
            }
        }
        Random random = new Random();
        int randomCoordinates = random.nextInt(allEmptyCoordinates.size());
        return allEmptyCoordinates.get(randomCoordinates);
    }

}
