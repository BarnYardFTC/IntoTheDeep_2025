package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.initializeTeleop(this);
        Robot.teleopSetup();
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(
                new ParallelAction(
                        Robot.displayTelemetry(),
                        Robot.activateDrivetrain(),
                        Robot.activateLiftArm(),
                        Robot.activateLift(),
                        Robot.activateDifferential(),
                        Robot.activateClaw(),
                        Robot.activateGamepads()
                )
        );
    }
}