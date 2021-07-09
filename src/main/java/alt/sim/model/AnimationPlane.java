package alt.sim.model;

import javafx.geometry.Point2D;

/**
 *  Define the Animation class behavior.
 */
public abstract class AnimationPlane {
    protected AnimationPlane() { }

    public abstract void startAnimation();

    public abstract void setPositionAnimation(Point2D positionAnimation);
}
