package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;

@Config
@Disabled
@Autonomous(name = "Blue_Sample_1_Park", group = "Autonomous")

public class Blue1Park extends LinearOpMode {
    public static int HIGH_BASKET_SETTLE_TIME = 1000;
    public static int HORIZONTAL_LIFT_SETTLE_TIME = 500;
    public static int POST_SCORE_DELAY = 700;

    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore1(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action park = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore1())
                .strafeToLinearHeading(BlueSampleCoordinates.getPark1().component1(), BlueSampleCoordinates.getPark1().heading)

                .strafeToConstantHeading(BlueSampleCoordinates.getPark2().component1())
                .build();

        Robot.initialize(this);
        Robot.autonomousSetup();
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                Differential.moveToDefaultAction(),
                                Claw.openClaw(),
                                Lift.sampleCollectionAction(),
                                Differential.CollectSampleAction(),
                                Robot.sleep(Differential.MOVEMENT_DURATION),
                                Claw.closeClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                                Differential.moveToDefaultAction(),
                                Lift.moveLift(Lift.Pos.RESET)
                        ),
                        Lift.liftPID(),
                        Robot.displayTelemetry()
                )
        );
    }
}