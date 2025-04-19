package simulation.gameMap;

public class GameMapRenderer {

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[0;100m";
    private static final String ANSI_COLOR_RESET = "\u001B[0m";
    private static final String EMOJI_HERBIVORE = "\uD83D\uDC07";
    private static final String EMOJI_PREDATOR = "\uD83E\uDD8A";
    private static final String EMOJI_GRASS = "\uD83C\uDF40";
    private static final String EMOJI_TREE = "\uD83E\uDD66";
    private static final String EMOJI_ROCK = "⛰️";
    private static final String EMOJI_EARTH = "\uD83D\uDFEB";

    private final GameMap gameMap;

    public GameMapRenderer(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void render() {
        System.out.println();
        for (int row = 0; row < gameMap.getMaxRow(); row++) {
            StringBuilder line = new StringBuilder();
            for (int column = 0; column < gameMap.getMaxColumn(); column++) {
                line.append(ANSI_BLACK_BACKGROUND);
                line.append(entitySprite(new Coordinates(row, column)));
            }
            System.out.println(line + ANSI_COLOR_RESET);
        }
    }

    public String entitySprite(Coordinates coordinates) {

        if (gameMap.isEmpty(coordinates)) {
            return EMOJI_EARTH;
        }

        return switch (gameMap.getEntity(coordinates).getClass().getSimpleName()) {
            case "Herbivore" -> EMOJI_HERBIVORE;
            case "Predator" -> EMOJI_PREDATOR;
            case "Grass" -> EMOJI_GRASS;
            case "Tree" -> EMOJI_TREE;
            case "Rock" -> EMOJI_ROCK;
            case null, default -> throw new IllegalArgumentException();
        };
    }

}
