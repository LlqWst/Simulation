package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.*;
import simulation.entity.staticObjects.Grass;

import java.util.List;

public class Herbivore extends Creature {

    private int hp;
    private static final int speed = 2;
    private static final Entity goal = new Grass();

    public Herbivore() {
        this.hp = 12;
    }

    public Herbivore(int hp) {
        this.hp = hp;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public int setHp(int hp) {
        return this.hp = hp;
    }

    @Override
    public List<Coordinates> makeMove(Coordinates coordinates, Map map) {
        return new FindPath(coordinates, map, goal).findPath();
    }

}
