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
@Autonomous(name = "Red_Specimen_Park", group = "Autonomous")

public class RedSpecimenPark extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Pose2d initialPose = new Pose2d(8, -63, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder trajectoryBuilder = ignitionSystem.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(59, -63));

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