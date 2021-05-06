package alt.sim.model;

/**
 * Represent the Mathematical concept of Ratio with an antecedent value e consequent value.
 * 
 * Linking it to the ProportionCount class for finalize the Count of 2 Ratio object
 * 
 */
public class Ratio {

    private double antecedent;
    private double consequent;

    /** */
    public Ratio() { 
        this.antecedent = 0;
        this.consequent = 0;
    }

    /** Initialization of the class with 2 parameter needed to represent a Mathematical Ratio.
     * @param antecedent the Antecedent in a Ratio x:Antecedent
     * @param consequent the Consequent in a Ratio Consequent:x
     */
    public Ratio(final double antecedent, final double consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    /**
     * @return Return the check result about if the antecedent value was passed or initialized
     */
    public boolean isAntedentKnowed() {
        if (this.antecedent > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return Return the check result about if the consequent value was passed or initialized
     */
    public boolean isConsequentKnowed() {
        if (this.consequent > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return Return the antecedent value
     */
    public double getAntecedent() {
        return this.antecedent;
    }

    /**
     * @return Return the consequent value
     */
    public double getConsequent() {
        return this.consequent;
    }

    /** Set the value of antecedent.
     * @param antecedent is the new parameter to Set
     */
    public void setAntecedent(final double antecedent) {
        this.antecedent = antecedent;
    }

    /** Set the value of consequent.
     * @param consequent is the new parameter to Set
    */
    public void setConsequent(final double consequent) {
        this.consequent = consequent;
    }

    /** Summary method of Ratio class nature.
     * @return string representation of Ratio
     */
    public String toString() {
        return ("Ratio = " + this.getAntecedent() + ":" + this.getConsequent() + " isAntedentKnowed? = " + this.isAntedentKnowed() + " isConsequentKnowed? = " + this.isConsequentKnowed());
    }

}
