package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Disabled
//@Autonomous(name = "Blue_Sample_Park", group = "Autonomous")

public class BlueSamplePark extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action park = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore2(), BlueSampleCoordinates.getIntake2HeadingChange())

                .strafeToLinearHeading(BlueSampleCoordinates.getPark1().component1(), BlueSampleCoordinates.getPark1().heading)

                .strafeToConstantHeading(BlueSampleCoordinates.getPark2().component1())
                .build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        park
                )
        );
    }
}