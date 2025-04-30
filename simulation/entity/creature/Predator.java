package simulation.entity.creature;

import simulation.gamemap.GameMapUtils;
import simulation.pathfinder.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;
    private final int damageRange;

    public Predator(int speed, int damage, int range, PathFinder pathFinder) {
        super(speed, Herbivore.class, pathFinder);
        this.damage = damage;
        this.damageRange = range;
    }

    public int getDamage() throws NullPointerException {
        return this.damage;
    }

    @Override
    public Coordinates makeMove(GameMap gameMap) {
        Coordinates start = gameMap.getCoordinates(this);
        List<Coordinates> path = pathFinder.find(gameMap, start, goal);
        int pathSize = path.size();
        if (isNotReachable(path)) {
            return start;
        } else if (damageRange >= pathSize) {
            return path.getLast();
        } else if (pathSize > speed) {
            return path.get(speed - TURN_TO_INDEX);
        } else {
            return path.get(pathSize - TURN_TO_INDEX - STOP_BEFORE_GOAL);
        }
    }

    public boolean canDamage(Coordinates coordinates, GameMap gameMap) {
        return GameMapUtils.isCoordinatesContains(gameMap, coordinates, goal);
    }
}
