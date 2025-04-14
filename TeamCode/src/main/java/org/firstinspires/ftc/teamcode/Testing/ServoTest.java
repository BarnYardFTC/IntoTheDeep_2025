package org.firstinspires.ftc.teamcode.Testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Test", group = "test")
@Config
public class ServoTest extends LinearOpMode {

    public static double servoPosition = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                servoPosition = 1;
            }
            else if (gamepad1.dpad_down){
                servoPosition = 0;

            }

            servo.setPosition(servoPosition);

            telemetry.addData("dpad up: ", "1");
            telemetry.addData("dpad down", "0");
            telemetry.addLine("Custom Position on dashboard");
            telemetry.addData("currentPos", servoPosition);
            telemetry.update();
        }
    }
}
