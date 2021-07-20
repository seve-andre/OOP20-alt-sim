package alt.sim.model.plane;

import javafx.beans.property.SimpleStringProperty;

public class ObservableState {
    private SimpleStringProperty simpleStateProperty;

    public ObservableState(final String stateValue){
        this.simpleStateProperty = new SimpleStringProperty(stateValue);

        //Add Listener in State
        this.getSimpleStateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("stateTest Changed!");
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue + "\n");
        });
    }

    public void setSimpleStateValue(final String stateValue) {
        this.simpleStateProperty.setValue(stateValue);
    }

    public String getSimpleStateValue() {
        return this.simpleStateProperty.getValue();
    }

    public SimpleStringProperty getSimpleStateProperty(){
        return this.simpleStateProperty;
    }
}
