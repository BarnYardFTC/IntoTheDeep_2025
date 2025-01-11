package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Trajactories.BlueSpecimenTrajectories;
import org.firstinspires.ftc.teamcode.autonomous.Trajactories.RedSpecimenTrajectories;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Autonomous(name = "Red_Specimen_5_Park", group = "Autonomous")

public class RedSpecimen5Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action scorePreLoad = RedSpecimenTrajectories.scorePreload.build();
        Action moveSpecimens = RedSpecimenTrajectories.moveSpecimens.build();
        Action scoreSecond = RedSpecimenTrajectories.scoreSecond.build();
        Action scoreThird = RedSpecimenTrajectories.scoreThird.build();
        Action scoreFourth= RedSpecimenTrajectories.scoreFourth.build();
        Action scoreFifth= RedSpecimenTrajectories.scoreFifth.build();
        Action intakeThird = RedSpecimenTrajectories.intakeThird.build();
        Action intakeFourth = RedSpecimenTrajectories.intakeFourth.build();
        Action intakeFifth = RedSpecimenTrajectories.intakeFifth.build();
        Action park = RedSpecimenTrajectories.park.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        moveSpecimens,
                        scoreSecond,
                        intakeThird,
                        scoreThird,
                        intakeFourth,
                        scoreFourth,
                        intakeFifth,
                        scoreFifth,
                        park
                )
        );
    }
}