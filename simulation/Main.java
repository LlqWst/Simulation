package simulation;

import simulation.actions.*;
import simulation.entity.Entity;
import simulation.entity.creature.*;
import simulation.entity.static_objects.*;
import simulation.gamemap.GameMap;
import simulation.settings.Parameters;

import java.util.List;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        CreatureActivity creatureAction = new CreatureActivity(gameMap);
        MakeMove makeMove = new MakeMove(gameMap, creatureAction);
        Supplier<Entity> supplierHerbivore = () -> new Herbivore(Parameters.getRandomSpeed(), Parameters.getRandomHp(), Parameters.EAT_RANGE);
        List<Actions> initActions = getActions(gameMap, supplierHerbivore);
        List<Actions> turnActions = List.of(
                makeMove,
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Herbivore.class), new SpawnEntity(gameMap, supplierHerbivore), Herbivore.class),
                new AddNewEntities(gameMap,  Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Grass.class), new SpawnEntity(gameMap, Grass::new), Grass.class)
        );
        Simulation simulation = new Simulation(gameMap, initActions, turnActions);
        simulation.execute();
    }

    private static List<Actions> getActions(GameMap gameMap, Supplier<Entity> supplierHerbivore) {
        Supplier<Entity> supplierPredator = () -> new Predator(Parameters.getRandomSpeed(), Parameters.getRandomDamage(), Parameters.getRandomRange());
        return List.of(
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Herbivore.class), new SpawnEntity(gameMap, supplierHerbivore), Herbivore.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Predator.class), new SpawnEntity(gameMap, supplierPredator), Predator.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Grass.class),new SpawnEntity(gameMap, Grass::new), Grass.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Tree.class), new SpawnEntity(gameMap, Tree::new), Tree.class),
                new AddNewEntities(gameMap, Parameters.THRESHOLD_ENTITY, Parameters.getRandomEntityNumber(Rock.class), new SpawnEntity(gameMap, Rock::new), Rock.class)
        );
    }
}