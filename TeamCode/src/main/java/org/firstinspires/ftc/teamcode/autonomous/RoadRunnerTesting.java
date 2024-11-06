package org.firstinspires.ftc.teamcode.autonomous;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
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

        Pose2d initialPose = new Pose2d(-24, -58, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = ignitionSystem.actionBuilder(initialPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-10, -37), Math.toRadians(90))
                .waitSeconds(2)
                .setTangent(Math.toRadians(0))
                .strafeTo(new Vector2d(-48, -40))
                .waitSeconds(2)
                .turn(Math.toRadians(-45))
                .strafeTo(new Vector2d(-55, -55))
                .waitSeconds(2)
                .lineToY(-50)
                .turn(Math.toRadians(45))
                .strafeTo(new Vector2d(-24, -58));

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(tab1.build()));
    }
}