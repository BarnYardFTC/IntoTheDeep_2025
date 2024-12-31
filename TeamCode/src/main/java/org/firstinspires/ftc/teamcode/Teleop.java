package org.firstinspires.ftc.teamcode;

// Imports.

import static org.firstinspires.ftc.teamcode.subSystems.Differential.servos;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

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
    }

    @Override
    public void runOpMode() {
        initializeAll();
        Differential.move(0, 0);
        LiftArm.move(LiftArm.Angle.HORIZONTAL);
        Lift.targetPos = 15;

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            TeleOpFunctions.runAll(gamepad1);
            if (gamepad1.a) {
                Differential.move(0, 142);
            }
            if (gamepad1.y) {
                Differential.move(0, 0);
            }
            if (gamepad1.b) {
                Suction.getSuction().setPower(1);
            }
            if (gamepad1.x) {
                Suction.getSuction().setPower(-1);
            }
            if (!gamepad1.b && !gamepad1.x) {
                Suction.getSuction().setPower(0);
            }
            if (gamepad1.right_trigger > 0.1 && Lift.targetPos + 10 < 800) {
                Lift.targetPos += 10;
            }
            if (gamepad1.left_trigger > 0.1 && Lift.targetPos - 10 > 20) {
                Lift.targetPos -= 10;
            }
            LiftArm.liftArmPID();
            Lift.liftPID();
        }
    }
}