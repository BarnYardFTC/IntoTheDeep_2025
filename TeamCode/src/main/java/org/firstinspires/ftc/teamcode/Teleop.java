package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.PrintWriter;
import java.io.StringWriter;

@TeleOp(name = "IntoTheDeep Temp TeleOp")

public class Teleop extends LinearOpMode {
    // Initializing Functions
    public void initEgnitionSystem() {
        // TODO: Change names of motor in configuration
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        IMU imu = hardwareMap.get(IMU.class, "IMU");

        IgnitionSystem.init(frontLeft, frontRight, backLeft, backRight, imu);
    }

    public void initDifferential() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferential");
        Servo left = hardwareMap.get(Servo.class, "leftDifferential");
        AnalogInput analogSensor = hardwareMap.get(AnalogInput.class, "analogSensor");

        Differential.init(right, left, analogSensor);
    }

    public void initDifferentialArm() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo left = hardwareMap.get(Servo.class, "leftDifferentialArm");

        DifferentialArm.init(right, left);
    }

    public void initClaw() {
        Servo claw = hardwareMap.get(Servo.class, "claw");
        ColorRangeSensor distanceSensor = hardwareMap.get(ColorRangeSensor.class, "distanceSensor");

        Claw.init(claw, distanceSensor);
    }

    public void initIntakeArm() {
        Servo right = hardwareMap.get(Servo.class, "rightIntakeArm");
        Servo left = hardwareMap.get(Servo.class, "leftIntakeArm");

        IntakeArm.init(right, left);
    }

    public void initLED() {
        RevBlinkinLedDriver LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        org.firstinspires.ftc.teamcode.LED.init(LED);
    }

    public void initVerticalLift() {
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");

        TempVerticalLift.init(left, right);
    }

    // Main Functions

    @Override
    public void runOpMode() {
        // Initializing

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {

            try {

                telemetry.update();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                telemetry.addData("stackTrace", stackTrace);
                telemetry.update();
                throw e;
            }
        }
    }
}