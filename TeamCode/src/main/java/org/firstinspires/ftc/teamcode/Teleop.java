package org.firstinspires.ftc.teamcode;

// Imports.

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
import org.firstinspires.ftc.teamcode.subSystems.Suction;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    // TODO: Change names of all hardware in configuration.

    /**
     * Initialize all hardware.
     */
    private void initializeAll() {
        TeleOpFunctions.setReseted(false);

        Suction suction = new Suction(this);
        Differential differential = new Differential(this);
        Drivetrain drivetrain = new Drivetrain(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);

        TeleOpFunctions.setGamepadEx(new GamepadEx(gamepad1));
        TeleOpFunctions.setLeftTrigger(new TriggerReader(TeleOpFunctions.getGamepadEx(), GamepadKeys.Trigger.LEFT_TRIGGER));
        TeleOpFunctions.setRightTrigger(new TriggerReader(TeleOpFunctions.getGamepadEx(), GamepadKeys.Trigger.RIGHT_TRIGGER));
    }

    @Override
    public void runOpMode() {
        initializeAll();

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            TeleOpFunctions.runAll(gamepad1);
        }
    }
}