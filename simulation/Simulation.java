package simulation;

import simulation.actions.AddNewEntities;
import simulation.actions.InitActions;
import simulation.actions.MakeMove;
import simulation.gameMap.GameMap;
import simulation.gameMap.GameMapRenderer;

import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {
    private int turnCounter = 0;
    private final AtomicBoolean isRunnable = new AtomicBoolean(true);
    private final GameMapRenderer renderer;
    private final InitActions initActions;
    private final MakeMove makeMove;
    private final AddNewEntities addNewEntities;
    private final Menu menu = new Menu(this);

    public Simulation(GameMap gameMap, InitActions initActions, MakeMove makeMove, AddNewEntities addNewEntities) {
        this.renderer = new GameMapRenderer(gameMap);
        this.initActions = initActions;
        this.makeMove = makeMove;
        this.addNewEntities = addNewEntities;
    }

    private void commandInput() {
        menu.showMenu();
        while (true) {
            menu.awaitingInput();
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

    private void initActions() {
        initActions.execute();
    }

    private void nextTurn() {
        if (isRunnable.get()) {
            printTurns();
            addNewEntities.execute();
            makeMove.execute();
            actRender();
        }
    }

    private void startSimulation() {
        initActions();
        while (true) {
            nextTurn();
        }
    }

    public void execute() {
        commandInput();
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