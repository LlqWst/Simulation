package simulation.entity.creature;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.IdGenerator;
import simulation.entity.Entity;
import simulation.PathFinder;

import java.util.List;

public abstract class Creature extends Entity {

    protected int speed;
    protected Entity goal;
    protected int id = IdGenerator.generateId();

    public Creature(int speed, Entity goal) {
        this.speed = speed;
        this.goal = goal;
    }

    public int getId() {
        return this.id;
    }

    abstract public Coordinates makeMove(Coordinates coordinates, GameMap gameMap);

    protected List<Coordinates> findPath(Coordinates coordinates, GameMap gameMap){
        return new PathFinder(coordinates, gameMap, goal).findPath();
    }

}
