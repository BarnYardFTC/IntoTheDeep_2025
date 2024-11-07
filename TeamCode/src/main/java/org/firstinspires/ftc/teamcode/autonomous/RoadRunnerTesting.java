package org.firstinspires.ftc.teamcode.autonomous;

// Import
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.LED;

@Config
@Autonomous(name = "ROAD_RUNNER_LEARNING", group = "Autonomous")

public class RoadRunnerTesting extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        TestFunctions.ClawAuto clawAuto = new TestFunctions.ClawAuto();

        Pose2d initialPose = new Pose2d(-24, -58, Math.toRadians(90));
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder trajectoryBuilder = ignitionSystem.actionBuilder(initialPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-10, -35.5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-48, -40), Math.toRadians(45))
                .strafeTo(new Vector2d(-55, -55))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-24, -58, Math.toRadians(90)), 0);

        Action trajectory;
        trajectory = trajectoryBuilder.build();
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        trajectory,
                        clawAuto.closeClaw()
                )
        );
    }
}