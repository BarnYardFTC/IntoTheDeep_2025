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
            if (TempGeneralFunctions.isPressed(gamepad1.x)) {
                TempDifferential.differentialServos[TempDifferential.rightDifferential].setPosition(TempDifferential.differentialServos[TempDifferential.rightDifferential].getPosition() + 0.01);
            }
            if (TempGeneralFunctions.isPressed(gamepad1.a)) {
                TempDifferential.differentialServos[TempDifferential.leftDifferential].setPosition(TempDifferential.differentialServos[TempDifferential.leftDifferential].getPosition() + 0.01);
            }
            if (TempGeneralFunctions.isPressed(gamepad1.y)) {
                TempDifferential.differentialServos[TempDifferential.rightDifferential].setPosition(TempDifferential.differentialServos[TempDifferential.rightDifferential].getPosition() - 0.01);
            }
            if (TempGeneralFunctions.isPressed(gamepad1.b)) {
                TempDifferential.differentialServos[TempDifferential.leftDifferential].setPosition(TempDifferential.differentialServos[TempDifferential.leftDifferential].getPosition() - 0.01);
            }
            telemetry.addData("R", TempDifferential.differentialServos[TempDifferential.rightDifferential].getPosition());
            telemetry.addData("L", TempDifferential.differentialServos[TempDifferential.leftDifferential].getPosition());
            telemetry.addData("Servo name 0", TempDifferential.differentialServos[TempDifferential.rightDifferential]);
            telemetry.addData("Servo name 1", TempDifferential.differentialServos[TempDifferential.leftDifferential]);
            telemetry.addData("Check function", TempGeneralFunctions.isPressed(gamepad1.right_bumper));
            telemetry.update();
        }
    }

    public void initTestDifferential() {
        Servo rightDifferential = hardwareMap.get(Servo.class, "rightDifferential");
        Servo leftDifferential = hardwareMap.get(Servo.class, "leftDifferential");
//        Servo rightDifferentialArm = hardwareMap.get(Servo.class, "rightDifferentialArm");
//        Servo leftDifferentialArm = hardwareMap.get(Servo.class, "leftDifferentialArm");
        TempDifferential.init(rightDifferential, leftDifferential);
    }
}