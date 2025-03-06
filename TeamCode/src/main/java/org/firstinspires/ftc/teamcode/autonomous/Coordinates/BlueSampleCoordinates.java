package org.firstinspires.ftc.teamcode.autonomous.Coordinates;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;

@Config
public class BlueSampleCoordinates {
    public static double startX = 39.75;
    public static double startY = 64.5;
    public static double scoreX0 = 57;
    public static double scoreY0 = 49;
    public static double scoreX = 53;
    public static double scoreY = 45;
    public static double intake2X = 49;
    public static double intake2Y = 52;
    public static double intake3X = 57;
    public static double intake4X = 51;
    public static double intake4Y = 50;
    public static double park1X = 36;
    public static double park2X = 26;
    public static double parkY = 7;
    private static final double startPoseHeading = Math.toRadians(180);
    private static final double scorePoseHeading = Math.toRadians(225);
    private static final double intake2PoseHeading = Math.toRadians(270);
    private static final double parkPoseHeading = Math.toRadians(0);
    private static final double scoreTangent = Math.toRadians(270);
    private static final double intake4PoseHeading = Math.toRadians(315);
    private static final double intake2HeadingChange = Math.toRadians(45);
    private static final double intake4HeadingChange = Math.toRadians(90);
    private static final Pose2d start = new Pose2d(startX, startY, startPoseHeading);
    private static final Pose2d score0 = new Pose2d(scoreX0, scoreY0, scorePoseHeading);
    private static final Pose2d score = new Pose2d(scoreX, scoreY, scorePoseHeading);
    private static final Pose2d intake2 = new Pose2d(intake2X, intake2Y, intake2PoseHeading);
    private static final Pose2d intake3 = new Pose2d(intake3X, intake2Y, intake2PoseHeading);
    private static final Pose2d intake4 = new Pose2d(intake4X, intake4Y, intake4PoseHeading);
    private static final Pose2d park1 = new Pose2d(park1X, parkY, parkPoseHeading);
    private static final Pose2d park2 = new Pose2d(park2X, parkY, parkPoseHeading);

    public static Pose2d getStart() {
        return start;
    }

    public static Pose2d getScore() {
        return score;
    }
    public static Pose2d getScore0() {
        return score0;
    }

    public static Pose2d getIntake2() {
        return intake2;
    }

    public static Pose2d getIntake3() {
        return intake3;
    }

    public static Pose2d getIntake4() {
        return intake4;
    }

    public static Pose2d getPark1() {
        return park1;
    }
    public static Pose2d getPark2() {
        return park2;
    }

    public static double getScoreTangent() {
        return scoreTangent;
    }

    public static double getIntake2HeadingChange() {
        return intake2HeadingChange;
    }

    public static double getIntake4HeadingChange() {
        return intake4HeadingChange;
    }
}
