package simulation;

import simulation.actions.AddNewEntities;
import simulation.actions.InitActions;
import simulation.actions.MakeMove;
import simulation.gameMap.GameMap;


public class Main {
    public static void main(String[] args){
        GameMap gameMap = new GameMap();
        InitActions initActions = new InitActions(gameMap);
        MakeMove makeMove = new MakeMove(gameMap);
        AddNewEntities addNewEntities = new AddNewEntities(gameMap);
        Simulation simulation = new Simulation(gameMap, initActions, makeMove, addNewEntities);
        simulation.startSimulation();
    }
}