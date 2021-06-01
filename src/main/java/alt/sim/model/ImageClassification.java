package alt.sim.model;

/**
 * Organization of the URL link of the Image with the Enum.
 */
public enum ImageClassification {

    /** Plane image path.*/
    // OOP20-alt-sim/src/main/resources/images/map_components/airplane.png
    AIRPLANE("airplane", "images/map_components/airplane.png"),
    /** Airstrip image path.*/
    AIRSTRIP("airstrip", "images/map_components/airstrip.png"),
    /** Boat image path.*/
    BOAT("boat", "images/map_components/boat.png"),
    /** Helicopter image path.*/
    HELICOPTER("helicopter", "images/map_components/helicopter.png"),
    /** Helipad image path.*/
    HELIPAD("helipad", "images/map_components/helipad.png");

    private String imageCategory;
    private String urlImage;

    ImageClassification(final String imageCategory, final String urlImage) {
        this.urlImage = urlImage;
        this.imageCategory = imageCategory;
    }

    public String getImageCategory() {
        return this.imageCategory;
    }

    public String getURLImage() {
        this.urlImage = ClassLoader.getSystemResource(this.urlImage).toExternalForm();

        return this.urlImage;
    }
}
