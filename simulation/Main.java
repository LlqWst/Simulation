package simulation;

import simulation.actions.*;
import simulation.entity.creature.*;
import simulation.entity.staticObjects.*;
import simulation.gameMap.GameMap;

import java.util.List;


public class Main {
    public static void main(String[] args){
        Parameters parameters = new Parameters();
        GameMap gameMap = new GameMap();
        MakeMove makeMove = new MakeMove(gameMap);
        List<Actions> initActions = List.of(
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Herbivore::new), Herbivore.class),
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Predator::new), Predator.class),
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Grass::new), Grass.class),
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Tree::new), Tree.class),
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Rock::new), Rock.class)
        );
        List<Actions> turnActions = List.of(
                makeMove,
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Herbivore::new), Herbivore.class),
                new AddNewEntities(gameMap, parameters, new SpawnCreature(gameMap, Grass::new), Grass.class)
        );
        Simulation simulation = new Simulation(gameMap, initActions, turnActions);
        simulation.execute();
    }
}