package org.firstinspires.ftc.teamcode.autonomous.Coordinates;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;

@Config
public class BlueSampleCoordinates {
    public static double startX = 39.75;
    public static double startY = 58.2;
    public static double scoreX1 = 57;
    public static double scoreY1 = 53;
    public static double scoreX2 = 52;
    public static double scoreY2 = 54.3;
    public static double scoreX3 = 50.5;
    public static double scoreY3 = 53.25;
    public static double scoreX4 = 49.4;
    public static double scoreY4 = 54;
    public static double intake2X = 48.75;
    public static double intake2Y = 50;
    public static double intake3X = 56.25;
    public static double intake3Y = 47;
    public static double intake4X = 51.25;
    public static double intake4Y = 40.4;
    public static double park1X = 36;
    public static double park2X = 26;
    public static double parkY = 5;
    private static final double startPoseHeading = Math.toRadians(180);
    private static final double scorePoseHeading = Math.toRadians(225);
    private static final double intake3PoseHeading = Math.toRadians(270);
    private static final double intake2PoseHeading = Math.toRadians(270);
    private static final double parkPoseHeading = Math.toRadians(0);
    private static final double scoreTangent = Math.toRadians(270);
    private static final double intake4PoseHeading = Math.toRadians(305);
    private static final double intake2HeadingChange = Math.toRadians(45);
    private static final Pose2d start = new Pose2d(startX, startY, startPoseHeading);
    private static final Pose2d score1 = new Pose2d(scoreX1, scoreY1, scorePoseHeading);
    private static final Pose2d score2 = new Pose2d(scoreX2, scoreY2, scorePoseHeading);
    private static final Pose2d score3 = new Pose2d(scoreX3, scoreY3, scorePoseHeading);
    private static final Pose2d score4 = new Pose2d(scoreX4, scoreY4, scorePoseHeading);
    private static final Pose2d intake2 = new Pose2d(intake2X, intake2Y, intake2PoseHeading);
    private static final Pose2d intake3 = new Pose2d(intake3X, intake3Y, intake3PoseHeading);
    private static final Pose2d intake4 = new Pose2d(intake4X, intake4Y, intake4PoseHeading);
    private static final Pose2d park1 = new Pose2d(park1X, parkY, parkPoseHeading);
    private static final Pose2d park2 = new Pose2d(park2X, parkY, parkPoseHeading);

    public static Pose2d getStart() {
        return start;
    }

    public static Pose2d getScore2() {
        return score2;
    }
    public static Pose2d getScore1() {
        return score1;
    }
    public static Pose2d getScore3() {
        return score3;
    }
    public static Pose2d getScore4() {
        return score4;
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
}
