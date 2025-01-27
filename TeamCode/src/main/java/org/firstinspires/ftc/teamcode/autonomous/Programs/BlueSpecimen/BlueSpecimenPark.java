package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.AutoFunctions;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Lift;

@Config
@Autonomous(name = "Blue_Specimen_Park", group = "Autonomous")

public class BlueSpecimenPark extends LinearOpMode {
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
                .build();

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
        AutoFunctions autoFunctions = new AutoFunctions();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        autoFunctions.closeClaw(),
                        scorePreLoad,
                        moveSpecimens,
                        autoFunctions.differentialCollectSpecimen(),
                        collectSecond,
                        autoFunctions.openClaw(),
                        scoreSecond,
                        autoFunctions.waitAutonomous(),
                        park
                )
        );

//        Actions.runBlocking(
//
//
//
//                new ParallelAction(
//                        autoFunctions.liftPID(),
//                        autoFunctions.liftArmPID(),
//                        autoFunctions.displayTelemetry(),
//                        new SequentialAction(
//                                autoFunctions.setup(),
//                                new ParallelAction(
//                                        scorePreLoad,
//                                        autoFunctions.liftArmVertical()
//                                ),
//                                autoFunctions.moveLift(Lift.Pos.POST_SCORE_HIGH_CHAMBER),
//                                new ParallelAction(
//                                        autoFunctions.openClaw(),
//                                        moveSpecimens,
//                                        new SequentialAction(
//                                                autoFunctions.moveLift(Lift.Pos.RESET),
//                                                autoFunctions.liftArmHorizontal()
//                                        )
//                                ),
//                                new ParallelAction(
//                                        collectSecond,
//                                        autoFunctions.differentialCollectSpecimen()
//                                ),
//                                autoFunctions.closeClaw(),
//                                autoFunctions.waitClaw(),
//                                new ParallelAction(
//                                        scoreSecond,
//                                        autoFunctions.differentialReset(),
//                                        autoFunctions.liftArmVertical()
//                                ),
//                                autoFunctions.moveLift(Lift.Pos.POST_SCORE_HIGH_CHAMBER),
//                                new ParallelAction(
//                                        autoFunctions.openClaw(),
//                                        collectThird,
//                                        new SequentialAction(
//                                                autoFunctions.moveLift(Lift.Pos.RESET),
//                                                autoFunctions.liftArmHorizontal(),
//                                                autoFunctions.differentialCollectSpecimen()
//                                        )
//                                ),
//                                autoFunctions.closeClaw(),
//                                autoFunctions.waitClaw(),
//                                new ParallelAction(
//                                        scoreThird,
//                                        autoFunctions.differentialReset(),
//                                        autoFunctions.liftArmVertical()
//
//                                ),
//                                autoFunctions.moveLift(Lift.Pos.POST_SCORE_HIGH_CHAMBER),
//                                new ParallelAction(
//                                        autoFunctions.openClaw(),
//                                        collectFourth,
//                                        new SequentialAction(
//                                                autoFunctions.moveLift(Lift.Pos.RESET),
//                                                autoFunctions.liftArmHorizontal(),
//                                                autoFunctions.differentialCollectSpecimen()
//                                        )
//                                ),
//                                autoFunctions.closeClaw(),
//                                autoFunctions.waitClaw(),
//                                new ParallelAction(
//                                        scoreFourth,
//                                        autoFunctions.differentialReset(),
//                                        autoFunctions.liftArmVertical()
//                                ),
//                                autoFunctions.moveLift(Lift.Pos.POST_SCORE_HIGH_CHAMBER),
//                                new ParallelAction(
//                                        autoFunctions.openClaw(),
//                                        park,
//                                        new SequentialAction(
//                                                autoFunctions.moveLift(Lift.Pos.RESET),
//                                                autoFunctions.liftArmHorizontal()
//                                        )
//                                )
//                        )
//                )
//        );
    }
}