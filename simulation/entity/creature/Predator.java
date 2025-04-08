package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.*;

import java.util.List;

public class Predator extends Creature {

    private static final int damage = 4;
    private static final int speed = 2;
    private static final Entity goal = new Herbivore();
    private static final int range = 1;

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public List<Coordinates> makeMove(Coordinates coordinates, Map map) {
        return new FindPath(coordinates, map, goal).findPath();
    }

    public int getRange() {
        return range;
    }

    public int doDamage() {
        return damage;
    }
}
