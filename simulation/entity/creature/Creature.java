package simulation.entity.creature;

import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;
import simulation.entity.Entity;
import simulation.pathfinder.PathFinder;

import java.util.List;

public abstract class Creature extends Entity {

    protected static final int TURN_TO_INDEX = 1;
    protected static final int STOP_BEFORE_GOAL = 1;

    protected final int speed;
    protected final Class<? extends Entity> goal;
    protected final PathFinder pathFinder;

    public Creature(int speed, Class<? extends Entity> goal, PathFinder pathFinder) {
        this.speed = speed;
        this.goal = goal;
        this.pathFinder = pathFinder;
    }

    public Class<? extends Entity> getGoal() throws NullPointerException {
        return this.goal;
    }

    protected boolean isNotReachable(List<Coordinates> path){
        return path.isEmpty();
    }

    abstract public Coordinates makeMove(GameMap gameMap);
}
