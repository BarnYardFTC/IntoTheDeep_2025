package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TempLift {
    private static final DcMotorEx[] motors = new DcMotorEx[2];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    public static void init(DcMotorEx right, DcMotorEx left) {
        motors[LEFT] = left;
        motors[RIGHT] = right;

        motors[LEFT].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor: motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}
