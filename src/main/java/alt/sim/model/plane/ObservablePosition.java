package alt.sim.model.plane;

import javafx.beans.property.SimpleDoubleProperty;

public class ObservablePosition {
    private SimpleDoubleProperty simplePositionProperty;

    public ObservablePosition(final Double positionValue) {
        this.simplePositionProperty = new SimpleDoubleProperty(positionValue);

        //Add Listener in State
        this.simplePositionProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("statePosition Changed!");
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue + "\n");
        });
    }

    public void setSimpleStateValue(final Double stateValue) {
        this.simplePositionProperty.setValue(stateValue);
    }

    public Double getSimpleStateValue() {
        return this.simplePositionProperty.getValue();
    }

    public SimpleDoubleProperty simplePositionProperty() {
        return this.simplePositionProperty;
    }
}
