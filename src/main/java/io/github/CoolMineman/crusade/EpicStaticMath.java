package io.github.CoolMineman.crusade;

import net.minecraft.util.math.MathHelper;

public class EpicStaticMath {
    private EpicStaticMath(){}

    public static double calcAngle(double x, double y, double x1, double y1)
    {
        return MathHelper.atan2(x - x1, y1 - y) * 180 / Math.PI + 180;
    }

    /**
     * Gets the diffrence between 2 degree angles
     * @param a 0 <= a <= 360
     * @param b 0 <= b <= 360
     * @return diffrence
     */
    public static double angleDiff(double a, double b) {
        double difference = a - b;
        while (difference < -180) difference += 360;
        while (difference > 180) difference -= 360;
        return difference;
    }
}