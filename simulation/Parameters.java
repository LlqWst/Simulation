package simulation;

import simulation.entity.Entity;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.entity.staticObjects.Grass;
import simulation.entity.staticObjects.Rock;
import simulation.entity.staticObjects.Tree;

import java.util.Random;

public class Parameters {
    Random random = new Random();
    private static final int MAX_HERBIVORE = 6;
    private static final int MIN_HERBIVORE = 4;
    private static final int MAX_PREDATOR = 3;
    private static final int MIN_PREDATOR = 2;
    private static final int MAX_GRASS = 8;
    private static final int MIN_GRASS = 5;
    private static final int MAX_TREE = 15;
    private static final int MIN_TREE = 12;
    private static final int MAX_ROCK = 15;
    private static final int MIN_ROCK = 12;
    private static final int MAX_HP = 12;
    private static final int MIN_HP = 6;
    private static final int MAX_SPEED = 2;
    private static final int MIN_SPEED = 1;
    private static final int MAX_RANGE = 2;
    private static final int MIN_RANGE = 1;
    private static final int MAX_DAMAGE = 4;
    private static final int MIN_DAMAGE = 2;
    public static final int THRESHOLD_ENTITY = 2;

    public int getRandomDamage() {
        return random.nextInt(MIN_DAMAGE, MAX_DAMAGE);
    }

    public int getRandomHp() {
        return random.nextInt(MIN_HP, MAX_HP);
    }

    public int getRandomSpeed() {
        return random.nextInt(MIN_SPEED, MAX_SPEED);
    }

    public int getRandomRange() {
        return random.nextInt(MIN_RANGE, MAX_RANGE);
    }

    public int getRandomHerbivoreNumber() {
        return random.nextInt(MIN_HERBIVORE, MAX_HERBIVORE);
    }

    public int getRandomPredatorNumber() {
        return random.nextInt(MIN_PREDATOR, MAX_PREDATOR);
    }

    public int getRandomGrassNumber() {
        return random.nextInt(MIN_GRASS, MAX_GRASS);
    }

    public int getRandomTreeNumber() {
        return random.nextInt(MIN_TREE, MAX_TREE);
    }

    public int getRandomRockNumber() {
        return random.nextInt(MIN_ROCK, MAX_ROCK);
    }

    public int getRandomEntityNumber(Class<? extends Entity> clazz) {
        if (clazz == Grass.class) return getRandomGrassNumber();
        else if (clazz == Herbivore.class) return getRandomHerbivoreNumber();
        else if (clazz == Rock.class) return getRandomRockNumber();
        else if (clazz == Tree.class) return getRandomTreeNumber();
        else if (clazz == Predator.class) return getRandomPredatorNumber();
        else throw new IllegalArgumentException();
    }
}
