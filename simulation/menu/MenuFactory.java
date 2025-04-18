package simulation.menu;

import java.util.Map;

class MenuFactory {

    private static final String MENU_LINE = "-------------------MENU-----------------";
    private static final String BORDER = "----------------------------------------";

    public static String create(Map<Integer, String> commands){
        StringBuilder co = new StringBuilder(MENU_LINE + "\n");
        for(Integer button : commands.keySet()){
            co.append(String.format("[%d] — %s\n", button, commands.get(button)));
        }
        co.append(BORDER);
        return co.toString();
    }

}
