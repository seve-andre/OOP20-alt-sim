package alt.sim.model;

/**
 * Utility class to use for Resize calculation and for other counts.
 */
public final class UtilityCalculation {
    //Percent section:
    /** Min persentage range for calculation. */
    public static final int PERCENT_MIN_RANGE = 5;
    /** Max persentage range for calculation. */
    public static final int PERCENT_MAX_RANGE = 95;
    /** percentage chosen for rendering. */
    public static final int PERCENT_VALUE = 15;
    /** base division. */
    public static final int BASE_PERCENT = 100;

    //Math_Pow section:
    /** power's exponent, chose. */
    public static final double BASE_POWER = 2;

    private UtilityCalculation() { }

    /**
     *  Calculation of a number's persentage.
     *  the percentage value must be between (5 and 95)
     *
     * @param number target for persentage calculation
     * @param indexPersentage value to calculation
     * @return result of the calculating ceiled for simplicity
     */
    public static double persentageCalculation(final double number, final double indexPersentage) {
        int persentage;

        // Check of the Screen Dimension Range
        try {
            if (indexPersentage >= UtilityCalculation.PERCENT_MIN_RANGE && indexPersentage <= UtilityCalculation.PERCENT_MAX_RANGE) {
                persentage = (int) ((number * indexPersentage) / UtilityCalculation.BASE_PERCENT);
            } else {
                persentage = 0;
            }
        } catch (final ArithmeticException artm) {
            System.out.println("ARTM Exception: " + artm);
            return 0;
        }

        //value for simplicity ceiled
        Math.floor(persentage);

        return persentage;
    }

    /**
     *  Calculating the exponent of a base power (2049 = 2^12).
     *
     * @param number of we want find the exponent
     * @return return the exponent found
     */
    public static int exponentCalcultation(final int number) {
        int result = 0;
        int exponent = 0;

        while (result < number) {
            exponent++;
            result = (int) Math.pow(BASE_POWER, exponent);
        }

        if (result == number) {
            if (result < ProportionImage.SPRITE_MIN_RANGE || result > ProportionImage.SPRITE_MAX_RANGE) {
                System.out.println("Sprite Range too hight or too low");
                return 0;
            } else {
                return exponent;
            }
        } else {
            if (result < ProportionImage.SPRITE_MIN_RANGE || result > ProportionImage.SPRITE_MAX_RANGE) {
                System.out.println("Sprite Range too hight or too low");
                return 0;
            } else {
                exponent--;
            }
        }
        return exponent;
    }

}
