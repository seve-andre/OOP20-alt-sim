package alt.sim.controller.mapchoice;

import alt.sim.model.user.validation.NameQuality;

public class MapChoiceControllerImpl implements MapChoiceController {

    private final NameQuality nameQuality = new NameQuality();

    @Override
    public void logUser(final String name) {
        this.nameQuality.checkName(name);
    }

}
