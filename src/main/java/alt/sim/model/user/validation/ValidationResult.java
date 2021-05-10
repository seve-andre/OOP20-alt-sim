package alt.sim.model.user.validation;

public enum ValidationResult {

    /**
     * Name is correct.
     */
    CORRECT("correct"),

    /**
     * Name is empty.
     */
    EMPTY("empty"),

    /**
     * Name is too long.
     */
    TOO_LONG("too long"),

    /**
     * Name does not match pattern.
     */
    WRONG("wrong");

    private final String result;

    ValidationResult(final String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }
}
