package org.firstinspires.ftc.teamcode.autonomous;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.LED;

@Config
@Autonomous(name = "ROAD_RUNNER_LEARNING", group = "Autonomous")
public class RoadRunnerTesting extends LinearOpMode {

    @Override
    public void runOpMode() {
        LED.changeAllianceColor(RevBlinkinLedDriver.BlinkinPattern.BLUE);

        waitForStart();

        Pose2d initialPose = new Pose2d(12, 62, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = ignitionSystem.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(0))
                .setTangent(Math.toRadians(90))
                .lineToY(48)
                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5);

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(tab1.build()));
    }
}