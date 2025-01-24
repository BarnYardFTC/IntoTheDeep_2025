package org.firstinspires.ftc.teamcode.autonomous;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Autonomous(name = "Testing_Autonomous", group = "Autonomous")

public class ActionTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSpecimenCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getStart())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore1().position).build();

        Action moveSpecimens = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore1())
                .setTangent(BlueSpecimenCoordinates.getMidWayMoveSpecimensTangent())
                .splineToConstantHeading(BlueSpecimenCoordinates.getMidWayMoveSpecimens().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimensStart0().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd1().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart1().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd2().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart2().position, BlueSpecimenCoordinates.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenStart3().position, BlueSpecimenCoordinates.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinates.getMoveSpecimenEnd3().position, BlueSpecimenCoordinates.getStart().heading).build();

        Action collectSecond = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getMoveSpecimenEnd3())
                .setTangent(BlueSpecimenCoordinates.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinates.getIntake().position, BlueSpecimenCoordinates.getStart().heading).build();

        Action scoreSecond = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore2().component1()).build();

        Action collectThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore2())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position).build();

        Action scoreThird = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore3().component1()).build();

        Action collectFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore3())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position).build();

        Action scoreFourth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore4().component1()).build();

        Action collectFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore3())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getIntake().position).build();

        Action scoreFifth = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getIntake())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getScore5().component1()).build();

        Action park = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore5())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position).build();

        Robot.initialize(this);
        Robot.autonomousSetup();
        AutoFunctions autoFunctions = new AutoFunctions();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(

                //ToDo: Add Actions of other robot system (claw, liftArm etc.)

                new ParallelAction(
                        autoFunctions.liftArmPID(),
                        autoFunctions.displayTelemetry(),
                        new SequentialAction(
                                new ParallelAction(
                                    scorePreLoad,
                                    autoFunctions.liftArmVertical()
                                    // ToDo: *Lift moves up to high chamber pos*
                                ),
                                // ToDo: *Lift moves down to post-score pos*,
                                new ParallelAction(
                                    autoFunctions.openClaw(),
                                    autoFunctions.liftArmHorizontal(),
                                    moveSpecimens
                                    // ToDo: *Lift moves down to reset pos*
                                ),
                                new ParallelAction(
                                    collectSecond,
                                    autoFunctions.differentialCollectSpecimen()
                                ),
                                autoFunctions.closeClaw(),
                                new ParallelAction(
                                    scoreSecond,
                                    autoFunctions.liftArmVertical(),
                                    autoFunctions.differentialReset()
                                    // ToDo: *Lift moves up to high chamber pos*
                                ),
                                // ToDo: *Lift moves down to post-score pos*
                                new ParallelAction(
                                    autoFunctions.openClaw(),
                                    autoFunctions.liftArmHorizontal(),
                                    collectThird,
                                    autoFunctions.differentialCollectSpecimen()
                                    // ToDo: *Lift moves down to reset pos*
                                ),
                                autoFunctions.closeClaw(),
                                new ParallelAction(
                                        scoreThird,
                                        autoFunctions.liftArmVertical(),
                                        autoFunctions.differentialReset()
                                        // ToDo: *Lift moves up to high chamber pos*
                                ),
                                // ToDo: *Lift moves down to post-score pos*
                                new ParallelAction(
                                        autoFunctions.openClaw(),
                                        autoFunctions.liftArmHorizontal(),
                                        collectFourth,
                                        autoFunctions.differentialCollectSpecimen()
                                        // ToDo: *Lift moves down to reset pos*
                                ),
                                autoFunctions.closeClaw(),
                                new ParallelAction(
                                        scoreFourth,
                                        autoFunctions.liftArmVertical(),
                                        autoFunctions.differentialReset()
                                        // ToDo: *Lift moves up to high chamber pos*
                                ),
                                // ToDo: *Lift moves down to post-score pos*
                                new ParallelAction(
                                        autoFunctions.openClaw(),
                                        autoFunctions.liftArmHorizontal(),
                                        collectFifth,
                                        autoFunctions.differentialCollectSpecimen()
                                        // ToDo: *Lift moves down to reset pos*
                                ),
                                autoFunctions.closeClaw(),
                                new ParallelAction(
                                        scoreFifth,
                                        autoFunctions.liftArmVertical(),
                                        autoFunctions.differentialReset()
                                        // ToDo: *Lift moves up to high chamber pos*
                                ),
                                // ToDo: *Lift moves down to post-score pos*
                                new ParallelAction(
                                        autoFunctions.openClaw(),
                                        autoFunctions.liftArmHorizontal(),
                                        park
                                        // ToDo: *Lift moves down to reset pos*
                                )
                        )
                )
        );
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//        Actions.runBlocking(
//                new ParallelAction(
//                        autoFunctions.liftArmPID(),
//                        new SequentialAction(
//                                autoFunctions.liftArmVertical()
//                        )
//                )
//        );
    }
}