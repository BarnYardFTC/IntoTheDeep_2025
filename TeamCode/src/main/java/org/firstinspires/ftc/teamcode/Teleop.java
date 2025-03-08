package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.initializeTeleop(this);
        Robot.teleopSetup();
        waitForStart();
        Differential.moveToDefault();
        if (isStopRequested()) return;
        Actions.runBlocking(
                new ParallelAction(
                        Lift.liftPID(),
                        LiftArm.liftArmPID(),
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