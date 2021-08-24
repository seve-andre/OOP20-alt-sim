package alt.sim.model.airstrip;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import alt.sim.controller.airstrip.AirStripController;
import alt.sim.controller.seaside.SeasideController;
import alt.sim.model.plane.Plane;

public class AirStripStatusTest {

    private AbstractAirStrip strip = new BasicAirStrip();

    private Plane plane1 = new Plane();
    private Plane plane2 = new Plane();

    private SeasideController transitionSeaside = new SeasideController();

    @Test
    public void checkStatus() {
        strip.setStatus(AirStripStatus.BUSY);
        assertFalse(AirStripController.acceptPlane(strip, transitionSeaside, plane1));
        strip.setStatus(AirStripStatus.DISABLED);
        assertFalse(AirStripController.acceptPlane(strip, transitionSeaside, plane2));
    }
}
