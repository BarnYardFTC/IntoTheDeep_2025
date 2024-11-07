package org.firstinspires.ftc.teamcode;

/*
    =================
     IMPORTANT NOTES
    =================

    - The methods from this class were not tested on the robot yet.

 */

// Imports.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Algorithm;
import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempVerticalLift {
    // Motors Initialization constant values
    private static final int MOTORS_AMOUNT = 2;
    private static final DcMotorEx[] motors = new DcMotorEx[MOTORS_AMOUNT];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    // Lift Positions constant values in centimeters
    private static final double BOTTOM_POSITION_CM = 0; // TODO: Find real value
    private static final double LOW_POSITION_CM = 20; // TODO: Find real value
    private static final double HIGH_POSITION_CM = 40; // TODO: Find real value
    private static final double[] POSITIONS_ARR = {BOTTOM_POSITION_CM, LOW_POSITION_CM, HIGH_POSITION_CM};
    // Motors Powers constant
    private static final double RUNNING_POWER = 0.1; // TODO: Find real value
    private static final double BRAKING_POWER = 0.1; // TODO: Find real value
    // By how much you need to multiply a given centimeters value to get a corresponding encoder value
    private static final double CM_TO_ENCODER_RATIO = 25; // TODO: Find real value

    /*
    ================ MAIN METHODS ====================

    The following methods are the methods that will be
    used in the Teleop class to operate the vertical
    lift.

    ==================================================
     */

    /**
        Initialize the two motors that the system has so that the lift could be used.
        Left motor is reversed so that: positive encoder=movement up for both motors.

        @param left left motor.
        @param right right motor
    */
    public static void init(DcMotorEx left, DcMotorEx right) {
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        motors[RIGHT] = right;
        motors[LEFT] = left;
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    /**
     Set the position of the lift in cm
     @param position Desired position in cm
     @return true if the lift arrived the specified position, false otherwise.
     */
    public static boolean setPosition(double position) {
        boolean arrived_position = false;
        for (DcMotorEx motor : motors) {
            arrived_position = MotorProps.runToPosition(motor, cmToEncoder(position), RUNNING_POWER);
        }
        return arrived_position;
    }

    /**
     * Brake the lift
     * @return True if the lift has arrived the braking position
     */
    public static boolean brake() {
        int position = cmToEncoder(getCurrentPosition());
        boolean maintaining_position = false;
        for (DcMotorEx motor : motors) {
            maintaining_position = MotorProps.runToAndMaintainPosition(motor, position, BRAKING_POWER);
        }
        return maintaining_position;
    }
    /**
     *  @return The position of the lift currently */
    public static String getCurrentPositionCm() {

        double position = Algorithm.closestTo(POSITIONS_ARR, motors[0].getCurrentPosition());
        if (position == HIGH_POSITION_CM) {
            return "HIGH";
        } else if (position == LOW_POSITION_CM) {
            return "LOW";
        } else {
            return "BOTTOM";
        }
    }

    /** Get the cm value of each position (HIGH/LOW/BOTTOM).
        @param position: The specified position (HIGH/LOW/BOTTOM).
        @return the cm value of the specified position
    */
    public static double getPositionCm(String position) {
        switch (position) {
            case "HIGH":
                return HIGH_POSITION_CM;
            case "LOW":
                return LOW_POSITION_CM;
            default:
                return BOTTOM_POSITION_CM;
        }
    }


    /*
    ================ HELPER METHODS ====================

    The following methods are private methods which were
    programmed to increase the functionality of the
    main methods.

    ====================================================
     */

    /**
     Convert cm the encoder
     @param cm Given cm value
     @return encoder position
     */
    private static int cmToEncoder(double cm) {
        return (int) (cm * CM_TO_ENCODER_RATIO);
    }

    /**
     * Convert encoder to cm
     * @param encoder A given encoder
     * @return a cm value corresponding to encoder
     */
    private static double encoderToCm(int encoder) {
        return (double) encoder / CM_TO_ENCODER_RATIO;
    }
    /**
     @return the cm value which is the closest to the lift position (bottom cm/low cm/high cm).
     */
    private static double getCurrentPosition() {
        return motors[LEFT].getCurrentPosition();
    }

    /*
    ================ TEST METHODS ====================

    The following methods  are temporary methods
    programed to help testing things on the robot.
    These methods will not serve us in the long run.

    ==================================================
     */
    public static int getEncoderPosition() {
        return motors[0].getCurrentPosition();
    }

    public static double getEncoderToCm() {
        return encoderToCm(motors[0].getCurrentPosition());
    }

    public static int getCmToEncoder() {
        return cmToEncoder(getEncoderToCm());
    }

    public static void measureEncoderCmRatio(Gamepad gamepad, Telemetry telemetry) {
        // TODO: When the lift is built, program this method to help measure the encoder-cm ratio
    }
}