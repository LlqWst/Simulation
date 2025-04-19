package simulation.entity.creature;

import simulation.Parameters;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;
import simulation.entity.Entity;
import simulation.PathFinder;

import java.util.List;

public abstract class Creature extends Entity {

    protected int speed;
    protected Class<? extends Entity> goal;
    protected int id = CreatureIdGenerator.generate();
    protected static final Parameters parameters = new Parameters(); // для всегда уникальных существ

    protected static final int TURN_TO_INDEX = 1;
    protected static final int RANGE_TO_GOAL = 1;

    public Creature(int speed, Class<? extends Entity> goal) {
        this.speed = speed;
        this.goal = goal;
    }

    public int getId() throws NullPointerException {
        return this.id;
    }

    public Class<? extends Entity> getGoal() throws NullPointerException {
        return this.goal;
    }

    protected boolean isNoGoal(List<Coordinates> path, Coordinates coordinates){
        return path.getFirst() == coordinates;
    }

    abstract public Coordinates makeMove(Coordinates coordinates, GameMap gameMap);

    protected List<Coordinates> findPath(Coordinates coordinates, GameMap gameMap) {
        return new PathFinder(coordinates, gameMap, goal).findPath();
    }

}
