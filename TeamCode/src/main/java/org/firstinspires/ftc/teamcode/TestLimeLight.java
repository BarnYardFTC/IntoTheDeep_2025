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
import org.firstinspires.ftc.teamcode.subSystems.LimeLight;

// TeleOp name.
@TeleOp(name = "LamLam")

public class TestLimeLight extends LinearOpMode {
    @Override
    public void runOpMode() {
        LimeLight.initialize(this);
        waitForStart();
        LimeLight.runLimeLight();
        if (isStopRequested()) return;
        Actions.runBlocking(
                new ParallelAction(
                        Robot.displayTelemetry(),
                        Robot.activateLimeLight()
                )
        );
    }
}

