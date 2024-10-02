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
            if (TestDifferential.isPressed(gamepad1.x)) {
                TestDifferential.differentialServos[TestDifferential.rightDifferential].setPosition(TestDifferential.differentialServos[TestDifferential.rightDifferential].getPosition() + 0.01);
            }
            if (TestDifferential.isPressed(gamepad1.a)) {
                TestDifferential.differentialServos[TestDifferential.leftDifferential].setPosition(TestDifferential.differentialServos[TestDifferential.leftDifferential].getPosition() + 0.01);
            }
            if (gamepad1.y) {
                TestDifferential.differentialServos[TestDifferential.rightDifferential].setPosition(0);
            }
            if (gamepad1.b) {
                TestDifferential.differentialServos[TestDifferential.leftDifferential].setPosition(0);
            }
            telemetry.addData("R", TestDifferential.differentialServos[TestDifferential.rightDifferential].getPosition());
            telemetry.addData("L", TestDifferential.differentialServos[TestDifferential.leftDifferential].getPosition());
            telemetry.update();
        }
    }

    public void initTestDifferential() {
        Servo rightDifferential = hardwareMap.get(Servo.class, "rightDifferential");
        Servo leftDifferential = hardwareMap.get(Servo.class, "leftDifferential");
//        Servo rightDifferentialArm = hardwareMap.get(Servo.class, "rightDifferentialArm");
//        Servo leftDifferentialArm = hardwareMap.get(Servo.class, "leftDifferentialArm");
        TestDifferential.init(rightDifferential, leftDifferential);
    }
}