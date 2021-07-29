package alt.sim.model.map;

import alt.sim.Main;

public class MapImpl implements Map {

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWidth() {
        return Main.getStage().getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeight() {
        return Main.getStage().getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return Main.getStage().getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return Main.getStage().getY();
    }

}
