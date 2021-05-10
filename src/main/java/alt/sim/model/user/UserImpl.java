package alt.sim.model.user;

import java.util.Objects;

public class UserImpl implements User {

    private final String name;
    private final String score;

    public UserImpl(final String name, final String score) {
        this.name = Objects.requireNonNull(name);
        this.score = score;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "UserImpl [name=" + name + ", score=" + score + "]";
    }
}
