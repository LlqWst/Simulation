package simulation;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menu {

    public final static int START = 1;
    public final static int CONTINUE = 2;
    public final static int PAUSE = 3;
    public final static int EXIT = 4;

    private final AtomicBoolean isRunnable = new AtomicBoolean(true);
    private final AtomicBoolean isStart = new AtomicBoolean(false);

    Scanner scanner = new Scanner(System.in);
    private final Runnable simulationStart;
    private final Runnable simulationPause;
    private final Runnable simulationContinue;
    private final Runnable simulationExit;

    public Menu(Simulation simulation) {
        this.simulationStart = simulation::simulationStart;
        this.simulationPause = simulation::simulationPause;
        this.simulationContinue = simulation::simulationContinue;
        this.simulationExit = simulation::simulationExit;
    }

    private int parsInt(String line){
        int input = 0;
        try {
            input = Integer.parseInt(line);
        } catch (IllegalArgumentException e){
            System.out.println("Incorrect Input");
        }
        return input;
    }

    public void awaitingInput(){
        int input = 0;
        while (input < START || input > EXIT) {
            String line = scanner.nextLine();
            input = parsInt(line);
        }
        commandSelection(input);
    }

    private void commandSelection(int input){
        if (shouldStarting(input)){
            commandStart();
        } else if(shouldContinue(input)){
            commandContinue();
        } else if(shouldPause(input)){
            commandPause();
        } else if(shouldExit(input)){
            commandExit();
        }
    }

    private void commandStart(){
        isStart.set(true);
        System.out.println("Simulation is starting");
        simulationStart.run();
    }

    private void commandContinue(){
        isRunnable.set(true);
        System.out.println("Simulation is continue");
        simulationContinue.run();
    }

    private void commandPause(){
        isRunnable.set(false);
        System.out.println("Simulation on pause");
        simulationPause.run();
    }

    private void commandExit(){
        System.out.println("Simulation is over");
        simulationExit.run();
    }

    private boolean shouldStarting(int input){
        return input == START && !isStart.get();
    }

    private boolean shouldContinue(int input){
        return input == CONTINUE && isStart.get() && !isRunnable.get();
    }

    private boolean shouldPause(int input){
        return input == PAUSE && isStart.get() && isRunnable.get();
    }

    private boolean shouldExit(int input){
        return input == EXIT;
    }

    public void showMenu(){
        System.out.println("Select one of the commands:");
        System.out.println("["+ START +"]" + "  —  start Simulation");
        System.out.println("["+ CONTINUE +"]" + "  —  continue Simulation");
        System.out.println("["+ PAUSE +"]" + "  —  pause Simulation");
        System.out.println("["+ EXIT +"]" + "  —  exit Simulation");
    }
}
