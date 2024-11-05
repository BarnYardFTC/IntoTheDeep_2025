package org.firstinspires.ftc.teamcode.subSystems;

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
    private static final int RIGHT_INDEX = 0;
    private static final int LEFT_INDEX = 1;

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
    public static void init(DcMotorEx left, DcMotorEx right) {

        /*
        Initialize the two motors that the system has so that the lift could be used.
        Left motor is reversed so that: positive encoder=movement up for both motors.
        TODO: Find out which motor needs to be reversed

        @param left: left motor.
        @param right: right motor
         */
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        motors[RIGHT_INDEX] = right;
        motors[LEFT_INDEX] = left;
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public static boolean setPosition(double position) {

        /*
        Set the position of the lift in cm
        @param position: Desired position (DEFAULT/LOW_UNLOADING/HIGH_UNLOADING)
        @return true if the lift arrived the specified position, false otherwise.
         */
        boolean arrived_position = false;
        for (DcMotorEx motor : motors) {
            arrived_position = MotorProps.runToPosition(motor, cmToEncoder(position), RUNNING_POWER);
        }
        return arrived_position;
    }

    public static boolean brake() {
        int position = cmToEncoder(getCurrentPositionValue());
        boolean maintaining_position = false;
        for (DcMotorEx motor : motors) {
            maintaining_position = MotorProps.runToAndMaintainPosition(motor, position, BRAKING_POWER);
        }
        return maintaining_position;
    }

    public static String getCurrentPositionCm() {
        /* @return The position of the lift currently */
        double position = Algorithm.closestTo(POSITIONS_ARR, motors[0].getCurrentPosition());
        if (position == HIGH_POSITION_CM) {
            return "HIGH";
        } else if (position == LOW_POSITION_CM) {
            return "LOW";
        } else {
            return "BOTTOM";
        }
    }

    public static double getPositionCm(String position) {
        /* Get the cm value of each position (HIGH/LOW/BOTTOM).
        @param position: The specified position (HIGH/LOW/BOTTOM).
        @return the cm value of the specified position
        */
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

    private static final double GEAR_RATIO = 19.6;
    private static final double RADIUS = 0.5;
    private static final double CM_TO_ENCODER_CONVERSION_CONSTANT = GEAR_RATIO/(2*Math.PI*RADIUS);
    private static int cmToEncoder(double cm) {

        return (int) (cm*CM_TO_ENCODER_CONVERSION_CONSTANT);
    }

    private static double encoderToCm(int encoder) {
        return 1/(encoder*CM_TO_ENCODER_CONVERSION_CONSTANT);
    }

    private static double getCurrentPositionValue() {
        /*
        @return the cm value which is the closest to the lift position (bottom cm/low cm/high cm).
         */
        return POSITIONS_ARR[Algorithm.closestTo(POSITIONS_ARR, encoderToCm(motors[0].getCurrentPosition()))];
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


}