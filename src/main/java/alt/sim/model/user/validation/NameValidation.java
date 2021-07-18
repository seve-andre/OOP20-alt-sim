package alt.sim.model.user.validation;

public enum NameValidation {

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
    WRONG("wrong"),

    /**
     * Name is already taken.
     */
    TAKEN("taken");

    private String result;

    NameValidation(final String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }
}
