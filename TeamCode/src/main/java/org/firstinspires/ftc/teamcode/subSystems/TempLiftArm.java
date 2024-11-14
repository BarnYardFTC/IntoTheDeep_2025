package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempLiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.

    private static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, (double) 1/3); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, (double) 1/3); // Left's motor props.

    private static boolean rests;

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

        rests = true;
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

    public static void rest() {
        if (motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(VERTICAL) - 100 && motors[RIGHT].getCurrentPosition() > RIGHT_MOTOR.getAngleToEncoder(0) + 100) {
            rests = false;
        }

        if ((motors[RIGHT].getCurrentPosition() > RIGHT_MOTOR.getAngleToEncoder(VERTICAL) - 5 || motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(0) + 5) && !rests) {
            motors[RIGHT].setPower(0);
            motors[LEFT].setPower(0);
            rests = true;
        }
    }

    public static boolean isHorizontal() {
        return motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(0) + 5;
    }
}
