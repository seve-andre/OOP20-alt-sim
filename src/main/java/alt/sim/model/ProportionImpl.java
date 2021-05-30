package alt.sim.model;

import java.awt.geom.Point2D;

/** Class that manage 2 Ratio object for calculate the unknownValue 10:40 = x:50.
 *
 */
public class ProportionImpl implements ProportionInterface {

    private RatioImpl ratioKnown;
    private RatioImpl ratioToKnow;
    private RatioImpl resultOfProportion;

    /**
     * @param ratioKnown Ratio of the proportion that we knowed
     * @param ratioToKnow Ratio need to know
     */
    public ProportionImpl(final RatioImpl ratioKnown, final RatioImpl ratioToKnow) {
        this.ratioKnown = ratioKnown;
        this.ratioToKnow = ratioToKnow;
        this.resultOfProportion = new RatioImpl();
    }

    /**
     * UnknownValue calculation, in this case we have only the antecedent value.
     */
    public void calculationWidthAntecedentKnowed() {
        double antecedentToKnow = Math.floor(ratioToKnow.getAntecedent());
        double consequentToKnow = Math.floor(
                (antecedentToKnow * ratioKnown.getConsequent()) / ratioKnown.getAntecedent()
        );

        if (antecedentToKnow == Double.POSITIVE_INFINITY || consequentToKnow == Double.POSITIVE_INFINITY) {
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
    public void calculationWidthConsequentKnowed() {
        double consequentToKnow = Math.floor(ratioToKnow.getConsequent());
        double antecedentToKnow = Math.floor(
                (consequentToKnow * ratioKnown.getAntecedent()) / ratioKnown.getConsequent()
        );

        if (Double.isNaN(antecedentToKnow) || Double.isNaN(consequentToKnow)) {
            antecedentToKnow = 0;
            consequentToKnow = 0;
        }

        this.resultOfProportion.setAntecedent(antecedentToKnow);
        this.resultOfProportion.setConsequent(consequentToKnow);
    }

    /**
     * Summary method that regroup the 2 type of UnknownValue calculation.
     */
    public void calculationProportion() {
        if (this.ratioToKnow.isAntedentKnown()) {
            this.calculationWidthAntecedentKnowed();
        } else {
            this.calculationWidthConsequentKnowed();
        }
    }

    /**
     * @return Return the resultOfProportion object, it contains the 2 UnknowValues researched
     */
    public RatioImpl getResultOfProportion() {
        return this.resultOfProportion;
    }

    /**
     * @return Return the resultOfProportion object converted for the Point2D type
     */
    public Point2D getResultOfProportionInConvertedType() {
        double antecedent = this.resultOfProportion.getAntecedent();
        double consequent = this.resultOfProportion.getConsequent();

        Point2D conversionType = new Point2D.Double(antecedent, consequent);

        return conversionType;
    }

}
