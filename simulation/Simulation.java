package simulation;

import simulation.menu.Menu;
import simulation.actions.Actions;
import simulation.gameMap.GameMap;
import simulation.gameMap.GameMapRenderer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {
    private int turnCounter = 0;
    private final AtomicBoolean isRunnable = new AtomicBoolean(true);
    private final GameMapRenderer renderer;
    private final Menu menu = new Menu(this);
    private final List<Actions> turnActions;
    private final List<Actions> initActions;

    public Simulation(GameMap gameMap, List<Actions> initActions, List<Actions> turnActions) {
        this.renderer = new GameMapRenderer(gameMap);
        this.turnActions = turnActions;
        this.initActions = initActions;
    }

    public void execute() {
        while (true) {
            menu.start();
        }
    }

    public void simulationStart() {
        simulation();
    }

    public void simulationContinue() {
        isRunnable.set(true);
    }

    public void simulationPause() {
        isRunnable.set(false);
    }

    public void simulationExit() {
        System.exit(0);
    }

    private void simulation() {
        Thread simulationRunnable = new Thread(this::startSimulation);
        simulationRunnable.setUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("An exception was caught during simulation " + exception.getMessage());
            System.exit(1);
        });
        simulationRunnable.setDaemon(true);
        simulationRunnable.start();
    }

    private void startSimulation() {
        doInitActions();
            while (true) {
                nextTurn();
            }
    }

    private void doInitActions() {
        for (Actions action : initActions){
            action.execute();
        }
    }

    private void nextTurn() {
        if (isRunnable.get()) {
            actRender();
            printTurns();
            doTurnActions();
        }
    }

    private void doTurnActions(){
        for(Actions action : turnActions){
            action.execute();
        }
    }

    private void printTurns() {
        System.out.println("Turn:" + ++turnCounter);
    }

    private void actRender() {
        renderer.render();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}