package org.firstinspires.ftc.teamcode.autonomous;

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
@Autonomous(name = "Red_Sample", group = "Autonomous")

public class RedSample extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        TestFunctions claw = new TestFunctions();

        Pose2d initialPose = new Pose2d(-24, -58, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder trajectoryBuilder = ignitionSystem.actionBuilder(initialPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-10, -35.5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-48, -40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-56.5, -56.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-58, -40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-56.5, -56.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-58, -40), Math.toRadians(90))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-25, -16, Math.toRadians(0)), 0);

        Action trajectory;
        trajectory = trajectoryBuilder.build();
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        trajectory,
                        claw.closeClaw()
                )
        );
    }
}