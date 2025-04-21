package simulation.entity.creature;

import simulation.PathFinder;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;
    private final int damageRange;

    public Predator(int speed, int damage, int range) {
        super(speed, Herbivore.class);
        this.damage = damage;
        this.damageRange = range;
    }

    public int getDamage() throws NullPointerException {
        return this.damage;
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, GameMap gameMap) {
        List<Coordinates> path = new PathFinder(coordinates, gameMap, getGoal()).findPath();
        int pathSize = path.size();
        if (isNotReachable(path)) {
            return coordinates;
        } else if (damageRange >= pathSize) {
            return path.getLast();
        } else if (pathSize > getSpeed()) {
            return path.get(getSpeed()- TURN_TO_INDEX);
        } else {
            return path.get(pathSize - TURN_TO_INDEX - STOP_BEFORE_GOAL);
        }
    }

    public boolean canDamage(Coordinates coordinates, GameMap gameMap) {
        return gameMap.isCoordinatesContains(coordinates, getGoal());
    }
}
