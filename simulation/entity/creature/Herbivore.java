package simulation.entity.creature;

import simulation.gamemap.GameMapUtils;
import simulation.pathfinder.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;
import simulation.entity.static_objects.Grass;

import java.util.List;

public class Herbivore extends Creature {

    private int hp;
    private final int eatRange;
    public Herbivore(int speed, int hp, int eatRange, PathFinder pathFinder) {
        super(speed, Grass.class, pathFinder);
        this.hp = hp;
        this.eatRange = eatRange;
    }

    public int getHp() throws NullPointerException {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public Coordinates makeMove(GameMap gameMap) {
        Coordinates start = gameMap.getCoordinates(this);
        List<Coordinates> path = pathFinder.find(gameMap, start, goal);
        int pathSize = path.size();
        if(isNotReachable(path)){
            return start;
        } else if (pathSize == eatRange) {
            return path.getFirst();
        } else if (pathSize > speed) {
            return path.get(speed - TURN_TO_INDEX);
        } else {
            return path.get(pathSize - TURN_TO_INDEX - STOP_BEFORE_GOAL);
        }
    }

    public boolean canEat(Coordinates coordinates, GameMap gameMap) {
        return GameMapUtils.isCoordinatesContains(gameMap, coordinates, goal);
    }

}
