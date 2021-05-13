package alt.sim.model;

/**
 * Represent the Mathematical concept of Ratio with an antecedent value e consequent value.
 *
 * Linking it to the ProportionCount class for finalize the Count of 2 Ratio object.
 *
 */
public class Ratio {

    private double antecedent;
    private double consequent;
    private boolean antecedentPresence;
    private boolean consequentPresence;
    private boolean antecedentHigher;

    /** constructor to initialized Ratio. */
    public Ratio() {
        this.antecedent = 0;
        this.consequent = 0;
    }

    /** Initialization of the class with 2 parameter needed to represent a Mathematical Ratio.
     * @param antecedent the Antecedent in a Ratio (x:Antecedent).
     * @param consequent the Consequent in a Ratio (Consequent:x).
    */
    public Ratio(final double antecedent, final double consequent) {
        this();
        if (antecedent > 0 && consequent > 0) {
            this.antecedent = antecedent;
            this.consequent = consequent;
        }
    }

    /**
     * @return Return the check result about if the antecedent value was passed or initialized.
     */
    public boolean isAntedentKnown() {
        if (this.antecedent > 0) {
            antecedentPresence = true;
            return antecedentPresence;
        }

        antecedentPresence = false;
        return false;
    }

    /**
    * @return Return the check result about if the consequent value was passed or initialized.
    */
    public boolean isConsequentKnown() {
        if (this.consequent > 0) {
            consequentPresence = true;
            return consequentPresence;
        }

        consequentPresence = false;
        return consequentPresence;
    }

    /**
     * Compare antecedent value with consequent for set the low or the high.
    */
    public void compareSize() {
        if (this.antecedent < this.consequent) {
            antecedentHigher = false;
        } else {
            this.antecedentHigher = true;
        }
    }

    /**
     * Execute the scale of the Ratio.
    */
    public void scale() {
        this.antecedent = UtilityCalculation.persentageCalculation(this.antecedent, UtilityCalculation.PERCENT_VALUE);
        this.consequent = UtilityCalculation.persentageCalculation(this.consequent, UtilityCalculation.PERCENT_VALUE);
    }

    /**
     * @return true if the antecedent is higher to consequent.
    */
    public boolean isAntecedentHigher() {
        return this.antecedentHigher;
    }

    /**
    * @return Return the antecedent value.
    */
    public double getAntecedent() {
        return this.antecedent;
    }

    /**
    * @return Return the consequent value.
    */
    public double getConsequent() {
        return this.consequent;
    }

    /** Set the value of antecedent.
    * @param antecedent is the new parameter to Set.
    */
    public void setAntecedent(final double antecedent) {
        this.antecedent = antecedent;
    }

    /** Set the value of consequent.
     * @param consequent is the new parameter to Set.
    */
    public void setConsequent(final double consequent) {
        this.consequent = consequent;
    }

    /**
    * @param antecedent the value of antecedent in the Ratio.
    * @param consequent the value of consequent in the Ratio.
    */
    public void setRatioValue(final double antecedent, final double consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    @Override
    public String toString() {
        return "Ratio [antecedent=" + this.antecedent + ", consequent=" + this.consequent + ", antecedentPresence="
                + this.antecedentPresence + ", consequentPresence=" + this.consequentPresence + ", antecedentHigher="
                + this.antecedentHigher + "]";
    }
}
