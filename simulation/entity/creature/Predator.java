package simulation.entity.creature;

import simulation.Coordinates;
import simulation.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;
    private final int range;

    public Predator() {
        super(1, new Herbivore());
        this.damage = 4;
        this.range = 1;
    }

    public int getRange() {
        return this.range;
    }

    public int doDamage() {
        return this.damage;
    }

    public Coordinates move(Coordinates coordinates, GameMap gameMap){
        List<Coordinates> path = super.makeMove(coordinates, gameMap);
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
