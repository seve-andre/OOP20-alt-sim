package alt.sim.controller.mapchoice;

import java.io.IOException;

import alt.sim.model.user.validation.NameValidation;

public interface MapChoiceController {

    /**
     * Checks given name quality.
     * @param name
     * @return name quality result
     */
    NameValidation checkName(String name) throws IOException;

    /**
     * Checks if given name is taken.
     * @param name
     * @return true if name is already taken
     * @throws IOException
     */
    boolean isNameTaken(String name) throws IOException;

    /**
     * Adds given user to file.
     * @param name
     * @throws IOException
     */
    void addUser(String name) throws IOException;
}
