package simulation.gamemap;

import simulation.entity.Entity;

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
        StringBuilder lines = new StringBuilder();
        for (int row = 0; row < gameMap.getMaxRow(); row++) {
            for (int column = 0; column < gameMap.getMaxColumn(); column++) {
                lines.append(ANSI_BLACK_BACKGROUND);
                Coordinates coordinates = new Coordinates(row, column);
                if(gameMap.isEmpty(coordinates)){
                    lines.append(EMOJI_EARTH);
                } else {
                    Entity entity = gameMap.getEntity(coordinates);
                    lines.append(entitySprite(entity));
                }
            }
            lines.append(ANSI_COLOR_RESET + "\n");
        }
        System.out.println(lines);
    }

    public String entitySprite(Entity entity) {

        return switch (entity.getClass().getSimpleName()) {
            case "Herbivore" -> EMOJI_HERBIVORE;
            case "Predator" -> EMOJI_PREDATOR;
            case "Grass" -> EMOJI_GRASS;
            case "Tree" -> EMOJI_TREE;
            case "Rock" -> EMOJI_ROCK;
            case null, default -> throw new IllegalArgumentException();
        };
    }

}
