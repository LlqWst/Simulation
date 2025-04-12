package simulation.entity.creature;

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

}
