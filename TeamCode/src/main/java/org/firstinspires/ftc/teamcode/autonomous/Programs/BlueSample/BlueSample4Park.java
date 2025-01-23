package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Autonomous(name = "Blue_Sample_4_Park", group = "Autonomous")

public class BlueSample4Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinates.getIntake2(), BlueSampleCoordinates.getIntake2HeadingChange())

                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinates.getIntake3(), BlueSampleCoordinates.getIntake2HeadingChange())

                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinates.getIntake4(), BlueSampleCoordinates.getIntake4HeadingChange())

                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake4HeadingChange())

                .waitSeconds(15)

                .strafeToLinearHeading(BlueSampleCoordinates.getPark().component1(), BlueSampleCoordinates.getPark().heading).build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad
                )
        );
    }
}