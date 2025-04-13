package simulation;

import java.util.Random;

public class Parameters {
    Random random = new Random();
    private final int MAX_HERBIVORE = 6;
    private final int MIN_HERBIVORE = 4;
    private final int MAX_PREDATOR = 3;
    private final int MIN_PREDATOR = 2;
    private final int MAX_GRASS = 8;
    private final int MIN_GRASS = 5;
    private final int MAX_TREE = 15;
    private final int MIN_TREE = 12;
    private final int MAX_ROCK = 15;
    private final int MIN_ROCK = 12;
    private final int MAX_HP = 12;
    private final int MIN_HP = 6;
    private final int MAX_SPEED = 2;
    private final int MIN_SPEED = 1;
    private final int MAX_RANGE = 2;
    private final int MIN_RANGE = 1;
    private final int MAX_DAMAGE = 4;
    private final int MIN_DAMAGE = 2;


    public int getDamage(){
        return random.nextInt(MIN_DAMAGE, MAX_DAMAGE);
    }

    public int getHp(){
        return random.nextInt(MIN_HP, MAX_HP);
    }

    public int getSpeed(){
        return random.nextInt(MIN_SPEED, MAX_SPEED);
    }

    public int getRange(){
        return random.nextInt(MIN_RANGE, MAX_RANGE);
    }

    public int getHerbivoreNumber(){
        return random.nextInt(MIN_HERBIVORE, MAX_HERBIVORE);
    }

    public int getPredatorNumber(){
        return random.nextInt(MIN_PREDATOR, MAX_PREDATOR);
    }

    public int getGrassNumber(){
        return random.nextInt(MIN_GRASS, MAX_GRASS);
    }

    public int getTreeNumber(){
        return random.nextInt(MIN_TREE, MAX_TREE);
    }

    public int getRockNumber(){
        return random.nextInt(MIN_ROCK, MAX_ROCK);
    }

}
