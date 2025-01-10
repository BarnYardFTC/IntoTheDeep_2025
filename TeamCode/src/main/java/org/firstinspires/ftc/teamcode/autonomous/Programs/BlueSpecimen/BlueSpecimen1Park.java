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
@Autonomous(name = "Blue_Specimen_1_Park", group = "Autonomous")

public class BlueSpecimen1Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action scorePreLoad = BlueSpecimenTrajectories.scorePreload.build();
        Action moveSpecimens = BlueSpecimenTrajectories.moveSpecimens.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        moveSpecimens
                )
        );
    }
}