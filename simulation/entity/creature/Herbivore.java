package simulation.entity.creature;

import simulation.entity.staticObjects.Grass;

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

    public boolean shouldEat (int rangeToGrass){
        return rangeToGrass == 1;
    }
}
