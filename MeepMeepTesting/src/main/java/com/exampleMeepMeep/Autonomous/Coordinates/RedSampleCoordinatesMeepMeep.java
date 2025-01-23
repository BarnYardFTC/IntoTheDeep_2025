package com.exampleMeepMeep.Autonomous.Coordinates;

import com.acmerobotics.roadrunner.Pose2d;

public class RedSampleCoordinatesMeepMeep {
    private static final double startX = -32;
    private static final double startY = -63;
    private static final double parkX = 32;
    private static final double startPoseHeading = Math.toRadians(90);
    private static final Pose2d start = new Pose2d(startX, startY, startPoseHeading);
    private static final Pose2d park = new Pose2d(parkX, startY, startPoseHeading);

    public static Pose2d getStart() {
        return start;
    }

    public static Pose2d getPark() {
        return park;
    }
}
