package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
//@Autonomous(name = "Blue_Specimen_1_Park", group = "Autonomous")

public class BlueSpecimen1Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSpecimenCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getStart())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore1().position).build();

        Action moveSpecimens = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore1())
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
                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd3().position, BlueSpecimenCoordinates.getStart().heading).build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        moveSpecimens
                )
        );
    }
}