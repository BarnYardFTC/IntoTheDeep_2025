package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.PrintWriter;
import java.io.StringWriter;


@TeleOp(name = "IntoTheDeep Temp TeleOp")

public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initializing

        initVerticalLift();
        waitForStart();

        boolean A_held = false;
        // Main Loop
        while (opModeIsActive()) {

            if (gamepad1.b){
                TempVerticalLift.brake();
            }
            else if (!A_held && gamepad1.a && gamepad1.dpad_down){
                TempVerticalLift.setPosition(TempVerticalLift.Position.BOTTOM);
                A_held = true;
            }
            else if (!A_held && gamepad1.a && gamepad1.dpad_up){
                TempVerticalLift.setPosition(TempVerticalLift.Position.HIGH);
                A_held = true;
            }
            else if (!A_held && gamepad1.a && (gamepad1.dpad_left||gamepad1.dpad_right)){
                TempVerticalLift.setPosition(TempVerticalLift.Position.LOW);
                A_held = true;
            }
            else if (!gamepad1.a){
                A_held = false;
            }


            printLiftData(telemetry);
            printLiftCommends(telemetry);
            telemetry.update();
//            try {
//                telemetry.update();
//            } catch (Exception e) {
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                e.printStackTrace(pw);
//                String stackTrace = sw.toString();
//                telemetry.addData("stackTrace", stackTrace);
//                telemetry.update();
//                throw e;
//            }
        }
    }

    // Initializing Functions

    public void initEgnitionSystem() {
        // ToDo: Change names of motor in configuration
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        IMU imu = hardwareMap.get(IMU.class, "IMU");

        EgnitionSystem.init(frontLeft, frontRight, backLeft, backRight, imu);
    }

    public void initTempDifferential() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferential");
        Servo left = hardwareMap.get(Servo.class, "leftDifferential");
        AnalogInput analogSensor = hardwareMap.get(AnalogInput.class, "analogSensor");

        TempDifferential.init(right, left, analogSensor);
    }

    public void initTempDifferentialArm() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo left = hardwareMap.get(Servo.class, "rightDifferentialArm");

        TempDifferentialArm.init(right, left);
    }

    public void initTempClaw() {
        Servo claw = hardwareMap.get(Servo.class, "claw");
        ColorRangeSensor distanceSensor = hardwareMap.get(ColorRangeSensor.class, "distanceSensor");

        TempClaw.init(claw, distanceSensor);
    }

    public void initTempIntakeArm() {
        Servo right = hardwareMap.get(Servo.class, "rightIntakeArm");
        Servo left = hardwareMap.get(Servo.class, "leftIntakeArm");

        TempIntakeArm.init(right, left);
    }

    public void initLed() {
        RevBlinkinLedDriver LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        TempLED.init(LED);
    }
    public void initVerticalLift(){
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "leftLift");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "rightLift");
        TempVerticalLift.init(left, right);
    }
    // Main Functions


    // Test Functions

    public void printLiftData(Telemetry telemetry){
        telemetry.addLine("Lift Data:");
        telemetry.addData("Motors' Encoders Position: ", TempVerticalLift.getEncoderPosition());
        telemetry.addData("Position In Cm: ", TempVerticalLift.getEncoderToCm());
        telemetry.addData("Encoder Position (based on cm): ", TempVerticalLift.getCmToEncoder());
        telemetry.addData("Position: ", TempVerticalLift.getCurrentPosition());
    }
    public void printLiftCommends(Telemetry telemetry) {
        telemetry.addLine("Lift Commands:");
        telemetry.addData("Set Position: ", "Press A+Position");
        telemetry.addLine("*Position values:");
        telemetry.addData("dpad up: ", "HIGH POSITION");
        telemetry.addData("dpad left/right: ", "LOW POSITION");
        telemetry.addData("dpad down: ", "BOTTOM");
        telemetry.addData("Brake", "Hold B");
    }
}