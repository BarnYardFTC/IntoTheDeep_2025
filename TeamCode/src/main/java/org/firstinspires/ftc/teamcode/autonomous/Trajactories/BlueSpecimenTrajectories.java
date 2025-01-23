package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class BlueSpecimenTrajectories {
    public static MecanumDrive ignitionSystem;

    public static TrajectoryActionBuilder scorePreload = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getStart())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore1().position);

    public static TrajectoryActionBuilder moveSpecimens = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore1())
            .setTangent(BlueSpecimenCoordinates.getMidWayMoveSpecimensTangent())
            .splineToConstantHeading(BlueSpecimenCoordinates.getMidWayMoveSpecimens().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimensStart0().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd1().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd2().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStart().heading)

            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart3().position, BlueSpecimenCoordinates.getStart().heading)
            .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd3().position, BlueSpecimenCoordinates.getStart().heading);

    public static TrajectoryActionBuilder scoreSecond = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getMoveSpecimenEnd3())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore2().component1());

    public static TrajectoryActionBuilder intakeThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore2())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore3().component1());

    public static TrajectoryActionBuilder intakeFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore3())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore4().component1());

    public static TrajectoryActionBuilder intakeFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore4())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position);

    public static TrajectoryActionBuilder scoreFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getScore5().component1());

    public static TrajectoryActionBuilder park = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore5())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position);

    public static TrajectoryActionBuilder parkAfterSecond = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore2())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position);

    public static TrajectoryActionBuilder parkFromStart = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getStart())
            .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position);

    public static MecanumDrive getIgnitionSystem() {
        return ignitionSystem;
    }

    public static void setIgnitionSystem(MecanumDrive ignitionSystem) {
        BlueSpecimenTrajectories.ignitionSystem = ignitionSystem;
    }
}
