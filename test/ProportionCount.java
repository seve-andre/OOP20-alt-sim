package test;

import javafx.geometry.Point2D;


/** Class that manage 2 Ratio object for calculate the unknowValue 10:40 = x:50
 * 
 * @author daniel
 */
public class ProportionCount {

	private Ratio ratioKnowed;
	private Ratio ratioToKnowed;
	private Ratio resultOfProportion;

	/**
	 * @param ratioKnowed Ratio of the proportion that we knowed
	 * @param ratioToKnowed Ratio need to know
	 */
	public ProportionCount(final Ratio ratioKnowed, final Ratio ratioToKnowed) {
		this.ratioKnowed = ratioKnowed;
		this.ratioToKnowed = ratioToKnowed;
		this.resultOfProportion = new Ratio();
	}
	
	/**
	 * UnknownValue calculation, in this case we have only the antecedent value
	 */
	private void calculationWidthAntecedentKnowed() {
		double antecedentToKnow = Math.floor(ratioToKnowed.getAntecedent());
		double consequentToKnow = Math.floor((antecedentToKnow * ratioKnowed.getConsequent()) / ratioKnowed.getAntecedent());

		if(antecedentToKnow == Double.POSITIVE_INFINITY || consequentToKnow == Double.POSITIVE_INFINITY){
			antecedentToKnow = 0;
			consequentToKnow = 0;
		}

		this.resultOfProportion.setAntecedent(antecedentToKnow);
		this.resultOfProportion.setConsequent(consequentToKnow);
	}

	/**
	 * UnknownValue calculation, in this case we have only the consequent value
	 */
	private void calculationWidthConsequentKnowed() {
		double consequentToKnow = Math.floor(ratioToKnowed.getConsequent());
		double antecedentToKnow = Math.floor((consequentToKnow * ratioKnowed.getAntecedent()) / ratioKnowed.getConsequent());

		if(Double.isNaN(antecedentToKnow) == true || Double.isNaN(consequentToKnow) == true){
			antecedentToKnow = 0;
			consequentToKnow = 0;
		}
		
		this.resultOfProportion.setAntecedent(antecedentToKnow);
		this.resultOfProportion.setConsequent(consequentToKnow);
	}
	
	/**
	 * Summary method that regroup the 2 type of UnknowValue calculation
	 */
	public void calculationProportion() {
		if(this.ratioToKnowed.isAntedentKnowed() == true) {
			this.calculationWidthAntecedentKnowed();
		}
		else { this.calculationWidthConsequentKnowed(); }
	}

	/**
	 * Execute the proportion to find the dimension value in scale proportion
	 * formula: [ pane_width:pane_height = sprite_width:sprite_height ]
	 * 
	 * @param spriteDimensionScaled Sprite dimension already scaled
	 * @param paneDimensionScaled   Pane dimension already scaled
	 * @param isSquare              define the kind of dimension scale [16x16] =
	 *                              Square [128x64] Rectangle
	 * @return return the proportion between Pane and Sprite in base_power chose
	 */
	/*
	 * public static Point2D proportionCalculation(final Point2D
	 * spriteDimensionScaled, final Point2D paneDimensionScaled, final boolean
	 * isSquare) { Point2D proportionResult;
	 * 
	 * double x_unknownWidth; double x_unknownHeight;
	 * 
	 * // Check the Pane Range if(paneDimensionScaled.getX() <=
	 * paneDimensionScaled.getY()){ x_unknownHeight = (spriteDimensionScaled.getY()
	 * * paneDimensionScaled.getX()) / spriteDimensionScaled.getX(); x_unknownWidth
	 * = paneDimensionScaled.getX();
	 * 
	 * }else { x_unknownWidth = (spriteDimensionScaled.getX() *
	 * paneDimensionScaled.getY() ) / spriteDimensionScaled.getY(); x_unknownHeight
	 * = paneDimensionScaled.getY();
	 * 
	 * proportionResult = new Point2D(x_unknownWidth, x_unknownHeight); }
	 * 
	 * proportionResult = new Point2D(x_unknownWidth, x_unknownHeight);
	 * 
	 * 
	 * if(isSquare == true) { proportionResult =
	 * spriteDimensionCalculationSquare(proportionResult); }else { proportionResult
	 * = spriteDimensionCalculationNotSquare(proportionResult); }
	 * 
	 * 
	 * return proportionResult; }
	 */

	
	/**
	 * @return Return the resultOfProportion object, it contains the 2 UnknowValues researched
	 */
	public Ratio getResultOfProportion() {
		return this.resultOfProportion;
	}
	
	/**
	 * @return Return the resultOfProportion object converted for the Point2D type
	 */
	public Point2D getResultOfProportionInConvertedType() {
		Point2D conversionType;
		double antecedent = this.resultOfProportion.getAntecedent();
		double consequent = this.resultOfProportion.getConsequent();
		
		conversionType = new Point2D(antecedent, consequent);
		
		return conversionType;
	}

	public static void main(String[] args) {
		Ratio ratioKnowed = new Ratio(0, 0);
		Ratio ratioNotKnowed = new Ratio(0, 0);
		ProportionCount pTest = new ProportionCount(ratioKnowed, ratioNotKnowed);
		Point2D resultConverted;
		
		pTest.calculationProportion();
		resultConverted = pTest.getResultOfProportionInConvertedType();

		System.out.println(pTest.getResultOfProportion().toString());
		System.out.println("resultConverted = " + resultConverted);
	}

}
