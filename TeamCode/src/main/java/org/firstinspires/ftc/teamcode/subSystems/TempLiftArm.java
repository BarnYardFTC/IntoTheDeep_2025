package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempLiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.

    public static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, 3); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, 3); // Left's motor props.

    private static final int VERTICAL = 90; // Angle for moving the lift arm to a vertical position.

    /**
     * Initializing all hardware.
     *
     * @param right - Hardware for right motor.
     * @param left  - Hardware for left motor.
     */
    public static void init(DcMotorEx right, DcMotorEx left) {
        // Assigning the given motors to the motors in the class.
        motors[RIGHT] = right;
        motors[LEFT] = left;

        motors[RIGHT].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    /**
     * Moved the arm 90 degrees so it becomes horizontal.
     */
    public static void makeHorizontal() {
        motors[RIGHT].setPower(1);
        motors[LEFT].setPower(1);

        motors[RIGHT].setTargetPosition(0);
        motors[LEFT].setTargetPosition(0);

        motors[RIGHT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motors[LEFT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Moved the arm 90 degrees so it becomes vertical.
     */
    public static void makeVertical() {
        motors[RIGHT].setPower(1);
        motors[LEFT].setPower(1);

        motors[RIGHT].setTargetPosition(RIGHT_MOTOR.getAngleToEncoder(VERTICAL));
        motors[LEFT].setTargetPosition(LEFT_MOTOR.getAngleToEncoder(VERTICAL));

        motors[RIGHT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motors[LEFT].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Informs if the arm is horizontal.
     *
     * @return - If the current arm's position is horizontal.
     */
    public static boolean isHorizontal() {
        return motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(0) + 5;
    }
}
