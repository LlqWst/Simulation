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
    protected final int range;
    protected final Class<? extends Entity> goal;
    protected final PathFinder pathFinder;

    public Creature(int speed, int distance, Class<? extends Entity> goal, PathFinder pathFinder) {
        this.speed = speed;
        this.range = distance;
        this.goal = goal;
        this.pathFinder = pathFinder;
    }

    abstract public void makeMove(GameMap gameMap);

    protected void move(GameMap gameMap, Coordinates start, Coordinates end) {
        gameMap.removeEntity(start);
        gameMap.setEntity(end, this);
    }

    public Class<? extends Entity> getGoal() throws NullPointerException {
        return this.goal;
    }

    protected boolean isDead(int hp) {
        return hp <= 0;
    }

    protected boolean isReachable(List<Coordinates> path){
        return !path.isEmpty();
    }

    protected boolean getGoal(int pathSize, int range){
        return pathSize <= range;
    }

}
