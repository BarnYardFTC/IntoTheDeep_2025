package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Autonomous(name = "Red_Specimen_1_Park", group = "Autonomous")

public class RedSpecimen1Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Pose2d initialPose = new Pose2d(8, -63, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder trajectoryBuilder = ignitionSystem.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(8, -33))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(33, -36), Math.toRadians(90))


                .splineToConstantHeading(new Vector2d(33, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(46, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(46, -54), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(46, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(54, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(54, -54), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(54, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(62, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(62, -54), Math.toRadians(90));

        Action trajectory;
        trajectory = trajectoryBuilder.build();
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        trajectory
                )
        );
    }
}