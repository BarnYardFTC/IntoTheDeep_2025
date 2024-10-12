package org.firstinspires.ftc.teamcode.modules;

// Imports.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorProps {

    // An enum specifying all the different comparison options between two encoder positions
    private static enum compare_options{
        EQUALS,
        BIGGER,
        SMALLER
    }

    public static compare_options comparePosition(DcMotorEx motorEx, int position){
        /*
          Compare the encoder position of a given motor
          to another position

          param motorEx: A given motor.

          param position: A position being compared to

          param operator: An operator ('<'/'>'/'=') being executed on the values given by
                          motorEx.getCurrentPosition and position

          return the relationship between the current position and the given position.
                 3 options: SMALLER, BIGGER and EQUALS.
         */
        int cur_position = motorEx.getCurrentPosition();
        if (cur_position < position){
            return compare_options.SMALLER;
        }
        else if (cur_position > position){
            return compare_options.BIGGER;
        }
        else{
            return compare_options.EQUALS;
        }
    }
    public static boolean runToPosition(DcMotorEx motorEx, int position, double power) {
        /*
        Move motor to a specified position with a specified power,
        and when the motor arrives there, stop it's movement.
        Assumptions:
        1. Motor's zero-power-behaviour is brake
        2. Motor runs with encoder

        @param motorEx: A given motor
        @param position: Position the runs moves to
        @param power: The power in which the motor moves to the position

        @return: true when the motor arrived to the desired position. false otherwise
         */

        // Run the motor until it arrives the specified position
        if ( ! (comparePosition(motorEx, position) == compare_options.EQUALS) ) {
            motorEx.setTargetPosition(position);
            motorEx.setPower(power);
            motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            return false;
        }
        // When the motor arrived the position, stop running it
        else {
            motorEx.setPower(0);
            return true;
        }

    }
    public static boolean runToAndMaintainPosition(DcMotorEx motorEx, int position, double power){
        /*
        Run a motor to a specified position with a specified power
        and when the motor arrives there, maintain it.

        @param motorEx: A given motor
        @param position: Position the motor runs to and maintains
        @param power: The power in which to motor is running

        @return true if the motor is in the specified position and false otherwise
         */
        motorEx.setTargetPosition(position);
        motorEx.setPower(power);
        motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return comparePosition(motorEx, position) == compare_options.EQUALS;
    }
}
