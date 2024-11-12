package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempLiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.

    private static final MotorProps RIGHT_MOTOR = new MotorProps(); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(); // Right's motor props.

    // Angle for moving the lift arm.
    private static final int VERTICAL = 90;

    public static void init(DcMotorEx right, DcMotorEx left) {
        // Assigning the given motors to the motors in the class.
        motors[RIGHT] = right;
        motors[LEFT] = left;

        motors[LEFT].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public static void makeHorizontal() {
        motors[RIGHT].setPower(1);
        motors[LEFT].setPower(1);

        motors[RIGHT].setTargetPosition(0);
        motors[LEFT].setTargetPosition(0);

        motors[RIGHT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motors[LEFT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public static void makeVertical() {
        motors[RIGHT].setPower(1);
        motors[LEFT].setPower(1);

        motors[RIGHT].setTargetPosition(RIGHT_MOTOR.getAngleToEncoder(VERTICAL));
        motors[LEFT].setTargetPosition(LEFT_MOTOR.getAngleToEncoder(VERTICAL));

        motors[RIGHT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motors[LEFT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }
}
