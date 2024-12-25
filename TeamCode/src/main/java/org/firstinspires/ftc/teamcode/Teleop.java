package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.DifferentialWrist;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

import java.util.ArrayList;
import java.util.List;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    // TODO: Change names of all hardware in configuration.

    /**
     * Initialize all hardware.
     */
    private void initializeAll() {
        TeleOpFunctions.setReseted(false);

        Claw claw = new Claw(this);
        Differential differential = new Differential(this);
        DifferentialWrist differentialWrist = new DifferentialWrist(this);
        Drivetrain drivetrain = new Drivetrain(this);
        LED led = new LED(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);

        TeleOpFunctions.setGamepadEx(new GamepadEx(gamepad1));
        TeleOpFunctions.setLeftTrigger(new TriggerReader(TeleOpFunctions.getGamepadEx(), GamepadKeys.Trigger.LEFT_TRIGGER));
        TeleOpFunctions.setRightTrigger(new TriggerReader(TeleOpFunctions.getGamepadEx(), GamepadKeys.Trigger.RIGHT_TRIGGER));
    }

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);
//        LiftArm.move(LiftArm.Angle.HORIZONTAL);
//        Lift.move(Lift.Pos.RESET);

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {

        }
    }
}