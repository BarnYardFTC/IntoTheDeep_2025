package org.firstinspires.ftc.teamcode;

//Imports.

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IgnitionSystem {
    private static final int MOTORS_AMOUNT = 4; // Amount of motors used.
    private static final DcMotorEx[] motors = new DcMotorEx[MOTORS_AMOUNT]; // Motors array.
    private static final int FL = 0; // Front left's motor index.
    private static final int FR = 1; // Front right's motor index.
    private static final int BL = 2; // Back left's motor index.
    private static final int BR = 3; // Back right's motor index.

    private static IMU imu; // IMU.

    // Variables needed to calculate the power that each motor is given
    private static double maxPower; // Max power that a motor will get.
    private static double lx; // Rc left x-axis.
    private static double ly; // Rc left y-axis.
    private static double rx; // Rc right x-axis.
    private static double robotHeading; // Measurement unit: Radians.
    private static double adjustedLx; // Adjusted x axis vector of movement.
    private static double adjustedLy; // Adjusted y axis vector of movement.

    /**
     * Initializing all hardware.
     *
     * @param frontLeft  - Hardware for front left motor.
     * @param frontRight - Hardware for front right motor.
     * @param backLeft   - Hardware for back left motor.
     * @param backRight  - Hardware for back right motor.
     * @param imuConfig  - Hardware for imu.
     */
    public static void init(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, IMU imuConfig) {
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

        imu.resetYaw(); // Reset imu heading.

        resetVariables(); // Setting initial values to the variables.
    }

    /**
     * This function is built to reset all the inconstant variables of the class.
     * This function is essential to enable reuse of the class without restarting the robot.
     */
    public static void resetVariables() {
        maxPower = 1;
        lx = 0;
        ly = 0;
        rx = 0;
        robotHeading = 0;
        adjustedLx = 0;
        adjustedLy = 0;
    }

    /**
     * Moves the drive train based on manual inputs from the rc and data from the IMU.
     *
     * @param gamepad1 - Rc inputs for all needed joysticks and axis of said joysticks.
     */
    public static void move(Gamepad gamepad1) {
        // Setting variables
        lx = gamepad1.left_stick_x;
        ly = -gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        robotHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Calculating maxPower for saving the ratio of motor powers.
        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        // Calculating adjusted vectors for field centric drive.
        adjustedLx = -ly * Math.sin(robotHeading) + lx * Math.cos(robotHeading);
        adjustedLy = ly * Math.cos(robotHeading) + lx * Math.sin(robotHeading);

        // Giving power to motors.
        motors[FL].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        motors[FR].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        motors[BL].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        motors[BR].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}