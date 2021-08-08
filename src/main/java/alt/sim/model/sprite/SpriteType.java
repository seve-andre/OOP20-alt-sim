package alt.sim.model.sprite;

/**
 * Organization of the URL link of the Image with the Enum.
 */
public enum SpriteType {

    /** Plane image path.*/
    AIRPLANE("airplane", "images/map_components/airplane.png"),
    /** Airstrip image path.*/
    AIRSTRIP("airstrip", "images/map_components/singleAirstrip.png"),
    /** Boat image path.*/
    BOAT("boat", "images/map_components/boat.png"),
    /** Helicopter image path.*/
    HELICOPTER("helicopter", "images/map_components/helicopter.png"),
    /** Helipad image path.*/
    HELIPAD("helipad", "images/map_components/helipad.png");

    private String urlImage;

    SpriteType(final String imageCategory, final String urlImage) {
        this.urlImage = urlImage;
    }

    public String getURLImage() {
        this.urlImage = ClassLoader.getSystemResource(this.urlImage).toExternalForm();

        return this.urlImage;
    }
}
