package org.firstinspires.ftc.teamcode;

//Imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EgnitionSystem {
    // Variables
    static private DcMotorEx[] Motors;
    static private IMU imu;
    static private double maxPower;
    static private double lx;
    static private double ly;
    static private double rx;
    static private double imuHeadingRadians;
    static private double adjustedLx;
    static private double adjustedLy;
    static private int fl = 0;
    static private int fr = 1;
    static private int bl = 2;
    static private int br = 3;

    // Initializing function
    public static void init(DcMotorEx fl_wheel, DcMotorEx fr_wheel, DcMotorEx bl_wheel, DcMotorEx br_wheel, IMU imu) {
        // Assigning objects to variables
        EgnitionSystem.Motors = new DcMotorEx[]{fl_wheel, fr_wheel, bl_wheel, br_wheel};
        EgnitionSystem.imu = imu;

        // Setting motors behavior
        EgnitionSystem.Motors[fl].setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.Motors[bl].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : Motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Setting imu behavior
        EgnitionSystem.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        EgnitionSystem.imu.resetYaw();

        // Setting variables
        maxPower = 1;
        lx = 0;
        ly = 0;
        rx = 0;
        imuHeadingRadians = 0;
        adjustedLx = 0;
        adjustedLy = 0;
    }

    // Main drive function
    public static void driveTeleOp(Gamepad gamepad1) {
        // Setting variables
        lx = gamepad1.left_stick_x;
        ly = -gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        imuHeadingRadians = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Calculating maxPower for saving the ratio of motor powers
        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        // Calculating adjusted powers for field centric drive
        adjustedLx = -ly * Math.sin(imuHeadingRadians) + lx * Math.cos(imuHeadingRadians);
        adjustedLy = ly * Math.cos(imuHeadingRadians) + lx * Math.sin(imuHeadingRadians);

        // Giving power to motors
        Motors[fl].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        Motors[fr].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        Motors[bl].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        Motors[br].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}
