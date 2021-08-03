package alt.sim.controller;

import alt.sim.model.plane.Plane;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class PlaneController {

    //Connecting Model with Controller
    private Plane airlinePlane;

    @FXML
    private BorderPane root;

    @FXML
    private Label lblCoordinates;

    @FXML
    void handlerMouseClicked(final MouseEvent event) {
        lblCoordinates.setText(event.getX() + " : " + event.getY());

        // When event is handler, moved the Plane to the position specified and reload the root position
        airlinePlane.getSpritePlane().setX(event.getX());
        airlinePlane.getSpritePlane().setY(event.getY());

        root.getChildren().remove(airlinePlane.getImagePlane());
        root.getChildren().add(airlinePlane.getImagePlane());
    }

    /** Method executed before launch the FXML View to initialize the components. */
    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'PlaneMovement.fxml'.";

        this.airlinePlane = new Plane("images/map_components/airplane.png", new Point2D(0, 0));

        //airlinePlane.getSpritePlane().getSprite().resizeImageSprite(true);
        root.getChildren().add(airlinePlane.getImagePlane());
    }
}
