package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSample;

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
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Config
@Autonomous(name = "Blue_Sample_4_Park !", group = "Autonomous")

public class BlueSample4Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
                .setTangent(BlueSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinates.getScore(), BlueSampleCoordinates.getIntake2HeadingChange())
                
                .build();

        Action intake2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getScore())
                .splineToLinearHeading(BlueSampleCoordinates.getIntake2(), BlueSampleCoordinates.getIntake2HeadingChange())
                
                .build();
        
        Action score2 = ignitionSystem.actionBuilder(BlueSampleCoordinates.getIntake2())
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
        AutoFunctions autoFunctions = new AutoFunctions();
        waitForStart();
        Robot.autonomousSetup();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                    autoFunctions.liftPID(),
                    autoFunctions.liftArmPID(),
                    autoFunctions.displayTelemetry(),
                    new SequentialAction(
                        new ParallelAction(
                            scorePreLoad,
                            new SequentialAction(
                                autoFunctions.liftArmVertical(),
                                autoFunctions.moveLift(Lift.Pos.HIGH_BASKET)
                            )
                        ),
                        autoFunctions.waitClaw(),
                        autoFunctions.openClaw(),
                        new ParallelAction(
                            intake2,
                            new SequentialAction(
                                autoFunctions.moveLift(Lift.Pos.RESET),
                                autoFunctions.liftArmHorizontal(),
                                autoFunctions.moveLift(Lift.Pos.SAMPLE_COLLECTION)
                            )
                        ),
                        new ParallelAction(
                            score2,
                            new SequentialAction(
                                autoFunctions.liftArmVertical(),
                                autoFunctions.moveLift(Lift.Pos.HIGH_BASKET)
                            )
                        ),
                        new ParallelAction(
                            intake3,
                            new SequentialAction(
                                autoFunctions.moveLift(Lift.Pos.RESET),
                                autoFunctions.liftArmHorizontal(),
                                autoFunctions.moveLift(Lift.Pos.SAMPLE_COLLECTION)
                            )
                        ),

                        score2,
                        intake4,
                        score4,
                        park
                    )
                )

        );
    }
}