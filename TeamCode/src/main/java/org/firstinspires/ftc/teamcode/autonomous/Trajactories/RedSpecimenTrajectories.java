package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class RedSpecimenTrajectories {
    public static MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSpecimenCoordinates.getStart());

    public static TrajectoryActionBuilder scorePreload = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getStart())
            .strafeToConstantHeading(RedSpecimenCoordinates.getScore1().position);

    public static TrajectoryActionBuilder moveSpecimens = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore1())
            .setTangent(RedSpecimenCoordinates.getMidWayMoveSpecimensTangent())
            .splineToConstantHeading(RedSpecimenCoordinates.getMidWayMoveSpecimens().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimensStart0().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenStart1().position, RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenEnd1().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenStart1().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenStart2().position, RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenEnd2().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenStart2().position, RedSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenStart3().position, RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenEnd3().position, RedSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder scoreSecond = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getMoveSpecimenEnd3())
            .setTangent(RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getScore2().position, RedSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder intakeThird = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore2())
            .strafeToConstantHeading(RedSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreThird = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getIntake())
            .setTangent(RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getScore3().position, RedSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder intakeFourth = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore3())
            .strafeToConstantHeading(RedSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreFourth = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getIntake())
            .setTangent(RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getScore4().position, RedSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder intakeFifth = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore4())
            .strafeToConstantHeading(RedSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreFifth = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getIntake())
            .setTangent(RedSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(RedSpecimenCoordinates.getScore5().position, RedSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder park = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore5())
            .strafeToConstantHeading(RedSpecimenCoordinates.getPark().position);

    public static TrajectoryActionBuilder parkAfterSecond = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore2())
            .strafeToConstantHeading(RedSpecimenCoordinates.getPark().position);

    public static TrajectoryActionBuilder parkFromStart = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getStart())
            .strafeToConstantHeading(RedSpecimenCoordinates.getPark().position);
}
