package alt.sim.model.user.validation;

import java.util.regex.Pattern;

/**
 * Class that defines regex pattern.
 */
public class NameValidationFunction {

    /**
     * Only numbers and letters are accepted.
     * Length range: min 1, max 12
     */
    private final String regex = "^[A-Za-z0-9]{1,12}$";
    private final Pattern pattern = Pattern.compile(regex);
    private String name;
    private static final int MAX_LENGTH = 12;

    public NameValidationFunction(final String name) {
        this.name  = name;
    }

    /**
     * Checks given name according to regex pattern.
     *
     * @return enum value result
     */
    public NameValidation checkName() {

        final String trimmedName = name.trim();
        if (name.isBlank()) {
            return NameValidation.EMPTY;
        }

        if (name.length() > MAX_LENGTH) {
            return NameValidation.TOO_LONG;
        }

        return pattern.matcher(trimmedName).find() ? NameValidation.CORRECT : NameValidation.WRONG;
    }
}
