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
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Disabled
@Autonomous(name = "Blue_Sample_3_Park", group = "Autonomous")

public class Blue3Park extends LinearOpMode {
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

        Action intake2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore1())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake2(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action score2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake2())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore2(), BlueSampleCoordinates.getIntake2HeadingChange())
                .build();

        Action intake3 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore2())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake3(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action park = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore2())
                .strafeToLinearHeading(BlueSampleCoordinates.getPark1().component1(), BlueSampleCoordinates.getPark1().heading)

                .strafeToConstantHeading(BlueSampleCoordinates.getPark2().component1())
                .build();

        Robot.initialize(this);
        waitForStart();
        Robot.autonomousSetup();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                new ParallelAction(
                                        Robot.highBasketDeposit(),
                                        scorePreLoad
                                ),
                                Robot.sleep(HIGH_BASKET_SETTLE_TIME),
                                Claw.openClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                                new ParallelAction(
                                        intake2,
//                                        Differential.differentialDown(),
                                        new SequentialAction(
                                                Robot.sleep(POST_SCORE_DELAY),
                                                Robot.reset(),
                                                Lift.sampleCollectionAction(),
                                                Robot.sleep(HORIZONTAL_LIFT_SETTLE_TIME),
                                                Claw.closeClaw(),
                                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION)
                                        )
                                ),
                                Claw.closeClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                                new ParallelAction(
                                        score2,
                                        Robot.highBasketDeposit()
                                ),
                                Robot.sleep(HIGH_BASKET_SETTLE_TIME),
                                Claw.openClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                                new ParallelAction(
                                        intake3,
//                                        Differential.differentialDown(),
                                        new SequentialAction(
                                                Robot.sleep(POST_SCORE_DELAY),
                                                Robot.reset(),
                                                Lift.sampleCollectionAction(),
                                                Robot.sleep(HORIZONTAL_LIFT_SETTLE_TIME),
                                                Claw.closeClaw(),
                                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION)
                                        )
                                ),
                                Claw.closeClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                                new ParallelAction(
                                        score2,
                                        Robot.highBasketDeposit()
                                ),
                                Robot.sleep(HIGH_BASKET_SETTLE_TIME),
                                Claw.openClaw(),

                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                                new ParallelAction(
                                        park,
                                        new SequentialAction(
                                                Robot.sleep(POST_SCORE_DELAY),
                                                Robot.reset()
                                        )
                                )
                        ),
                        LiftArm.liftArmPID(),
                        Lift.liftPID()
                )
        );
    }
}