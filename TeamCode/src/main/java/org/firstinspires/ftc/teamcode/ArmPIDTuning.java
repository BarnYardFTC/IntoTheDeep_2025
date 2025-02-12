package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

// TeleOp name.
@TeleOp(name = "Arm_PID")

public class ArmPIDTuning extends LinearOpMode {
    private final FtcDashboard dash = FtcDashboard.getInstance();

    @Override
    public void runOpMode() {
        LiftArm.initialize(this);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
//            test(gamepad1);

            LiftArm.PID();

            telemetry.addData("target pos", LiftArm.getTargetPos());
            telemetry.addData("current pos", LiftArm.getRightMotor().getCurrentPosition());
            telemetry.addData("powerR", LiftArm.getRightMotor().getPower());
            telemetry.addData("powerL", LiftArm.getLeftMotor().getPower());
            telemetry.update();
        }
    }

    public void test(Gamepad gamepad){
        double positive_power = gamepad.right_trigger;
        double negative_power = gamepad.left_trigger;
        double power = positive_power + negative_power;
        LiftArm.getRightMotor().setPower(power);
    }
}