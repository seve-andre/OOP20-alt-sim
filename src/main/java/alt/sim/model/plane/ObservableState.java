package alt.sim.model.plane;

import alt.sim.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.util.Duration;

public class ObservableState {
    private static final int TIMELINE_DURATION = 4000;
    private final ObjectProperty<State> stateProperty;
    private Timeline timeline;
    private PlaneImpl planeObserved;

    private final ChangeListener<? super State> listener;

    public ObservableState(final PlaneImpl planeObserved, final State state) {
        final int deltaBound = 35;

        stateProperty = new SimpleObjectProperty<>(state);
        this.planeObserved = planeObserved;

        // initialize listener State:
        listener = (observable, oldValue, newValue) -> {

            if (newValue == State.TERMINATED && this.timeline != null) {
                this.timeline.stop();
            }

            this.planeObserved.stopPlaneMovementAnimation();
            this.planeObserved.stopRandomTransition();

            this.timeline = new Timeline(new KeyFrame(Duration.millis(TIMELINE_DURATION),
                    e -> { }));

            timeline.setCycleCount(1);
            timeline.play();
            timeline.setOnFinished(finish -> {
                if (planeObserved.getState().equals(State.WAITING)) {
                    this.planeObserved.loadRandomTransition(Main.getStage().getWidth() - deltaBound, Main.getStage().getHeight() - deltaBound);
                }
            });
        };

        //Add Listener in State
        stateProperty.addListener(listener);
    }

    public void setState(final State state) {
        stateProperty.setValue(state);
    }

    /**
     * @return current state Plane value (SPAWNING, WAITING, RANDOM_MOVEMENT, USER_MOVEMENT, LANDED, TERMINATED).
     */
    public State getState() {
        return stateProperty.getValue();
    }

    /**
     * remove the listener field stopping the timeline loop.
     */
    public void removeListener() {
        this.timeline.stop();
        stateProperty.removeListener(listener);
    }
}
