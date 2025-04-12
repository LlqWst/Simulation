package simulation.entity.creature;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.entity.Entity;

import java.util.List;

public abstract class Creature extends Entity {

    protected int speed;
    protected Entity goal;

    protected int id;

    public Creature(int speed, Entity goal) {
        this.speed = speed;
        this.goal = goal;
        this.id = IdGenerator.generateId();
    }

    public int getId() {
        return this.id;
    }

    public List<Coordinates> makeMove(Coordinates coordinates, GameMap gameMap){
        return new FindPath(coordinates, gameMap, goal).findPath();
    }

    public int getSpeed(){
        return this.speed;
    }

    public Entity getGoal(){
        return this.goal;
    }


}
