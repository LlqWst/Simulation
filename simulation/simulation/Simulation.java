package simulation.simulation;

import simulation.menu.Menu;
import simulation.actions.Actions;
import simulation.gamemap.GameMap;
import simulation.gamemap.GameMapRenderer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {
    private int turnCounter = 0;
    private final AtomicBoolean isRunnable;
    private final GameMapRenderer renderer;
    private final Menu menu;
    private final List<Actions> turnActions;
    private final List<Actions> initActions;

    public Simulation(GameMap gameMap, List<Actions> initActions, List<Actions> turnActions) {
        this.renderer = new GameMapRenderer(gameMap);
        this.turnActions = turnActions;
        this.initActions = initActions;
        this.menu = new Menu(this);
        this.isRunnable = new AtomicBoolean(true);
    }

    public void execute() {
        menu.start();
    }

    public void simulationStart() {
        start();
    }

    public void simulationContinue() {
        synchronized (this) {
            isRunnable.set(true);
            this.notifyAll();
        }
    }

    public void simulationPause() {
        synchronized (this) {
            isRunnable.set(false);
        }
    }

    public void simulationExit() {
        System.exit(0);
    }

    private void start() {
        Thread simulationRunnable = new Thread(this::simulation);
        simulationRunnable.setUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("An exception was caught during simulation ");
            exception.printStackTrace();
            System.exit(1);
        });
        simulationRunnable.setDaemon(true);
        simulationRunnable.start();
    }

    private void simulation() {
        doInitActions();
        while (true) {
            simulationStatus();
            nextTurn();
        }
    }

    private void doInitActions() {
        for (Actions action : initActions) {
            action.execute();
        }
    }

    private void simulationStatus() {
        synchronized (this) {
            try {
                while (!isRunnable.get()) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void nextTurn() {
        printTurns();
        doTurnActions();
        actRender();
    }

    private void actRender() {
        renderer.render();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void printTurns() {
        System.out.printf("      == Turn: %d ==\n", ++turnCounter);
    }

    private void doTurnActions() {
        for (Actions action : turnActions) {
            action.execute();
        }
    }

}