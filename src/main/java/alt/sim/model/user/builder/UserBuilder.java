package alt.sim.model.user.builder;

import alt.sim.model.user.User;

public interface UserBuilder {

    /**
     *
     * @param name of the user
     * @return builder
     */
    UserBuilder name(String name);

    /**
     *
     * @param score of the user
     * @return builder
     */
    UserBuilder score(int score);

    /**
     * Builds user by name and score.
     * @return user
     */
    User build();
}
