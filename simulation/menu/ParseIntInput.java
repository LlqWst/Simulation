package simulation.menu;

class ParseIntInput {
    public static Object parse(String line, int min, int max) {
        int input = 0;
        try {
            input = Integer.parseInt(line);
        } catch (IllegalArgumentException e) {
            return line;
        }
        if (input >= min && input <= max) {
            return input;
        }
        return line;
    }
}
