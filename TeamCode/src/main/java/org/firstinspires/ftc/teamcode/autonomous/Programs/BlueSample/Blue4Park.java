package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Autonomous(name = "Blue_Sample_4_Park", group = "Autonomous")

public class Blue4Park extends LinearOpMode {

    public static int HIGH_BASKET_SETTLE_TIME = 300;
    public static int HORIZONTAL_LIFT_SETTLE_TIME = 500;
    public static int POST_SCORE_DELAY = 700;

    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore0(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action intake2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore0())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake2(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action score2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake2())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())
                .build();

        Action score3 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake3())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())
                .build();

        Action intake3 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake3(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action intake4 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake4(), BlueSampleCoordinates.getIntake4HeadingChange())

                .build();

        Action score4 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake4())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake4HeadingChange())
                .build();

        Action park = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore())
                .strafeToLinearHeading(BlueSampleCoordinates.getPark1().component1(), BlueSampleCoordinates.getPark1().heading)

                .strafeToConstantHeading(BlueSampleCoordinates.getPark2().component1())
                .build();

        Robot.initialize(this);
        Robot.autonomousSetup();
        waitForStart();
        LiftArm.is_extra_power_required = true;

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        intake2,
                        score2,
                        intake3,
                        score3,
                        intake4,
                        score4,
                        park
                )
        );


//        Actions.runBlocking(
//            new ParallelAction(
//                new SequentialAction(
//                        new ParallelAction(
//                                Robot.highBasketDeposit(),
//                                scorePreLoad
//                        ),
//                        Robot.hasElapsed(HIGH_BASKET_SETTLE_TIME),
//                        Claw.openClaw(),
//                        Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
//
//                        new ParallelAction(
//                                intake2,
//                                new SequentialAction(
//                                        Robot.hasElapsed(POST_SCORE_DELAY),
//                                        Robot.reset()
//                                )
//                        ),
//                        Robot.sampleCollectionForAutonomous()

//                        new ParallelAction(
//                                score2,
//                                Robot.highBasketDeposit()
//                        ),
//                        Robot.hasElapsed(HIGH_BASKET_SETTLE_TIME),
//                        Claw.openClaw(),
//                        Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
//
//                        new ParallelAction(
//                                intake3,
//                                new SequentialAction(
//                                        Robot.hasElapsed(POST_SCORE_DELAY),
//                                        Robot.reset()
//                                )
//                        ),
//                        Robot.sampleCollectionForAutonomous(),
//
//                        new ParallelAction(
//                                score3,
//                                Robot.highBasketDeposit()
//                        ),
//                        Robot.hasElapsed(HIGH_BASKET_SETTLE_TIME),
//                        Claw.openClaw(),
//                        Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION)
//
//                        new ParallelAction(
//                                intake4,
//                                new SequentialAction(
//                                        Robot.hasElapsed(POST_SCORE_DELAY),
//                                        Robot.reset()
//                                )
//                        ),
//                        Robot.sampleCollectionAtAngleForAutonomous(),
//
//                        new ParallelAction(
//                                score4,
//                                Robot.highBasketDeposit()
//                        ),
//                        Robot.hasElapsed(HIGH_BASKET_SETTLE_TIME),
//                        Claw.openClaw(),
//                        Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
//
//                        new ParallelAction(
//                                park,
//                                new SequentialAction(
//                                        Robot.hasElapsed(POST_SCORE_DELAY),
//                                        Robot.reset()
//                                )
//                        ),
//                        LiftArm.liftArmHorizontal()
//                ),
//                LiftArm.liftArmPID(),
//                Lift.liftPID()
//            )
//        );

    }
}