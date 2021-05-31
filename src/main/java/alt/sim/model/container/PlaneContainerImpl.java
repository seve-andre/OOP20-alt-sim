package alt.sim.model.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alt.sim.model.plane.Plane;

public class PlaneContainerImpl implements PlaneContainer {

    private List<Optional<Plane>> planes;

    public PlaneContainerImpl() {
        planes = new ArrayList<>();
    }

    @Override
    public void addPlane(final Plane plane) {
        Optional<Plane> optionalPlane = Optional.ofNullable(plane);
        planes.add(optionalPlane);
    }

    @Override
    public Optional<Plane> getPlane() {
        return Optional.ofNullable(null); //to develop
    }

}
