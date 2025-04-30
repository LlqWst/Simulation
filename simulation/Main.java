package simulation;

import simulation.actions.*;
import simulation.entity.Entity;
import simulation.entity.creature.*;
import simulation.entity.static_objects.*;
import simulation.gamemap.GameMap;
import simulation.pathfinder.*;
import simulation.parameters.Parameters;
import simulation.simulation.Simulation;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        int speed = random.nextInt(Parameters.MIN_SPEED, Parameters.MAX_SPEED);
        int hp = random.nextInt(Parameters.MIN_HP, Parameters.MAX_HP);
        int range = random.nextInt(Parameters.MIN_RANGE, Parameters.MAX_RANGE);
        int damage = random.nextInt(Parameters.MIN_DAMAGE, Parameters.MAX_DAMAGE);
        int predatorAmount = random.nextInt(Parameters.MIN_PREDATOR, Parameters.MAX_PREDATOR);
        int herbivoreAmount = random.nextInt(Parameters.MIN_HERBIVORE, Parameters.MAX_HERBIVORE);
        int grassAmount = random.nextInt(Parameters.MIN_GRASS, Parameters.MAX_GRASS);
        int treeAmount = random.nextInt(Parameters.MIN_TREE, Parameters.MAX_TREE);
        int rockAmount = random.nextInt(Parameters.MIN_ROCK, Parameters.MAX_ROCK);

        GameMap gameMap = new GameMap();
        MoveAction makeMove = new MoveAction(gameMap);
        Supplier<Entity> supplierHerbivore = () -> new Herbivore(speed, hp, Parameters.EAT_RANGE, new AStarAlgorithm());
        Supplier<Entity> supplierPredator = () -> new Predator(speed, damage, range, new AStarAlgorithm());
        List<Actions> initActions = List.of(
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, herbivoreAmount, new SpawnEntity(gameMap, supplierHerbivore), Herbivore.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, predatorAmount, new SpawnEntity(gameMap, supplierPredator), Predator.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, grassAmount,new SpawnEntity(gameMap, Grass::new), Grass.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, treeAmount, new SpawnEntity(gameMap, Tree::new), Tree.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, rockAmount, new SpawnEntity(gameMap, Rock::new), Rock.class));
        List<Actions> turnActions = List.of(
                makeMove,
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, herbivoreAmount, new SpawnEntity(gameMap, supplierHerbivore), Herbivore.class),
                new AddNewEntities(gameMap,  Parameters.THRESHOLD_ENTITY, grassAmount, new SpawnEntity(gameMap, Grass::new), Grass.class)
        );
        Simulation simulation = new Simulation(gameMap, initActions, turnActions);
        simulation.execute();
    }

}