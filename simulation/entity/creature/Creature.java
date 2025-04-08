package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

import java.util.List;

public abstract class Creature extends Entity {

    private static int speed;
    private static Entity goal;
    public List<Coordinates> makeMove(Coordinates coordinates, Map map){
        return new FindPath(coordinates, map, goal).findPath();
    }
    public int getSpeed(){
        return speed;
    }

}
