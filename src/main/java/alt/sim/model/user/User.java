package alt.sim.model.user;

public interface User {

    /**
     *
     * @return name of the user (required non-null and unique,
     *         its length must be at most 12 chars long).
     */
    String getName();

    /**
     *
     * @return score of the user.
     */
    int getScore();

    /**
     *
     * @param score of the user
     */
    void setScore(int score);

    /**
     * Reset the score of the player
     */
    void resetScore();
}
