package org.firstinspires.ftc.teamcode.autonomous.Programs.BlueSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Trajactories.BlueSpecimenTrajectories;

@Config
@Autonomous(name = "Blue_Specimen_2_Park", group = "Autonomous")

public class BlueSpecimen2Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action scorePreLoad = BlueSpecimenTrajectories.scorePreload.build();
        Action moveSpecimens = BlueSpecimenTrajectories.moveSpecimens.build();
        Action scoreSecond = BlueSpecimenTrajectories.scoreSecond.build();
        Action park = BlueSpecimenTrajectories.parkAfterSecond.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        moveSpecimens,
                        scoreSecond,
                        park
                )
        );
    }
}