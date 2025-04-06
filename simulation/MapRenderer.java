package simulation;

public class MapRenderer {

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[0;100m";
    private static final String ANSI_COLOR_RESET = "\u001B[0m";
    private static final String EMOJI_HERBIVORE = "\uD83D\uDC07";
    private static final String EMOJI_PREDATOR = "\uD83E\uDD8A";
    private static final String EMOJI_GRASS = "\uD83C\uDF40";
    private static final String EMOJI_TREE = "\uD83E\uDD66";
    private static final String EMOJI_ROCK = "⛰️";
    private static final String EMOJI_EARTH = "\uD83D\uDFEB";

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
            return EMOJI_EARTH;
        }

        return switch (map.getEntities(coordinates).getClass().getSimpleName()) {
            case "Herbivore" -> EMOJI_HERBIVORE;
            case "Predator" -> EMOJI_PREDATOR;
            case "Grass" -> EMOJI_GRASS;
            case "Tree" -> EMOJI_TREE;
            case "Rock" -> EMOJI_ROCK;
            default -> EMOJI_ROCK;
        };
    }

}
