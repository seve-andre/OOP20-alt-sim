package alt.sim.model.game;

public class Score {

    private int value;

    public Score() {
        value = 0;
    }

    public void updateValue(int delta) {
        value += delta;
    }

    public int getValue() {
        return value;
    }

}
