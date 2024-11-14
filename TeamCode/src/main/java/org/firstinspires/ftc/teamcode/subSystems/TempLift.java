package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempLift {
    private static final DcMotorEx[] motors = new DcMotorEx[2];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    private static final MotorProps RIGHT_MOTOR = new MotorProps(); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(); // Left's motor props.

    private static final double rotationPerInch = 0; // Amount of rotations of the last gear needed to move one inch of the lift.

    /**
     * Initializing all hardware.
     *
     * @param right - Hardware for right motor.
     * @param left  - Hardware for left motor.
     */
    public static void init(DcMotorEx right, DcMotorEx left) {
        motors[LEFT] = left;
        motors[RIGHT] = right;

        motors[LEFT].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}
