package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class BlueSpecimenTrajectories {
    public static MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSpecimenCoordinates.getStartPose());

    public static TrajectoryActionBuilder scorePreload = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getStartPose())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore1().position);

    public static TrajectoryActionBuilder moveSpecimens = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore1())
            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(BlueSpecimenCoordinates.getMidWayMoveSpecimens().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimensStart0().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd1().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd2().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStartPose().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd3().position, BlueSpecimenCoordinates.getStartPose().heading);

    public static TrajectoryActionBuilder scoreSecond = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getMoveSpecimenEnd3())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getScore2().position, BlueSpecimenCoordinates.getStartPose().heading);

    public static TrajectoryActionBuilder intakeThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore2())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getIntake().position, BlueSpecimenCoordinates.getIntake().heading);

    public static TrajectoryActionBuilder scoreThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getScore3().position, BlueSpecimenCoordinates.getStartPose().heading);

    public static TrajectoryActionBuilder intakeFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore3())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getIntake().position, BlueSpecimenCoordinates.getIntake().heading);

    public static TrajectoryActionBuilder scoreFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getScore4().position, BlueSpecimenCoordinates.getStartPose().heading);

    public static TrajectoryActionBuilder intakeFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore4())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getIntake().position, BlueSpecimenCoordinates.getIntake().heading);

    public static TrajectoryActionBuilder scoreFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .setTangent(BlueSpecimenCoordinates.getStartPose().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getScore5().position, BlueSpecimenCoordinates.getStartPose().heading);

    public static TrajectoryActionBuilder park = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore5())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position);
}
