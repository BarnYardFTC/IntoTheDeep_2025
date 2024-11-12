package org.firstinspires.ftc.teamcode.subSystems;

//Imports.

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain {
    public static final DcMotorEx[] motors = new DcMotorEx[4]; // Motors array.
    public static final int LF = 0; // Front left's motor index.
    private static final int RF = 1; // Front right's motor index.
    public static final int LB = 2; // Back left's motor index.
    private static final int RB = 3; // Back right's motor index.

    private static IMU imu; // IMU.

    // MaxPower is the largest motor power (absolute value) or 1
    // This ensures all the powers maintain the same ratio,
    // But only if at least one is out of the range [-1, 1]
    private static double maxPower;
    private static double lx; // Rc left x-axis.
    private static double ly; // Rc left y-axis.
    private static double rx; // Rc right x-axis.
    private static double robotHeading; // Measurement unit: Radians.
    private static double adjustedLx; // Adjusted x axis vector of movement.
    private static double adjustedLy; // Adjusted y axis vector of movement.

    /**
     * Initializing all hardware.
     *
     * @param leftFront  - Hardware for front left motor.
     * @param rightFront - Hardware for front right motor.
     * @param leftBack   - Hardware for back left motor.
     * @param rightBack  - Hardware for back right motor.
     * @param imuConfig  - Hardware for imu.
     */
    public static void init(DcMotorEx leftFront, DcMotorEx rightFront, DcMotorEx leftBack, DcMotorEx rightBack, IMU imuConfig) {
        // Assigning the given motors to the motors in the class.
        motors[LF] = leftFront;
        motors[RF] = rightFront;
        motors[LB] = leftBack;
        motors[RB] = rightBack;
        imu = imuConfig;

        // Reversing some motors so that positive power = positive-direction wheel spin.
        motors[LF].setDirection(DcMotorEx.Direction.REVERSE);
        motors[LB].setDirection(DcMotorEx.Direction.REVERSE);

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
     * Get the robot heading given from the imu.
     *
     * @return - Current robot heading.
     */
    public static double getRobotHeading() {
        return robotHeading;
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
     * @param gamepad - Rc inputs for all needed joysticks and axis of said joysticks.
     */
    public static void move(Gamepad gamepad) {
        // Setting variables
        lx = gamepad.left_stick_x;
        ly = -gamepad.left_stick_y;
        rx = gamepad.right_stick_x;
        robotHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Calculating maxPower for saving the ratio of motor powers.
        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        // Calculating adjusted vectors for field centric drive.
        adjustedLx = -ly * Math.sin(robotHeading) + lx * Math.cos(robotHeading);
        adjustedLy = ly * Math.cos(robotHeading) + lx * Math.sin(robotHeading);

        // Giving power to motors.
        motors[LF].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        motors[RF].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        motors[LB].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        motors[RB].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }

    /**
     * Reset imu heading.
     * Current heading is only for testing.
     */
    public static void resetImu() {
        imu.resetYaw();
        imu.resetYaw();
    }
}