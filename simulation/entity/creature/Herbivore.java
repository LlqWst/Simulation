package simulation.entity.creature;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.entity.staticObjects.Grass;

import java.util.List;

public class Herbivore extends Creature{

    private final int hp;

    public Herbivore() {
        super(2, new Grass());
        this.hp = 12;
    }

    public Herbivore(int hp, int id) {
        super(2, new Grass());
        this.hp = hp;
        this.id = id;
    }

    public int getHp() {
        return this.hp;
    }

    public Coordinates action(Coordinates coordinates, GameMap gameMap){
        List<Coordinates> path = super.makeMove(coordinates, gameMap);
        int pathSize = path.size();
        if(shouldEat(pathSize) || path.getFirst() == coordinates){
            return path.getFirst();
        } else if(pathSize > speed){
            return path.get(speed - 1);
        } else {
            return path.get(pathSize - 2);
        }
    }

    private boolean shouldEat (int rangeToGrass){
        return rangeToGrass == 1;
    }

}
