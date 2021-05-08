package test;

/** 
 * Class to calculate the scaling of an image that we want to be scaled,
 * in proportion to the size of the screen on which it will be positioned.
 * 
 *  Example:
 *  Screen [1024 x 768]
 *  Sprite [424 x 393] raw dimension of the image loaded.
 *  
 *  Image must be the 15% of Screen dimension.
 *  
 *  dimensioneSprite after the count = [64 x 64].
 */
public class ProportionImage {
	//Pane section:
	/** Min range of Screen dimension */
	public final static double DIMENSION_MIN_RANGE = 60;
	/** Max range of Screen dimension  */
	public final static double DIMENSION_MAX_RANGE = 2560;
	
	//Sprite section:
	/** Max range of Sprite dimension */
	public final static double SPRITE_MAX_RANGE = 512;
	/** Min range of Sprite dimension */
	public final static double SPRITE_MIN_RANGE = 16;
	
	private Ratio ratioImage;
	private Ratio ratioScreen;
	private Ratio resultOfProportion;

	/**
	 * Constructor initial method.
	 */
	public ProportionImage() {
		this.ratioImage = new Ratio();
		this.ratioScreen = new Ratio();
		this.resultOfProportion = new Ratio();
	}

	/**
	 * @param ratioImage Ratio of the proportion that we knowed.
	 * @param ratioScreen Ratio need to know.
	 */
	public ProportionImage(final Ratio ratioImage, final Ratio ratioScreen) {
		this();
		this.ratioImage = ratioImage;
		this.ratioScreen = ratioScreen;
	}
	
	/**
	 * UnknownValue calculation, in this case we have only the antecedent value.
	 */
	private void calculationWidthAntecedentKnowed() {
		double antecedentToKnow = Math.floor(ratioScreen.getAntecedent());
		double consequentToKnow = Math.floor((antecedentToKnow * ratioImage.getConsequent()) / ratioImage.getAntecedent());

		if(antecedentToKnow == Double.POSITIVE_INFINITY || consequentToKnow == Double.POSITIVE_INFINITY){
			antecedentToKnow = 0;
			consequentToKnow = 0;
		}

		this.resultOfProportion.setAntecedent(antecedentToKnow);
		this.resultOfProportion.setConsequent(consequentToKnow);
	}

	/**
	 * UnknownValue calculation, in this case we have only the consequent value.
	 * 
	 * Double.isNan() check if result was divided for 0, causing the NotANumber value.
	 */
	private void calculationWidthConsequentKnowed() {
		double consequentToKnow = Math.floor(ratioScreen.getConsequent());
		double antecedentToKnow = Math.floor((consequentToKnow * ratioImage.getAntecedent()) / ratioImage.getConsequent());

		if(Double.isNaN(antecedentToKnow) == true || Double.isNaN(consequentToKnow) == true){
			antecedentToKnow = 0;
			consequentToKnow = 0;
		}
		
		this.resultOfProportion.setAntecedent(antecedentToKnow);
		this.resultOfProportion.setConsequent(consequentToKnow);
	}
	
	/** 
	 * Calculating and comparing the result of Screen proportion scaled because the count.
	 * of imageResized depends from it.
	 */
	public void comparisonLowExponentForImage() {
		double resultWidth = 0;
		double resultHeight = 0;
		
		int exponent = 0;
		
		if(this.resultOfProportion.getAntecedent() < this.resultOfProportion.getConsequent()) {
			exponent = UtilityCalculation.exponentCalcultation((int)resultOfProportion.getAntecedent());
					
			resultHeight = Math.pow(UtilityCalculation.BASE_POWER, exponent);
			resultWidth = resultHeight;
			
		}else {
			exponent = UtilityCalculation.exponentCalcultation((int)resultOfProportion.getConsequent()); 
						
			resultWidth = Math.pow(UtilityCalculation.BASE_POWER, exponent);
			resultHeight = resultWidth;
		}
		
		this.resultOfProportion.setRatioValue(resultWidth, resultHeight);
	}
	
	/**
	 * Summary method that regroup the 2 type of calculation: for the Image and for the Screen.
	 */
	public void calculationProportion() {
		if(this.ratioScreen.isAntedentKnowed() == true) {
			this.calculationWidthAntecedentKnowed();
		}
		else { this.calculationWidthConsequentKnowed(); }
	}
	
	/**
	 * Comparison the antecedent and consequent values of ratioScreen to find the lower.
	 */
	public void calculationProportionCaseEquality() {
		// Check who the 2 members are the lower, for use it in calculation.
		ratioScreen.sizeCompare();
		
		if(this.ratioScreen.isAntecedentHighter() == true) {
			this.calculationWidthConsequentKnowed();
		}
		
		else { this.calculationWidthAntecedentKnowed(); }
	}
	
	/**
	 * final method that regroup all the operation for resized an Image in proportion with the Screen.
	 */
	public void renderingProportionImage() {
		ratioImage.scale();
		ratioScreen.scale();
		
		calculationProportionCaseEquality();
		comparisonLowExponentForImage();
	}
	
	//Get and Set methods:
	
	/**
	 * @return Return the resultOfProportion object.
	 */
	public Ratio getResultOfProportion() {
		return this.resultOfProportion;
	}
	
	/**
	 * @param resultOfProportion Set the new values of resultOfProportion.
	 */
	public void setResultOfProportion(final Ratio resultOfProportion) {
		this.resultOfProportion = resultOfProportion;
	}
	
	/**
	 * @param ratioImage Set the new values of ratioImage.
	 */
	public void setRatioImage(final Ratio ratioImage) {
		this.ratioImage = ratioImage;
	}
	
	/**
	 * @param ratioScreen Set the new values of ratioScreen.
	 */
	public void setRatioScreen(final Ratio ratioScreen) {
		this.ratioScreen = ratioScreen;
	}
	
}
