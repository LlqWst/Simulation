package simulation;

import simulation.actions.AddNewEntities;
import simulation.actions.InitActions;
import simulation.actions.MakeMove;
import simulation.gameMap.GameMap;
import simulation.gameMap.GameMapRenderer;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {
    private int turnCounter;
    private final AtomicBoolean isRunnable = new AtomicBoolean(true);
    private final AtomicBoolean isStart = new AtomicBoolean(false);
    private final AtomicBoolean isEnd = new AtomicBoolean(false);
    private final GameMapRenderer renderer;
    private final InitActions initActions;
    private final MakeMove makeMove;
    private final AddNewEntities addNewEntities;
    private final InputCommands commands;

    public Simulation(GameMap gameMap, InitActions initActions, MakeMove makeMove, AddNewEntities addNewEntities) {
        this.turnCounter = 0;
        this.renderer = new GameMapRenderer(gameMap);
        this.initActions = initActions;
        this.makeMove = makeMove;
        this.addNewEntities = addNewEntities;
        this.commands = new InputCommands();
    }

    private void commandInput() {
        Scanner scanner = new Scanner(System.in);
        Thread inputThread = new Thread(() -> {
            while (true) {
               String input = scanner.nextLine();
               int command = commands.parsInt(input);
               if (isStarting(command)) {
                   simulationStart();
               } else if (isContinue(command)) {
                   simulationContinue();
               } else if (isPause(command)){
                   simulationPause();
               } else if (isExit(command)) {
                   simulationExit();
               }
            }
        });
        inputThread.setUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("An exception was caught during input " + exception.getMessage());
            System.exit(1);
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    private void simulationStart(){
        isStart.set(true);
        System.out.println("Simulation is starting");
    }

    private boolean isStarting(int command){
        return !isStart.get() && command == InputCommands.START;
    }

    private boolean isContinue(int command) {
        return isStart.get() && !isRunnable.get() && command == InputCommands.CONTINUE;
    }

    private boolean isPause(int command) {
        return isStart.get() && isRunnable.get() && command == InputCommands.PAUSE;
    }

    private boolean isExit(int command) {
        return command == InputCommands.EXIT;
    }

    private void simulationContinue(){
        isRunnable.set(true);
        System.out.println("Simulation is continue");
    }

    private void simulationPause(){
        isRunnable.set(false);
        System.out.println("Simulation on pause");
    }

    private void simulationExit(){
        isEnd.set(true);
        System.out.println("Simulation is over");
    }

    private void awaitingInitialInput(){
        commandInput();
        while (!isStart.get() && !isEnd.get()){
        }
    }

    private void initActions(){
        if(isStart.get()) {
            initActions.execute();
        }
    }

    private void nextTurn(){
        if(isStart.get() && isRunnable.get()) {
            printTurns();
            addNewEntities.execute();
            makeMove.execute();
            actRender();
        }
    }

    public void startSimulation(){
        commands.menu();
        awaitingInitialInput();
        initActions();
        while (!isEnd.get()){
            nextTurn();
        }
    }

    private void printTurns(){
        System.out.println("Turn:" + ++turnCounter);
    }

    private void actRender(){
        renderer.render();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
