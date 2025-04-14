package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="motor test", group="test")
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addLine("Click Start");
        waitForStart();
        while (opModeIsActive()){
            motor.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            telemetry.addData("Right Trigger: ", "Positive Rotation");
            telemetry.addData("Left Trigger: ", "Negative Rotation");
            telemetry.update();
        }
    }
}
