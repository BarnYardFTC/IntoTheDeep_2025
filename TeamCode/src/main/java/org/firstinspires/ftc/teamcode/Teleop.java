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
            if (gamepad1.x) {
                TestDifferential.X = true;
            }
            else if (TestDifferential.X) {
//                TestDifferential.differentialArmR(45);
//                TestDifferential.differentialArmL(45);
                TestDifferential.differentialYawMovement(45);
                TestDifferential.differentialRollMovement(45);
                TestDifferential.X = false;
            }
        }
    }

    public void initTestDifferential() {
        Servo rightDifferential = hardwareMap.get(Servo.class, "rightDifferential");
        Servo leftDifferential = hardwareMap.get(Servo.class, "leftDifferential");
        Servo rightDifferentialArm = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo leftDifferentialArm = hardwareMap.get(Servo.class, "leftDifferentialArm");
        TestDifferential.init(rightDifferential, leftDifferential, rightDifferentialArm, leftDifferentialArm);
    }
}