package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

// TeleOp
@TeleOp(name = "IntoTheDeep Temp TeleOp")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {

        waitForStart();

        while (opModeIsActive()) {

        }
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
    public void initLed() {
        RevBlinkinLedDriver LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
        TempLED.init(LED);
    }
}