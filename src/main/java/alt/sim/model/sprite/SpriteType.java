package alt.sim.model.sprite;

/**
 * Organization of the URL link of the Image with the Enum.
 */
public enum SpriteType {

    /** Plane image path.*/
    AIRPLANE("images/map_components/airplane.png"),
    /** Airstrip image path.*/
    AIRSTRIP( "images/map_components/singleAirstrip.png"),
    /** Boat image path.*/
    BOAT( "images/map_components/boat.png"),
    /** Helicopter image path.*/
    HELICOPTER("images/map_components/helicopter.png"),
    /** Helipad image path.*/
    HELIPAD("images/map_components/helipad.png");

    private String urlImage;

    SpriteType(final String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * @return String associated to URL Image.
     */
    public String getURLImage() {
        this.urlImage = ClassLoader.getSystemResource(this.urlImage).toExternalForm();

        return this.urlImage;
    }
}
