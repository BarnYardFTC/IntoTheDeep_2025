package org.firstinspires.ftc.teamcode;

//Imports.

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IgnitionSystem {
    // Motors initialization constants
    private static final int MOTORS_AMOUNT = 4;
    private static final DcMotorEx[] motors = new DcMotorEx[MOTORS_AMOUNT];
    private static final int FL = 0;
    private static final int FR = 1;
    private static final int BL = 2;
    private static final int BR = 3;

    // IMU.
    private static IMU imu;

    // Variables needed to calculate the power that each motor is given
    private static double maxPower;
    private static double lx; // rc left x-axis
    private static double ly; // rc left y-axis
    private static double rx; // rc right x-axis
    private static double robotHeading; // Measurement unit: Radians
    private static double adjustedLx;
    private static double adjustedLy;

    public static void init(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, IMU imuConfig) {
        /*
        Initialize the motors and imu.
        @param frontLeft: motor connected to the front-left wheel.
        @param frontRight: motor connected to the front-right wheel.
        @param backLeft: motor connected to the back-left wheel.
        @param backRight: motor connected to the back-right wheel.
         */

        // Assigning the given motors to the motors in the class.
        motors[FL] = frontLeft;
        motors[FR] = frontRight;
        motors[BL] = backLeft;
        motors[BR] = backRight;
        imu = imuConfig;

        // Reversing some motors so that positive power = positive-direction wheel spin.
        motors[FL].setDirection(DcMotorEx.Direction.REVERSE);
        motors[BL].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Setting imu attributes.
        // TODO: Make sure to update the orientation attributes
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));

        imu.resetYaw();

        // Setting initial values to the variables.
        resetVariables();
    }

    public static void resetVariables() {
        /*
        This function is built to reset all the inconstant variables of the class.
        This function is essential to enable reuse of the class without restarting the robot.
         */
        maxPower = 1;
        lx = 0;
        ly = 0;
        rx = 0;
        robotHeading = 0;
        adjustedLx = 0;
        adjustedLy = 0;
    }

    public static void moveDriveTrain(Gamepad gamepad1) {
        /*
        The giving of power to the different motors that move the wheels of the robot.
        @param gamepad1: the gamepad that provides manual inputs.
         */
        // Setting variables
        lx = gamepad1.left_stick_x;
        ly = -gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        robotHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Calculating maxPower for saving the ratio of motor powers.
        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        // Calculating adjusted powers for field centric drive.
        adjustedLx = -ly * Math.sin(robotHeading) + lx * Math.cos(robotHeading);
        adjustedLy = ly * Math.cos(robotHeading) + lx * Math.sin(robotHeading);

        // Giving power to motors.
        motors[FL].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        motors[FR].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        motors[BL].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        motors[BR].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}