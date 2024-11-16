package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class PIDTest extends OpMode {
    private PIDController controller;

    public static double p = 0;
    public static double i = 0;
    public static double d = 0;

    public static int targetPos;
    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "right");
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "left");

        TempLiftArm.init(right, left);
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int currentPos = TempLiftArm.motors[0].getCurrentPosition();
        double pid = controller.calculate(currentPos, targetPos);

        TempLiftArm.motors[0].setPower(pid);
        TempLiftArm.motors[1].setPower(pid);

        telemetry.addData("pos ", currentPos);
        telemetry.addData("target ", targetPos);
        telemetry.update();
    }
}
