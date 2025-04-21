package simulation.entity.creature;

import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;
import simulation.entity.Entity;

import java.util.List;

public abstract class Creature extends Entity {

    private final int speed;
    private final Class<? extends Entity> goal;

    protected static final int TURN_TO_INDEX = 1;
    protected static final int STOP_BEFORE_GOAL = 1;

    public Creature(int speed, Class<? extends Entity> goal) {
        this.speed = speed;
        this.goal = goal;
    }

    protected int getSpeed(){
        return this.speed;
    }

    public Class<? extends Entity> getGoal() throws NullPointerException {
        return this.goal;
    }

    protected boolean isNotReachable(List<Coordinates> path){
        return path.isEmpty();
    }

    abstract public Coordinates makeMove(Coordinates coordinates, GameMap gameMap);
}
