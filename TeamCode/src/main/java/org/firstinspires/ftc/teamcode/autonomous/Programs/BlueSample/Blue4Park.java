package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Autonomous(name = "Blue_Sample_4_Park", group = "Autonomous")

public class Blue4Park extends LinearOpMode {

    public static int HIGH_BASKET_SETTLE_TIME = 0;
    public static int HORIZONTAL_LIFT_SETTLE_TIME = 20;
    public static int POST_SCORE_DELAY = 250;

    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore1(), BlueSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action intake2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore1())
                .strafeToLinearHeading(BlueSampleCoordinates.getIntake2().component1(), BlueSampleCoordinates.getIntake2().heading)

                .build();

        Action score2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake2())
                .strafeToLinearHeading(BlueSampleCoordinates.getScore2().component1(), BlueSampleCoordinates.getScore2().heading)
                .build();

        Action intake3 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore2())
                .strafeToLinearHeading(BlueSampleCoordinates.getIntake3().component1(), BlueSampleCoordinates.getIntake3().heading)

                .build();

        Action score3 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake3())
                .strafeToLinearHeading(BlueSampleCoordinates.getScore3().component1(), BlueSampleCoordinates.getScore3().heading)
                .build();

        Action intake4 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore3())
                .strafeToLinearHeading(BlueSampleCoordinates.getIntake4().component1(), BlueSampleCoordinates.getIntake4().heading)

                .build();

        Action score4 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake4())
                .strafeToLinearHeading(BlueSampleCoordinates.getScore4().component1(), BlueSampleCoordinates.getScore4().heading)
                .build();

        Action park = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore4())
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
                    new ParallelAction(
                        scorePreLoad
                    ),
                    new ParallelAction(
                            Robot.highBasketDeposit(),
                            new SequentialAction(
                                    Robot.sleep(HIGH_BASKET_SETTLE_TIME),
                                    scorePreLoad
                            )
                    ),
                    Claw.openClaw(),
                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                    new ParallelAction(
                        intake2,
                        new SequentialAction(
                            Robot.sleep(POST_SCORE_DELAY),
                            Robot.reset(),
                            Lift.sampleCollectionAction(),
                            Differential.CollectSampleAction(),
                            Robot.sleep(Differential.MOVEMENT_DURATION),
                            Claw.closeClaw(),
                            Robot.sleep(Claw.CLAW_MOVEMENT_DURATION)
                        )
                    ),
                    Robot.reset(),
                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                    new ParallelAction(
                        score2
                    ),
                    Robot.highBasketDeposit(),
                    Claw.openClaw(),
                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                    new ParallelAction(
                        intake3,
                        new SequentialAction(
                                Robot.sleep(POST_SCORE_DELAY),
                                Robot.reset(),
                                Lift.sampleCollectionAction(),
                                Differential.CollectSampleAction(),
                                Robot.sleep(Differential.MOVEMENT_DURATION),
                                Claw.closeClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION)
                        )
                    ),
                    Robot.reset(),
                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                    new ParallelAction(
                        score3
                    ),
                    Robot.highBasketDeposit(),
                    Claw.openClaw(),

                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                    new ParallelAction(
                        intake4,
                        new SequentialAction(
                                Robot.sleep(POST_SCORE_DELAY),
                                Robot.reset(),
                                Lift.sampleCollection4Action(),
                                Differential.CollectSample4Action(),
                                Robot.sleep(Differential.MOVEMENT_DURATION),
                                Claw.closeClaw(),
                                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION)
                        )
                    ),
                    Robot.reset(),
                    Claw.closeClaw(),
                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                    score4,
                    Robot.highBasketDeposit(),
                    Claw.openClaw(),

                    Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),

                    new ParallelAction(
//                        park,
                        new SequentialAction(
//                            Robot.sleep(POST_SCORE_DELAY),
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