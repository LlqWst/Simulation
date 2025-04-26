package simulation.pathfinder;

import simulation.gamemap.Coordinates;

import java.util.Objects;

record Node(Coordinates coordinates, Coordinates parent, int pathLength, int cellAmount) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }
}
