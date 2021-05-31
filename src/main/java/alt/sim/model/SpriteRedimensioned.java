package alt.sim.model;

import alt.sim.model.calculation.UtilityCalculation;

public class SpriteRedimensioned {
    //Pane section:
    /** Min range of Screen dimension. */
    public static final double DIMENSION_MIN_RANGE = 60;
    /** Max range of Screen dimension.  */
    public static final double DIMENSION_MAX_RANGE = 2560;

    //Sprite section:
    /** Max range of Sprite dimension. */
    public static final double SPRITE_MAX_RANGE = 512;
    /** Min range of Sprite dimension. */
    public static final double SPRITE_MIN_RANGE = 16;

    private RatioImpl ratioScreen;
    private RatioImpl ratioSprite;
    private RatioImpl resultBoundsSprite;
    private boolean selectTheHightValueResize;

    /**
     * Constructor initial method.
    */
    public SpriteRedimensioned() {
        this.ratioSprite = new RatioImpl();
        this.ratioScreen = new RatioImpl();
        this.resultBoundsSprite = new RatioImpl();
        this.selectTheHightValueResize = false;
    }

    public SpriteRedimensioned(final RatioImpl ratioScreen, final RatioImpl ratioSprite, final boolean isSelectTheHightValueResize) {
        this();
        this.ratioScreen = ratioScreen;
        this.ratioSprite = ratioScreen;
    }

    public void resizeBoundsSprite() {
        scaleSprite(UtilityCalculation.PERCENT_VALUE);
        selectSpriteBoundsLow(); 
    }

    // l'immagine da scalare viene calcolata sulla dimensione della Mappa
    public void scaleSprite(final int percentageScale) {
        double spriteWidthScaled = UtilityCalculation.persentageCalculation(ratioScreen.getAntecedent(), percentageScale);
        double spriteHeightScaled = UtilityCalculation.persentageCalculation(ratioScreen.getConsequent(), percentageScale);

        this.ratioSprite.setRatioValue(spriteWidthScaled, spriteHeightScaled);
    }

    public void selectSpriteBoundsLow() {
        int exponent = 0;

        if (ratioSprite.getAntecedent() <= ratioSprite.getConsequent()) {
            exponent = UtilityCalculation.exponentCalcultation((int) ratioSprite.getAntecedent());
        } else {
            exponent = UtilityCalculation.exponentCalcultation((int) ratioSprite.getConsequent());
        }

        // Calcolo domensione finale dall'esponente
        this.resultBoundsSprite.setAntecedent(Math.pow(UtilityCalculation.BASE_POWER, exponent));
        this.resultBoundsSprite.setConsequent(Math.pow(UtilityCalculation.BASE_POWER, exponent));
    }

    public void setRatioScreenValue(final double widthScreen, final double heightScreen) {
        this.ratioScreen.setAntecedent(widthScreen);
        this.ratioScreen.setConsequent(heightScreen);
    }

    public void setRatioSpriteValue(final double widthSprite, final double heightSprite) {
        this.ratioSprite.setAntecedent(widthSprite);
        this.ratioSprite.setConsequent(heightSprite);
    }

    public RatioImpl getResultBoundsSprite() {
        return this.resultBoundsSprite;
    }

    public void setSelectTheHightValueResize(final boolean selectTheHightValueResize) {
        this.selectTheHightValueResize = selectTheHightValueResize;
    }

    public boolean getSelectTheHightValueResize() {
        return this.selectTheHightValueResize;
    }


    /*
     * public static void main(final String[] args) { SpriteRedimensioned sprtTest =
     * new SpriteRedimensioned();
     * 
     * sprtTest.setRatioSpriteValue(600, 600); sprtTest.setRatioScreenValue(1920,
     * 1080); sprtTest.setSelectTheHightValueResize(false);
     * 
     * sprtTest.resizedBoundsSprite(); System.out.println("Sprite resized width: " +
     * sprtTest.getResultBoundsSprite().getAntecedent() + " ,height: " +
     * sprtTest.getResultBoundsSprite().getConsequent()); }
     */
}
