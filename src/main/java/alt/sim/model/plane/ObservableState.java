package alt.sim.model.plane;

import alt.sim.model.game.Game;
import alt.sim.view.seaside.Seaside;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.util.Duration;

public class ObservableState {
    private final ObjectProperty<State> stateProperty;
    //    private SimpleStringProperty simpleStateProperty;
    private Timeline timeline;
    private Plane planeObserved;

    private final ChangeListener<? super State> listener;

    public ObservableState(final Plane planeObserved, final State state) {
        // this.simpleStateProperty = new SimpleStringProperty(stateValue);
        stateProperty = new SimpleObjectProperty<>(state);
        this.planeObserved = planeObserved;

        System.out.println("create Plane: " + planeObserved.hashCode() + " State = " + state);

        listener = (observable, oldValue, newValue) -> {
            //System.out.println("stateTest Changed!");
            //System.out.println("Old state: " + oldValue);
            System.out.println(planeObserved.hashCode() + " New state: " + newValue);
            if(newValue == State.TERMINATED){
                this.timeline.stop();
            }

            this.planeObserved.stopPlaneMovementAnimation();
            this.planeObserved.stopRandomTransition();

            this.timeline = new Timeline(new KeyFrame(Duration.millis(4000),
                    e -> { }));

            timeline.setCycleCount(1);
            timeline.play();
            timeline.setOnFinished(finish -> {
                if (planeObserved.getState().equals(State.WAITING)) {
                    Screen screenGame = Screen.getPrimary();
                    Rectangle2D screenBound = screenGame.getVisualBounds();

                    //this.planeObserved.loadRandomTransition(screenBound.getWidth(), screenBound.getHeight());
                    this.planeObserved.loadRandomTransition(Seaside.getScreenWidth(), Seaside.getScreenHeight());
                }
            });
        };

        //Add Listener in State
        stateProperty.addListener(listener);
    }

    public void setState(final State state) {
        stateProperty.setValue(state);
    }

    public State getState() {
        return stateProperty.getValue();
    }

    public ObjectProperty<State> getStateProperty() {
        return stateProperty;
    }

    public void removeListener() {
        this.timeline.stop();
        stateProperty.removeListener(listener);
    }
}
