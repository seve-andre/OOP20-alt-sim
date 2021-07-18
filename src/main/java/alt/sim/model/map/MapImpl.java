package alt.sim.model.map;

import alt.sim.Main;
import alt.sim.view.mapchoice.GameMap;

public class MapImpl implements Map {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return (int) Main.getStage().getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return (int) Main.getStage().getHeight();
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
