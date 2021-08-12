package alt.sim.model.user.validation;

import alt.sim.controller.mapchoice.MapChoiceControllerImpl;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Class that defines regex pattern.
 */
public final class NameQuality {

    /**
     * Only numbers and letters are accepted.
     * Length range: min 1, max 12
     */
    private static final String REGEX = "^[A-Za-z0-9]{1,12}$";
    private final Pattern pattern = Pattern.compile(REGEX);
    private static final int MAX_LENGTH = 12;

    /**
     * Checks given name according to regex pattern.
     *
     * @param name to check for quality
     * @return enum value result
     */
    public NameValidation checkName(final String name) throws IOException {

        final MapChoiceControllerImpl mapChoice = new MapChoiceControllerImpl();
        final String trimmedName = name.trim();

        if (name.isBlank()) {
            return NameValidation.EMPTY;
        }

        if (trimmedName.length() > MAX_LENGTH) {
            return NameValidation.TOO_LONG;
        }

        if (mapChoice.isNameTaken(trimmedName)) {
            return NameValidation.TAKEN;
        }

        return pattern.matcher(trimmedName).find() ? NameValidation.CORRECT : NameValidation.WRONG;
    }
}
