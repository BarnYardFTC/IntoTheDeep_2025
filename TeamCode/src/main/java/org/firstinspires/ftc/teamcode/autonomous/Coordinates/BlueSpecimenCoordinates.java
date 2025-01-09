package org.firstinspires.ftc.teamcode.autonomous.Coordinates;

import com.acmerobotics.roadrunner.Pose2d;

public class BlueSpecimenCoordinates {
    private static final double startX = -8;
    private static final double startY = 63;

    private static final double midWayMoveSpecimensTangent = Math.toRadians(90);
    private static final double startPoseHeading = Math.toRadians(270);

    private static final Pose2d startPose = new Pose2d(-8, 63, startPoseHeading);

    private static final Pose2d score1 = new Pose2d(4, 33, startPoseHeading);
    private static final Pose2d score2 = new Pose2d(1, 33, startPoseHeading);
    private static final Pose2d score3 = new Pose2d(-2, 33, Math.toRadians(270));
    private static final Pose2d score4 = new Pose2d(-5, 33, Math.toRadians(270));
    private static final Pose2d score5 = new Pose2d(-8, 33, Math.toRadians(270));

    private static final Pose2d midWayMoveSpecimens = new Pose2d(-33, 36, startPoseHeading);
    private static final Pose2d moveSpecimensStart0 = new Pose2d(-33, 13, startPoseHeading);

    private static final Pose2d moveSpecimenStart1 = new Pose2d(-46, 13, Math.toRadians(270));
    private static final Pose2d moveSpecimenStart2 = new Pose2d(-54, 13, Math.toRadians(270));
    private static final Pose2d moveSpecimenStart3 = new Pose2d(-62, 33, Math.toRadians(270));

    private static final Pose2d moveSpecimenEnd1 = new Pose2d(-46, 54, Math.toRadians(270));
    private static final Pose2d moveSpecimenEnd2 = new Pose2d(-54, 54, Math.toRadians(270));
    private static final Pose2d moveSpecimenEnd3 = new Pose2d(-62, 54, Math.toRadians(270));

    private static final Pose2d intake = new Pose2d(-46, 59, Math.toRadians(270));

    private static final Pose2d park = new Pose2d(-62, 59, Math.toRadians(270));

    public static Pose2d getStartPose() {
        return startPose;
    }

    public static Pose2d getScore1() {
        return score1;
    }

    public static Pose2d getScore2() {
        return score2;
    }

    public static Pose2d getScore3() {
        return score3;
    }

    public static Pose2d getScore4() {
        return score4;
    }

    public static Pose2d getScore5() {
        return score5;
    }

    public static Pose2d getMidWayMoveSpecimens() {
        return midWayMoveSpecimens;
    }

    public static Pose2d getMoveSpecimensStart0() {
        return moveSpecimensStart0;
    }

    public static Pose2d getMoveSpecimenStart1() {
        return moveSpecimenStart1;
    }

    public static Pose2d getMoveSpecimenStart2() {
        return moveSpecimenStart2;
    }

    public static Pose2d getMoveSpecimenStart3() {
        return moveSpecimenStart3;
    }

    public static Pose2d getMoveSpecimenEnd1() {
        return moveSpecimenEnd1;
    }

    public static Pose2d getMoveSpecimenEnd2() {
        return moveSpecimenEnd2;
    }

    public static Pose2d getMoveSpecimenEnd3() {
        return moveSpecimenEnd3;
    }

    public static Pose2d getIntake() {
        return intake;
    }

    public static Pose2d getPark() {
        return park;
    }
}
