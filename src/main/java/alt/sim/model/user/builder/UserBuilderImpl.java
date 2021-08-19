package alt.sim.model.user.builder;

import alt.sim.model.user.User;
import alt.sim.model.user.UserImpl;

public class UserBuilderImpl implements UserBuilder {

    private String name;
    private int score;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder score(final int score) {
        this.score = score;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User build() {
        return new UserImpl(this.name, this.score);
    }
}
