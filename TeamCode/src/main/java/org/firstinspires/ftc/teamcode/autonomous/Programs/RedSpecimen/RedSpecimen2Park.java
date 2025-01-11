package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Trajactories.RedSpecimenTrajectories;

@Config
@Autonomous(name = "Red_Specimen_2_Park", group = "Autonomous")

public class RedSpecimen2Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action scorePreLoad = RedSpecimenTrajectories.scorePreload.build();
        Action moveSpecimens = RedSpecimenTrajectories.moveSpecimens.build();
        Action scoreSecond = RedSpecimenTrajectories.scoreSecond.build();
        Action park = RedSpecimenTrajectories.parkAfterSecond.build();

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