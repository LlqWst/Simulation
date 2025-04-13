package simulation;

public class InputCommands {

    public final static int START = 1;
    public final static int CONTINUE = 2;
    public final static int PAUSE = 3;
    public final static int EXIT = 4;

    public int parsInt(String input){
        int command = 0;
        try {
            command = Integer.parseInt(input);
        } catch (IllegalArgumentException e){
            System.out.println("Incorrect Input");
            return 0;
        }
        if (command < START || command > EXIT){
            System.out.println("Incorrect Input");
        }
        return command;
    }

    public void menu(){
        System.out.println("Select one of the commands:");
        System.out.println("["+ InputCommands.START +"]" + "  —  start Simulation");
        System.out.println("["+ InputCommands.CONTINUE +"]" + "  —  continue Simulation");
        System.out.println("["+ InputCommands.PAUSE +"]" + "  —  pause Simulation");
        System.out.println("["+ InputCommands.EXIT +"]" + "  —  exit Simulation");
    }
}
