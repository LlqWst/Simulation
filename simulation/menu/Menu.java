package simulation.menu;

import simulation.Simulation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menu {

    public final static String START = "1";
    public final static String CONTINUE = "2";
    public final static String PAUSE = "3";
    public final static String EXIT = "4";

    private final AtomicBoolean isRunnable = new AtomicBoolean(true);
    private final AtomicBoolean isStart = new AtomicBoolean(false);

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
        Map<String, String> commands = new LinkedHashMap<>();
        commands.put(START, "start Simulation");
        commands.put(CONTINUE, "continue Simulation");
        commands.put(PAUSE, "pause Simulation");
        commands.put(EXIT, "exit Simulation");
        return MenuFactory.create(commands);
    }

    private StringSelectDialog createDialog(){
        String menu = createMenu();
        List<String> keys = List.of(START, CONTINUE, PAUSE, EXIT);
        String failMessage = "Неизвестная команда";
        return new StringSelectDialog(menu, failMessage, keys);
    }

    public void start() {
        StringSelectDialog dialog = createDialog();
        while (true) {
            checkInput(dialog);
        }
    }

    private void checkInput(StringSelectDialog dialog) {
        String command = dialog.input();
        commandSelection(command);
    }

    private void commandSelection(String input) {
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

    private boolean isInitInput(String input) {
        return isStart.get() || input.equals(START) || input.equals(EXIT);
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

    private boolean shouldStarting(String input) {
        return input.equals(START) && !isStart.get();
    }

    private boolean shouldContinue(String input) {
        return input.equals(CONTINUE) && isStart.get() && !isRunnable.get();
    }

    private boolean shouldPause(String input) {
        return input.equals(PAUSE) && isStart.get() && isRunnable.get();
    }

    private boolean shouldExit(String input) {
        return input.equals(EXIT);
    }

}
