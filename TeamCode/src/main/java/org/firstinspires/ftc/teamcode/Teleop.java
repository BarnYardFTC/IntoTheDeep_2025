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

// TeleOp.
@TeleOp(name = "IntoTheDeep Temp TeleOp")

public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initializing.

        waitForStart();

        // Maim loop.
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

    // Initializing functions.
    public void initEgnitionSystem() {
        DcMotorEx leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        DcMotorEx leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        DcMotorEx rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        IMU imu = hardwareMap.get(IMU.class, "IMU");

        EgnitionSystem.init(leftFront, rightFront, leftBack, rightBack, imu);
    }

    public void initTempDifferential() {
        Servo rightDifferential = hardwareMap.get(Servo.class, "rightDifferential");
        Servo leftDifferential = hardwareMap.get(Servo.class, "leftDifferential");
        AnalogInput analogSensor = hardwareMap.get(AnalogInput.class, "analogSensor");

        TempDifferential.init(rightDifferential, leftDifferential, analogSensor);
    }

    public void initTempDifferentialArm() {
        Servo rightDifferentialArm = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo leftDifferentialArm = hardwareMap.get(Servo.class, "rightDifferentialArm");

        TempDifferentialArm.init(rightDifferentialArm, leftDifferentialArm);
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

    // Main functions.
}