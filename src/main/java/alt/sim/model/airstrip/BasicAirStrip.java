package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.Seaside;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {
    private Rectangle landingBoxLeft;
    private Rectangle landingBoxRight;
    private Seaside transitionSeaside;
    //private List<plane> planes;
    //private List<plane> planesToRemove;
    private int score;

    public BasicAirStrip(final String url) {
        super(url);
    }

    public BasicAirStrip(final String url, final Seaside transitionSeaside) {
        super(url, transitionSeaside);
        this.transitionSeaside = transitionSeaside;
        //this.planes = this.transitionSeasideFix.getPlanes();
        //this.planesToRemove = new LinkedList<>();
        this.score = 0;
    }

    @Override
    public Rectangle getLandSpot() {
        // TODO calculate land spot
        return null;
    }

    /*@Override
    public void acceptPlane(final Plane plane) {
        if (checkCollision(plane)) {
            super.setStatus(AirStripStatus.BUSY);
            plane.getLandingAnimation().play();
            //TODO: super.setScore(UserImpl.getUser(), 50);
            System.out.println("Plane landing...");
        }
        super.setStatus(AirStripStatus.FREE);
    }*/

    //TO-DO: da eliminare una volta terminato i Test
  /*  @Override
    public void acceptPlane(final Plane plane) {
        if (checkCollision(plane) && !plane.isLanded()) {
            plane.setState(State.LANDED);
            super.setStatus(AirStripStatus.BUSY);

            plane.getLandingAnimation().play();
            plane.removedObservableStateListener();
            //TO-DO planeRemoves(plane)
            //planesToRemove.add(plane);
            //transitionSeasideFix.removePlanes(planesToRemove);
            //planes.removeAll(planesToRemove);
            System.out.println("Plane" + plane.hashCode() + " landing...");
            transitionSeaside.removePlane(plane);
            return true;
        }
        super.setStatus(AirStripStatus.FREE);
        return false;
    }*/

    @Override
    public boolean acceptPlane(final Plane plane) {
        if (checkCollision(plane) && !plane.isLanded()) {
            plane.setState(State.LANDED);
            super.setStatus(AirStripStatus.BUSY);

            plane.getLandingAnimation().play();
            plane.removedObservableStateListener();
            //TO-DO planeRemoves(plane)
            //planesToRemove.add(plane);
            //transitionSeaside.removePlanes(plane);
            //planes.removeAll(planesToRemove);
            System.out.println("Plane" + plane.hashCode() + " landing...");
            transitionSeaside.removePlane(plane);
            return true;
        }
        super.setStatus(AirStripStatus.FREE);
        return false;
    }

    //TO-DO: da eliminare una volta terminato i Test
    private boolean checkCollision(final Plane plane) {
        Bounds monitoredPlaneBounds = plane.getSprite().getBoundsInParent();

        if (monitoredPlaneBounds.intersects(landingBoxLeft.getBoundsInParent())
                || monitoredPlaneBounds.intersects(landingBoxRight.getBoundsInParent())) {
            this.score += 100;
            System.out.println(score);
            return true;
        }
        return false;
    }

    /*private boolean checkCollision(final Plane plane) {
        Bounds monitoredPlaneBounds = plane.getSprite().getBoundsInParent();

        if (monitoredPlaneBounds.intersects(landingBoxLeft.getBoundsInParent())
                || monitoredPlaneBounds.intersects(landingBoxRight.getBoundsInParent())) {

            return true;
        }
        return false;
    }*/

    /**
     * Setter method for left landing spot.
     * @param leftBox: the left rectangle
     */
    public void setBoxLeft(final Rectangle leftBox) {
        this.landingBoxLeft = leftBox;
    }
    /**
     * Setter method for right landing spot.
     * @param rightBox: the right rectangle
     */
    public void setBoxRight(final Rectangle rightBox) {
        this.landingBoxRight = rightBox;
    }

    @Override
    public String toString() {
        return "BasicAirStrip [status=" + this.getStatus() + "]";
    }
}
