package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.modules.LiftProps;
import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempLift {
    private static final DcMotorEx[] motors = new DcMotorEx[2];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    private static final LiftProps RIGHT_MOTOR = new LiftProps(); // Right's motor props.
    private static final LiftProps LEFT_MOTOR = new LiftProps(); // Left's motor props.

    public static final double HIGH_CHAMBER = RIGHT_MOTOR.getEncodersToCm(66);
    public static final double HIGH_BASKET = RIGHT_MOTOR.getEncodersToCm(109.2);
    public static final double LOW_BASKET = RIGHT_MOTOR.getEncodersToCm(65.4);

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
