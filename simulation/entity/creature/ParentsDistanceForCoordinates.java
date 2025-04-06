package simulation.entity.creature;

import simulation.Coordinates;

import java.util.Objects;

public class ParentsDistanceForCoordinates {

    private final int distance;
    private final Coordinates parents;

    public ParentsDistanceForCoordinates(int distance, Coordinates parents) {
        this.distance = distance;
        this.parents = parents;
    }

    public int getDistance() {
        return distance;
    }

    public Coordinates getParents() {
        return parents;
    }

}
