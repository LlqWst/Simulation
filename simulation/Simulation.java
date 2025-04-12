package simulation;



import simulation.entity.actions.AddNewEntities;
import simulation.entity.actions.InitActions;
import simulation.entity.actions.MakeMove;
import simulation.entity.creature.Herbivore;

import simulation.entity.staticObjects.Grass;

import java.util.*;

public class Simulation {
    private int turnCounter;
    private final GameMap gameMap;
    private final Scanner scanner = new Scanner(System.in);
    MapRenderer renderer;
    private final InitActions initActions;
    private final MakeMove makeMove;
    private final AddNewEntities addNewEntities;

    public Simulation(GameMap gameMap, InitActions initActions, MakeMove makeMove, AddNewEntities addNewEntities) {
        this.turnCounter = 0;
        renderer = new MapRenderer(gameMap);
        this.gameMap = gameMap;
        this.initActions = initActions;
        this.makeMove = makeMove;
        this.addNewEntities = addNewEntities;
    }

public void startSimulation(){
    initActions.execute();
    renderer.render();
    do {
        if(!gameMap.getAllEntities().containsValue(new Herbivore()) || !gameMap.getAllEntities().containsValue(new Grass())){
            addNewEntities.execute();
            renderer.render();
        }
        makeMove.execute();
        renderer.render();

   // } while (scanner.nextInt() != 3);
        } while (true);
}

    private void actRenderAddSomeEntities(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
