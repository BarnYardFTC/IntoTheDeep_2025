package org.firstinspires.ftc.teamcode;

//Imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EgnitionSystem {
    // Variables
    static private DcMotor [] Motors;
    static private IMU imu;
    static private double maxPower;
    static private double lx;
    static private double ly;
    static private double rx;
    static private double imuHeadingRadians;
    static private double adjustedLx;
    static private double adjustedLy;

    // Initializing function
    public static void init(DcMotor fl_wheel, DcMotor fr_wheel, DcMotor bl_wheel, DcMotor br_wheel, IMU imu) {
        // Assigning objects to variables
        EgnitionSystem.Motors[0] = fl_wheel;
        EgnitionSystem.Motors[1] = fr_wheel;
        EgnitionSystem.Motors[2] = bl_wheel;
        EgnitionSystem.Motors[3] = br_wheel;
        EgnitionSystem.imu = imu;

        // Setting motors behavior
        EgnitionSystem.Motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.Motors[2].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotor motor : Motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        Motors[0].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        Motors[1].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        Motors[2].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        Motors[3].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}
