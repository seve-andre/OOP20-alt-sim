package alt.sim.model.map;

import alt.sim.Main;
import alt.sim.view.mapchoice.GameMap;

public class MapImpl implements Map {

    private GameMap map;

    public MapImpl(final GameMap map) {
        this.map = map;
    }

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
