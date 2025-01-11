package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Trajactories.RedSampleTrajectories;

@Config
@Autonomous(name = "Blue_Sample_Park", group = "Autonomous")

public class RedSamplePark extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        Action park = RedSampleTrajectories.parkFromStart.build();
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        park
                )
        );
    }
}