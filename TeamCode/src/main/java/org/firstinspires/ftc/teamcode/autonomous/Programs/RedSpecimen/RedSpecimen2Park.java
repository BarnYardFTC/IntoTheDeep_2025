package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
//@Autonomous(name = "Red_Specimen_2_Park", group = "Autonomous")

public class RedSpecimen2Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSpecimenCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getStart())
                .strafeToConstantHeading(RedSpecimenCoordinates.getScore1().position).build();

        Action moveSpecimens = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore1())
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
                .splineToConstantHeading(RedSpecimenCoordinates.getMoveSpecimenEnd3().position, RedSpecimenCoordinates.getStart().heading).build();

        Action collectSecond = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getMoveSpecimenEnd3())
                .setTangent(RedSpecimenCoordinates.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinates.getIntake().position, RedSpecimenCoordinates.getStart().heading).build();

        Action scoreSecond = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getIntake())
                .strafeToConstantHeading(RedSpecimenCoordinates.getScore2().component1()).build();

        Action park = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getScore2())
                .strafeToConstantHeading(RedSpecimenCoordinates.getPark().position).build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        moveSpecimens,
                        collectSecond,
                        scoreSecond,
                        park
                )
        );
    }
}