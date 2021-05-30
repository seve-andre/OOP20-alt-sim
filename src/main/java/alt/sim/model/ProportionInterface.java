package alt.sim.model;

public interface ProportionInterface {

    /**
     * UnknownValue calculation, in this case we have only the antecedent value.
     */
    void calculationWidthAntecedentKnowed();

    /**
     * UnknownValue calculation, in this case we have only the consequent value.
     *
     * Double.isNan() check if result was divided for 0, causing the NotANumber value.
     */
    void calculationWidthConsequentKnowed();

    /**
     * Summary method that regroup the 2 type of UnknownValue calculation.
     */
    void calculationProportion();
}
