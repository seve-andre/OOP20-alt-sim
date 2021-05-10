package alt.sim.model.user;

import java.util.Objects;

public class UserImpl implements User {

    private final String name;
    private final int score;

    public UserImpl(final String name, final int score) {
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
    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "UserImpl [name=" + name + ", score=" + score + "]";
    }
}
