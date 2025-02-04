package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.initializeTeleop(this);
//        Robot.teleOpSetup();
        waitForStart();
//            Robot.activateAll();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        Robot.activateLift()
                )
        );
    }
}