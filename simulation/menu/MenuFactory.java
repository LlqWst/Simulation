package simulation.menu;

import java.util.Map;

class MenuFactory {

    private MenuFactory() {}

    private static final String MENU_LINE = "-----------------MENU-------------------";
    private static final String BORDER = "----------------------------------------";

    public static String create(Map<String, String> commands) {
        StringBuilder co = new StringBuilder(MENU_LINE + "\n");
        for (String button : commands.keySet()) {
            co.append(String.format("[%s] — %s\n", button, commands.get(button)));
        }
        co.append(BORDER);
        return co.toString();
    }

}
