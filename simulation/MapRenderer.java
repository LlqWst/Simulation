package simulation;

public class MapRenderer {

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[0;100m";
    private static final String ANSI_COLOR_RESET = "\u001B[0m";
    private final Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render(){
        System.out.println();
        for (int row = 0; row < map.getMaxRow(); row++) {
            String line = "";
            for (int column = 0; column < map.getMaxColumn(); column++) {
                line += ANSI_BLACK_BACKGROUND;
                line += entitySprite(new Coordinates(row, column));
            }
            System.out.println(line + ANSI_COLOR_RESET);
        }
    }

    public String entitySprite(Coordinates coordinates){

        if(map.isEmpty(coordinates)){
            return "_";
        }

        return switch (map.getEntities(coordinates).getClass().getSimpleName()) {
            case "Herbivore" -> "H";
            case "Grass" -> "G";
            case "Tree" -> "T";
            case "Rock" -> "R";
           // case "Predator" -> "\uD83D\uDC7A";
            default -> "";
        };
    }

}
