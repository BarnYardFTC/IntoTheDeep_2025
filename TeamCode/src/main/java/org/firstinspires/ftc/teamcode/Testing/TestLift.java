package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestLift", group = "test")
public class TestLift extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor right = hardwareMap.get(DcMotorEx.class, "rightLift");
        DcMotor left = hardwareMap.get(DcMotorEx.class, "leftLift");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            right.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
            left.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        }
    }
}