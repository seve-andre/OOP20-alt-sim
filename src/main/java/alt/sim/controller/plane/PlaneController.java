package alt.sim.controller.plane;

import alt.sim.model.plane.PlaneImpl;
import alt.sim.model.sprite.SpriteType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

@Deprecated
public class PlaneController {

    //Connecting Model with Controller
    private PlaneImpl airlinePlane;

    @FXML
    private BorderPane root;

    @FXML
    private Label lblCoordinates;

    @FXML
    void handlerMouseClicked(final MouseEvent event) {
        lblCoordinates.setText(event.getX() + " : " + event.getY());

        // When event is handler, moved the Plane to the position specified and reload the root position
        airlinePlane.getSprite().setX(event.getX());
        airlinePlane.getSprite().setY(event.getY());

        root.getChildren().remove(airlinePlane.getSprite());
        root.getChildren().add(airlinePlane.getSprite());
    }

    /** Method executed before launch the FXML View to initialize the components. */
    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'PlaneMovement.fxml'.";

        this.airlinePlane = new PlaneImpl(SpriteType.AIRPLANE);

        root.getChildren().add(airlinePlane.getSprite());
    }
}
