package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TempVerticalLift {
    // Motors Initialization
    private static final int MOTORS_AMOUNT = 2;
    private static final DcMotorEx[] motors = new DcMotorEx[MOTORS_AMOUNT];
    private static final int RIGHT_INDEX = 0;
    private static final int LEFT_INDEX = 1;

    private static final double ENCODER_LIFT_CM_RATIO = 1; // ToDo: Find real value

    public static void init(DcMotorEx left, DcMotorEx right) {

        /*
        Initialize the two motors that the system has so that the lift could be used.
        Left motor is reversed for a better organization of the code.

        @param left: left motor.
        @param right: right motor
         */
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        motors[RIGHT_INDEX] = right;
        motors[LEFT_INDEX] = left;
        for (DcMotorEx motor: motors){
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    private static int cmToEncoder(int cm) {

        /*
          Find out by how much the encoder position of a motor needs to change
          in order for the lift to move X cm.

          param cm: By how many cm the lift needs to move
                     A positive cm value = movement up.
                     A negative value = movement down.

          return by how much the encoder position needs to change in order for the lift to move X cm.
         */
        return (int) (cm * ENCODER_LIFT_CM_RATIO);
    }
}
