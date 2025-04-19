package simulation.entity.creature;

// для проверки, чтобы не было повторного хода с клетки, на которой умерло существо
class CreatureIdGenerator {
    private static int nextId = 1;

    public static int generate() {
        return nextId++;
    }
}
