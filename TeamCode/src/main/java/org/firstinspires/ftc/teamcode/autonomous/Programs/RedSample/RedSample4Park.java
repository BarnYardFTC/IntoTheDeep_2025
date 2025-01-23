package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Autonomous(name = "Red_Sample_4_Park", group = "Autonomous")

public class RedSample4Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(RedSampleCoordinates.getStart())
                .setTangent(RedSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(RedSampleCoordinates.getScore(), RedSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(RedSampleCoordinates.getIntake2(), RedSampleCoordinates.getIntake2HeadingChange())

                .setTangent(RedSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(RedSampleCoordinates.getScore(), RedSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(RedSampleCoordinates.getIntake3(), RedSampleCoordinates.getIntake2HeadingChange())

                .setTangent(RedSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(RedSampleCoordinates.getScore(), RedSampleCoordinates.getIntake2HeadingChange())

                .splineToLinearHeading(RedSampleCoordinates.getIntake4(), RedSampleCoordinates.getIntake4HeadingChange())

                .setTangent(RedSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(RedSampleCoordinates.getScore(), RedSampleCoordinates.getIntake4HeadingChange())

                .waitSeconds(15)

                .strafeToLinearHeading(RedSampleCoordinates.getPark().component1(), RedSampleCoordinates.getPark().heading).build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad
                )
        );
    }
}