package org.firstinspires.ftc.teamcode.subSystems;

//Imports.

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain {
    public static final int LB = 2; // Back left's motor index.
    public static final DcMotorEx[] motors = new DcMotorEx[4]; // Motors array.
    private static final int LF = 0; // Front left's motor index.
    private static final int RF = 1; // Front right's motor index.
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

    private static double speed_adjustment = 1;

    public static final double SLOW_MODE_DIVIDER = 2.3;

    public static void initialize(OpMode opMode) {
        motors[LF] = opMode.hardwareMap.get(DcMotorEx.class, "leftFront");
        motors[RF] = opMode.hardwareMap.get(DcMotorEx.class, "rightFront");
        motors[LB] = opMode.hardwareMap.get(DcMotorEx.class, "leftBack");
        motors[RB] = opMode.hardwareMap.get(DcMotorEx.class, "rightBack");
        imu = opMode.hardwareMap.get(IMU.class, "imu");

        // Reversing some motors so that positive power = positive-direction wheel spin.
        motors[LF].setDirection(DcMotorEx.Direction.REVERSE);
        motors[LB].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Setting imu attributes.

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.LEFT)));

        imu.resetYaw(); // Reset imu heading.

        resetVariables(); // Setting initial values to the variables.
    }

    public static boolean isTurned90(){
        return -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > 50 && -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < 135 ||
                -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < -50 && -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > -135;

    }


    /**
     * Get the robot heading given from the imu.
     *
     * @return - Current robot heading.
     */
    public static double getRobotHeading() {
        return -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public static double getSpeedAdjustment() {
        return speed_adjustment;
    }
    public static boolean isSlowed() {
        return speed_adjustment == SLOW_MODE_DIVIDER;
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
    public static void move(GamepadEx gamepad) {
        // Setting variables
        lx = gamepad.getLeftX();
        ly = gamepad.getLeftY();
        rx = gamepad.getRightX();
        robotHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Calculating maxPower for saving the ratio of motor powers.
        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        // Calculating adjusted vectors for field centric drive.
        adjustedLx = -ly * Math.sin(robotHeading) + lx * Math.cos(robotHeading);
        adjustedLy = ly * Math.cos(robotHeading) + lx * Math.sin(robotHeading);

        // Giving power to motors.
        motors[LF].setPower((adjustedLy + adjustedLx + rx) / maxPower / speed_adjustment);
        motors[RF].setPower((adjustedLy - adjustedLx - rx) / maxPower / speed_adjustment);
        motors[LB].setPower((adjustedLy - adjustedLx + rx) / maxPower / speed_adjustment);
        motors[RB].setPower((adjustedLy + adjustedLx - rx) / maxPower / speed_adjustment);
    }

    public static void changeHeading(double power) {
        // Giving power to motors.
        motors[LF].setPower(power);
        motors[RF].setPower(-power);
        motors[LB].setPower(power);
        motors[RB].setPower(-power);
    }

    /**
     * Reset imu heading.
     * Current heading is only for testing.
     */
    public static void resetImu() {
        imu.resetYaw();
        imu.resetYaw();
    }

    public static void slowMode() {
        speed_adjustment = SLOW_MODE_DIVIDER;
    }
    public static void regularMode() {
        speed_adjustment = 1;
    }
}