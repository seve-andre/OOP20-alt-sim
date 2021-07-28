package alt.sim.controller.mapchoice;

import alt.sim.model.user.UserImpl;
import alt.sim.model.user.records.UserRecordsImpl;
import alt.sim.model.user.validation.NameQuality;
import alt.sim.model.user.validation.NameValidation;

import java.io.IOException;

public class MapChoiceControllerImpl implements MapChoiceController {

    private final NameQuality nameQuality = new NameQuality();
    private final UserRecordsImpl userRecordsImpl = new UserRecordsImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public NameValidation checkName(final String name) throws IOException {
        return nameQuality.checkName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameTaken(final String name) throws IOException {
        return userRecordsImpl.isPresent(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(final String name) throws IOException {
        userRecordsImpl.addUser(new UserImpl(name, 0));
    }
}
