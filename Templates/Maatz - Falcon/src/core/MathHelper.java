package core;

public final class MathHelper {
    /**
     * Checks whether a number is in the range [min, max] (including the limits)
     * @param value - the value to be checked
     * @param min - the minimum boundary
     * @param max - the maximum boundary
     * @return - true if min <= value <= max
     */
    public static boolean isInRange(int value, int min, int max) {
        return (value >= min) && (value <= max);
    }
}
