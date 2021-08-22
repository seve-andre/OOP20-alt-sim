package alt.sim.controller.mapchoice;

import alt.sim.model.user.validation.NameResult;

import java.io.IOException;

public interface MapChoiceController {

    /**
     * Checks given name quality.
     * @param name to check for quality
     * @return name quality result
     * @throws IOException if name not present
     */
    NameResult checkName(String name) throws IOException;

    /**
     * Checks if given name is taken.
     * @param name to check if taken
     * @return true if name is already taken
     * @throws IOException if name not present
     */
    boolean isNameTaken(String name) throws IOException;

    /**
     * Adds given user to file.
     * @param name of the user
     * @throws IOException if file does not exist
     */
    void addUser(String name) throws IOException;
}
