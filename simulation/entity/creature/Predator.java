package simulation.entity.creature;

import simulation.entity.Entity;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;
    private final int range;

    public Predator() {
        super(parameters.getRandomSpeed(), Herbivore.class);
        this.damage = parameters.getRandomDamage();
        this.range = parameters.getRandomRange();
    }

    public Predator(int speed, int damage, int range) {
        super(speed, Herbivore.class);
        this.damage = damage;
        this.range = range;
    }

    public int doDamage() {
        return this.damage;
    }

    public Coordinates makeMove(Coordinates coordinates, GameMap gameMap){
        List<Coordinates> path = super.findPath(coordinates, gameMap);
        int pathSize = path.size();
        if(path.getFirst() == coordinates){
            return path.getFirst();
        } else if (range >= pathSize) {
            return path.getLast();
        } else if(pathSize > speed){
            return path.get(speed - 1);
        } else {
            return path.get(pathSize - 2);
        }
    }

    public boolean shouldDoDamage(Coordinates coordinates, GameMap gameMap){
        return gameMap.isHerbivore(coordinates);
    }
}
