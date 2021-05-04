package alt.sim.model;

import javafx.geometry.Point2D;


/** Class that manage 2 Ratio object for calculate the unknowValue 10:40 = x:50
 * 
 * @author daniel
 */
public class Proportion {

	private Ratio ratioKnowed;
	private Ratio ratioToKnowed;
	private Ratio resultOfProportion;

	/**
	 * @param ratioKnowed Ratio of the proportion that we knowed
	 * @param ratioToKnowed Ratio need to know
	 */
	public Proportion(final Ratio ratioKnowed, final Ratio ratioToKnowed) {
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
	 * 
	 * Double.isNan() check if result was divided for 0, causing the NotANumber value
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

}
