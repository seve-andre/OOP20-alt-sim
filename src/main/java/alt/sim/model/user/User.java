package alt.sim.model.user;

public interface User {

    /**
     * Gets user name.
     * @return name of the user (required non-null and unique,
     *         its length must be at most 12 chars long).
     */
    String getName();

    /**
     * Gets user score.
     * @return score of the user.
     */
    int getScore();

    /**
     * Sets user score.
     * @param score of the user
     */
    void setScore(int score);

    /**
     * Reset the score of the user.
     */
    void resetScore();
}
