package org.firstinspires.ftc.teamcode;

// Imports.

import static org.firstinspires.ftc.teamcode.subSystems.Differential.goalAngle;
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
                Differential.move(0, 104);
                Differential.goalAngle = 104;
            }
            if (gamepad1.y) {
                Differential.move(0, 0);
                Differential.goalAngle = 0;
            }
            if (gamepad1.b) {
                Suction.getSuction().setPower(-1);
            }
            if (gamepad1.x) {
                Suction.getSuction().setPower(1);
            }
            if (!gamepad1.b && !gamepad1.x) {
                Suction.getSuction().setPower(0);
            }
            if (Lift.targetPos + 10 > 800 && LiftArm.isHorizontal()){
                Lift.targetPos = 800;
            }
            if (gamepad1.right_trigger > 0.1 && Lift.targetPos + 10 < 800 && LiftArm.isHorizontal()) {
                Lift.targetPos += 10;
            }
            if (gamepad1.right_trigger > 0.1 && !LiftArm.isHorizontal()) {
                Lift.targetPos += 10;
            }
            if (gamepad1.left_trigger > 0.1 && Lift.targetPos - 10 > 20) {
                Lift.targetPos -= 10;
            }
            if (gamepad1.right_bumper) {
                LiftArm.move(LiftArm.Angle.VERTICAL);
                Differential.move(0, 0);
                Differential.goalAngle = 0;
            }
            if (gamepad1.left_bumper) {
                LiftArm.move(LiftArm.Angle.HORIZONTAL);
                Differential.move(0, 0);
                Differential.goalAngle = 0;
            }
            if (gamepad1.dpad_up) {
                Differential.move(0, goalAngle + 2);
                goalAngle += 2;
            }
            if (gamepad1.dpad_down) {
                Differential.move(0, goalAngle - 2);
                goalAngle -= 2;
            }
            LiftArm.liftArmPID();
            Lift.liftPID();
            telemetry.addData("angle", LiftArm.getRightMotor().getCurrentPosition() / LiftArm.RIGHT_MOTOR.getENCODER_TO_DEGREE());
            telemetry.update();
        }
    }
}