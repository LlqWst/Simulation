package simulation.actions;

import simulation.Parameters;

public abstract class Actions {

    protected static final Parameters parameters = new Parameters();

    public abstract void execute();
}
