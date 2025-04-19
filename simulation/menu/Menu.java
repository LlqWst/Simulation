package simulation.menu;

import simulation.Simulation;

import java.util.LinkedHashMap;
import java.util.Map;
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

    private String createMenu() {
        Map<Integer, String> commands = new LinkedHashMap<>();
        commands.put(START, "start Simulation");
        commands.put(CONTINUE, "continue Simulation");
        commands.put(PAUSE, "pause Simulation");
        commands.put(EXIT, "exit Simulation");
        return MenuFactory.create(commands);
    }

    public void start() {
        String menu = createMenu();
        printMenu(menu);
        awaitingInput();
    }

    private void printMenu(String menu) {
        System.out.println(menu);
    }

    private void awaitingInput() {
        while (true) {
            checkInput();
        }
    }

    private void checkInput() {
        String line = scanner.nextLine();
        Object answer = ParseIntInput.parse(line, START, EXIT);
        if (isInputInt(answer)) {
            int input = (int) answer;
            commandSelection(input);
        } else {
            printErrorMessage();
        }
    }

    private boolean isInputInt(Object answer) {
        return answer instanceof Integer;
    }

    private void printErrorMessage() {
        System.out.println("Incorrect input");
    }

    private void commandSelection(int input) {
        if (!isInitInput(input)) {
            printNotInitInput();
        } else if (shouldStarting(input)) {
            commandStart();
        } else if (shouldContinue(input)) {
            commandContinue();
        } else if (shouldPause(input)) {
            commandPause();
        } else if (shouldExit(input)) {
            commandExit();
        }
    }

    private void printNotInitInput() {
        System.out.printf("Enter %s for Start or %s for Exit\n", START, EXIT);
    }

    private boolean isInitInput(int input) {
        return isStart.get() || input == START || input == EXIT;
    }

    private void commandStart() {
        isStart.set(true);
        System.out.println("Simulation is starting");
        simulationStart.run();
    }

    private void commandContinue() {
        isRunnable.set(true);
        System.out.println("Simulation is continue");
        simulationContinue.run();
    }

    private void commandPause() {
        isRunnable.set(false);
        System.out.println("Simulation on pause");
        simulationPause.run();
    }

    private void commandExit() {
        System.out.println("Simulation is over");
        simulationExit.run();
    }

    private boolean shouldStarting(int input) {
        return input == START && !isStart.get();
    }

    private boolean shouldContinue(int input) {
        return input == CONTINUE && isStart.get() && !isRunnable.get();
    }

    private boolean shouldPause(int input) {
        return input == PAUSE && isStart.get() && isRunnable.get();
    }

    private boolean shouldExit(int input) {
        return input == EXIT;
    }

}
