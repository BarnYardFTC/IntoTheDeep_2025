package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSpecimen;

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
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
@Autonomous(name = "Blue_Specimen_4_Park", group = "Autonomous")

public class BlueSpecimen4Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action scorePreLoad = BlueSpecimenTrajectories.scorePreload.build();
        Action moveSpecimens = BlueSpecimenTrajectories.moveSpecimens.build();
        Action scoreSecond = BlueSpecimenTrajectories.scoreSecond.build();
        Action scoreThird = BlueSpecimenTrajectories.scoreThird.build();
        Action scoreFourth= BlueSpecimenTrajectories.scoreFifth.build();
        Action intakeThird = BlueSpecimenTrajectories.intakeThird.build();
        Action intakeFourth = BlueSpecimenTrajectories.intakeFourth.build();
        Action park = BlueSpecimenTrajectories.park.build();

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
                        park
                )
        );
    }
}