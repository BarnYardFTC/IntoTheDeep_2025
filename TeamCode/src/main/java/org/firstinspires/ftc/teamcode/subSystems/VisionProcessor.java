package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

public class VisionProcessor {
    // Rate limit variables
    private static final int READ_PERIOD = 1;
    private static Deadline rate_limit;

    // HuskyLens
    private static HuskyLens huskyLens;
    private static HuskyLens.Block[] blocks;

    // Algorithms variables
    private static double W0, H0, w1, h1, degree;

    /**
     * Create an HuskyLens.
     *
     * @param opMode: The opMode in which the huskyLens is being created.
     */
    public VisionProcessor(OpMode opMode) {
        huskyLens = opMode.hardwareMap.get(HuskyLens.class, "huskyLens");
        rate_limit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        W0 = 7;
        H0 = 3;
        w1 = 7;
        h1 = 3;
    }

    /**
     * Get the orientation of a sample which is in front of the huskylens in degrees
     *
     * @return The orientation in degrees
     */
    public static double getSampleOrientation() {
        if (rate_limit.hasExpired()) {
            rate_limit.reset();
            blocks = huskyLens.blocks();
            for (HuskyLens.Block bbox : blocks) {
                w1 = bbox.width;
                h1 = bbox.height;
                if (W0 == h1 && W0 == w1) {
                    degree = 90;
                } else if (W0 == w1 && H0 == h1) {
                    degree = 0;
                } else {
                    degree = Math.toDegrees(Math.atan(Math.abs((W0 - H0 * w1 / h1) / (W0 * w1 / h1 - H0))));
                }
            }
        }
        return degree;
    }
}