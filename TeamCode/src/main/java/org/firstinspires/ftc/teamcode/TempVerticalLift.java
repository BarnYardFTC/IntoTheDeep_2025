package org.firstinspires.ftc.teamcode;

/*
    =================
     IMPORTANT NOTES
    =================

    - The logic in the class is built based on the assumption that the motor can hold the lift
      just using the 'setZeroPowerBehaviour(BRAKE)' method.
      Yet, there is a a brake option if needed

    - The methods from this class were not tested on the robot yet.

 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.Algorithm;
import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class TempVerticalLift {
    // Motors Initialization constant values
    private static final int MOTORS_AMOUNT = 2;
    private static final DcMotorEx[] motors = new DcMotorEx[MOTORS_AMOUNT];
    private static final int RIGHT_INDEX = 0;
    private static final int LEFT_INDEX = 1;

    // Lift Positions constant values in centimeters
    private static final double BOTTOM_POSITION_CM = 0;
    private static final double LOW_POSITION_CM = 20;
    private static final double HIGH_POSITION_CM = 400;
    private static final double[] POSITIONS_ARR = {BOTTOM_POSITION_CM,
            LOW_POSITION_CM,
            HIGH_POSITION_CM};

    // Lift positions
    public static enum Position {
        BOTTOM,
        LOW,
        HIGH
    }

    // Motors Powers constant
    private static final double RUNNING_POWER = 0.1; // ToDo: Find real value
    private static final double BRAKING_POWER = 0.1; // ToDo: Find real value

    // By how much you need to multiply a given centimeters value to get a corresponding encoder value
    private static final double CM_TO_ENCODER_RATIO = 25; // ToDo: Find real value

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
        ToDo: Find out which motor needs to be reversed

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
    public static boolean setPosition(Position position) {

        /*
        Set the position of the lift (3 options)
        @param position: Desired position (DEFAULT/LOW_UNLOADING/HIGH_UNLOADING)
        @return true if the lift arrived the specified position, false otherwise.
         */
        boolean arrived_position = false;
        switch (position) {
            case BOTTOM:
                for (DcMotorEx motor : motors) {
                    arrived_position = MotorProps.runToPosition(motor, cmToEncoder(BOTTOM_POSITION_CM), RUNNING_POWER);
                }
                return arrived_position;
            case LOW:
                for (DcMotorEx motor : motors) {
                    arrived_position = MotorProps.runToPosition(motor, cmToEncoder(LOW_POSITION_CM), RUNNING_POWER);
                }
                return arrived_position;
            case HIGH:
                for (DcMotorEx motor : motors) {
                    arrived_position = MotorProps.runToPosition(motor, cmToEncoder(HIGH_POSITION_CM), RUNNING_POWER);
                }
                return arrived_position;
            default:
                return false;
        }
    }
    public static boolean brake() {
        int position = cmToEncoder(getCurrentPositionValue());
        boolean maintaining_position = false;
        for (DcMotorEx motor : motors) {
            maintaining_position = MotorProps.runToAndMaintainPosition(motor, position, BRAKING_POWER);
        }
        return maintaining_position;
    }
    public static Position getCurrentPosition() {

        // @return The position of the lift currently

        return Position.values()[Algorithm.closestTo(
                POSITIONS_ARR,
                motors[0].getCurrentPosition()
        )];
    }

    /*
    ================ HELPER METHODS ====================

    The following methods are private methods which were
    programmed to increase the functionality of the
    main methods.

    ====================================================
     */
    private static int cmToEncoder(double cm) {

        /*
          Find out by how much the encoder position of a motor needs to change
          in order for the lift to move X cm.

          param cm: By how many cm the lift needs to move
                     A positive cm value = movement up.
                     A negative value = movement down.

          return by how much the encoder position needs to change in order for the lift to move X cm.
         */
        return (int) (cm * CM_TO_ENCODER_RATIO);
    }
    private static double encoderToCm(int encoder) {
        return (double) encoder / CM_TO_ENCODER_RATIO;
    }
    private static double getCurrentPositionValue() {
        /*
        @return the cm value which is the closest to the lift position (bottom cm/low cm/high cm).
         */
        return POSITIONS_ARR[Algorithm.closestTo(
                POSITIONS_ARR,
                encoderToCm(motors[0].getCurrentPosition())
        )];
    }

    /*
    ================ TEST METHODS ====================

    The following methods  are temporary methods
    programed to help testing things on the robot.
    These methods will not serve us in the long run.

    ==================================================
     */
   public static int getEncoderPosition(){
       return motors[0].getCurrentPosition();
   }
   public static double getEncoderToCm(){
       return encoderToCm(motors[0].getCurrentPosition());
   }
   public static int getCmToEncoder(){
       return cmToEncoder(getEncoderToCm());
   }
   public static void measureEncoderCmRatio(Gamepad gamepad){

        // ToDo: When the lift is complete, program this method to help measure the encoder-cm ratio
    }
}