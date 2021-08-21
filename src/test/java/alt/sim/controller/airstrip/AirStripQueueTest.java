package alt.sim.controller.airstrip;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.AirStripStatus;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.airstrip.HelipadAirStrip;
import alt.sim.model.plane.Plane;
import alt.sim.model.sprite.SpriteType;

public class AirStripQueueTest {

    private AbstractAirStrip strip1 = new BasicAirStrip();
    private AbstractAirStrip strip2 = new HelipadAirStrip();

    private Plane plane1 = new Plane();
    private Plane plane2 = new Plane();

    @Test
    public void checkQueue() {
        //TODO finish
        plane1.setSpritePlane(SpriteType.AIRPLANE.getURLImage());
        plane2.setSpritePlane(SpriteType.AIRPLANE.getURLImage());
        AirStripController.acceptPlane(strip1, null, plane1);
        AirStripController.acceptPlane(strip2, null, plane2);
        assertTrue(strip1.getStatus().equals(AirStripStatus.FREE));
        assertTrue(strip2.getStatus().equals(AirStripStatus.FREE));
    }
}
