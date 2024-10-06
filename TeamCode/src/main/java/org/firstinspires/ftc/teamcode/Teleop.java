package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

// TeleOp
@TeleOp(name = "IntoTheDeep Temp TeleOp")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {

        initTestDifferential();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x && !TempGeneralFunctions.xWasPressed) {
                TempGeneralFunctions.xWasPressed = true;
                TempDifferential.differentialRollMovement(45);
            }
            if (!gamepad1.x) {
                TempGeneralFunctions.xWasPressed = false;
            }
            if (gamepad1.a && !TempGeneralFunctions.aWasPressed) {
                TempGeneralFunctions.aWasPressed = true;
                TempDifferential.differentialYawMovement(45);            }
            if (!gamepad1.a) {
                TempGeneralFunctions.aWasPressed = false;
            }
            telemetry.addData("r", TempDifferential.differentialServos[TempDifferential.rightDifferential].getPosition());
            telemetry.addData("l", TempDifferential.differentialServos[TempDifferential.leftDifferential].getPosition());
            telemetry.update();
        }
    }

    public void initTestDifferential() {
        Servo rightDifferential = hardwareMap.get(Servo.class, "rightDifferential");
        Servo leftDifferential = hardwareMap.get(Servo.class, "leftDifferential");
        TempDifferential.init(rightDifferential, leftDifferential);
    }
}