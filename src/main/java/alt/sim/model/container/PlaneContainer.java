package alt.sim.model.container;

import java.util.Optional;

import alt.sim.model.plane.Plane;

public interface PlaneContainer {

    /**
     * Add new plane in the collection.
     * @param plane plane to add
     */
    void addPlane(Plane plane);
    /**
     *
     * @return a plane
     */
    Optional<Plane> getPlane();
}
