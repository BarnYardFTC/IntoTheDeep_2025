package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

// TeleOp name.
@TeleOp(name = "Lift_PID")

public class LiftPIDTuning extends LinearOpMode {
    private final FtcDashboard dash = FtcDashboard.getInstance();

    @Override
    public void runOpMode() {
        Lift.initialize(this);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            Lift.liftPID();

            telemetry.addData("pos", Lift.getTargetPos());
            telemetry.addData("current", Lift.getRightMotor().getCurrentPosition());
            telemetry.update();
        }
    }
}