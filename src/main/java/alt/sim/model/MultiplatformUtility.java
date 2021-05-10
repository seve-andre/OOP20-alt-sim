package alt.sim.model;

public final class MultiplatformUtility {
    /**
     * Separator applied by the operating system currently used.
     */
    public static final String KERNEL_SEPARATOR = System.getProperty("file.separator");

    private MultiplatformUtility() { }
}
