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
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Autonomous(name = "Blue_Specimen_4_Park", group = "Autonomous")

public class BlueSpecimen4Park extends LinearOpMode {
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

        Action park = ignitionSystem.actionBuilder(BlueSpecimenCoordinates.getScore4())
                .strafeToConstantHeading(BlueSpecimenCoordinates.getPark().position).build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        Lift.liftPID(),
                        LiftArm.liftArmPID(),
                        Robot.displayTelemetry(),
                        new SequentialAction(
                                new ParallelAction(
                                        scorePreLoad,
                                        LiftArm.liftArmVertical()
                                ),
                                //Robot.fullyScoreSpecimen(),

                                new ParallelAction(
                                        Robot.reset(),
                                        moveSpecimens
                                ),

                                new ParallelAction(
                                        collectSecond,
                                        Differential.differentialCollectSpecimen()
                                ),
                                Claw.closeClaw(),
                                Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
                                new ParallelAction(
                                        scoreSecond,
                                        Differential.differentialReset(),
                                        LiftArm.liftArmVertical()
                                ),
//                                Robot.scoreSpecimen(),

                                new ParallelAction(
                                        Robot.reset(),
                                        collectThird
                                ),
                                Claw.closeClaw(),
                                Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
                                new ParallelAction(
                                        scoreThird,
                                        Differential.differentialReset(),
                                        LiftArm.liftArmVertical()

                                ),
//                                Robot.scoreSpecimen(),

                                new ParallelAction(
                                        Robot.reset(),
                                        collectFourth
                                ),
                                Claw.closeClaw(),
                                Robot.hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
                                new ParallelAction(
                                        scoreFourth,
                                        Differential.differentialReset(),
                                        LiftArm.liftArmVertical()
                                ),
//                                Robot.scoreSpecimen(),

                                new ParallelAction(
                                        Robot.reset(),
                                        park
                                )
                        )
                )
        );
    }
}