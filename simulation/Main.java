package simulation;

import simulation.entity.actions.AddNewEntities;
import simulation.entity.actions.InitActions;
import simulation.entity.actions.MakeMove;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        InitActions initActions = new InitActions(gameMap);
        MakeMove makeMove = new MakeMove(gameMap);
        AddNewEntities addNewEntities = new AddNewEntities(gameMap);
        Simulation simulation = new Simulation(gameMap, initActions, makeMove, addNewEntities);
        simulation.startSimulation();
    }
}