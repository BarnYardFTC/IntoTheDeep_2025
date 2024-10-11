package org.firstinspires.ftc.teamcode.modules;

// Imports.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorProps {


    public static boolean compareEncoderPosition(DcMotorEx motorEx, int position, String operator) throws Exception {

        /*
          Compare the encoder position of a given motor
          to another position

          param motorEx: A given motor.

          param position: A position being compared to

          param operator: An operator ('<'/'>'/'=') being executed on the values given by
                          motorEx.getCurrentPosition and position

          return true if the operator is representing the relationship between the values
                  of the encoder position of motorEx and the param position. false otherwise
         */


        switch (operator) {
            case "<":
                return motorEx.getCurrentPosition() < position;
            case ">":
                return motorEx.getCurrentPosition() > position;
            case "=":
                return motorEx.getCurrentPosition() == position;
        }

        throw new Exception("No such operation");
    }

    public static boolean setPosition(DcMotorEx motorEx, int position, double power) {
        /*
        Move motor to a certain position with a certain power
        Assumptions:
        1. Motor's zero-power-behaviour is brake
        2. Motor runs with encoder

        @param motorEx: A given motor
        @param position: Position the motor moves to
        @param power: The power in which the motor moves to the position

        @return: true if the motor arrived to the desired position. false otherwise
         */

        motorEx.setTargetPosition(position);
        motorEx.setPower(power);
        motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return motorEx.getCurrentPosition() == position;
    }
}
