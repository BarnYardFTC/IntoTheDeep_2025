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
    private static final DcMotorEx[] Motors = new DcMotorEx[4];
    private static IMU imu;
    private static double maxPower;
    private static double lx;
    private static double ly;
    private static double rx;
    private static double imuHeadingRadians;
    private static double adjustedLx;
    private static double adjustedLy;
    private static final int FL = 0;
    private static final int FR = 1;
    private static final int BL = 2;
    private static final int BR = 3;

    // Initializing function
    public static void init(DcMotorEx leftFront, DcMotorEx rightFront, DcMotorEx leftBack, DcMotorEx rightBack, IMU imu) {
        // Assigning objects to variables
        Motors[FL] = leftFront;
        Motors[BL] = leftBack;
        Motors[FR] = rightFront;
        Motors[BR] = rightBack;
        EgnitionSystem.imu = imu;

        // Setting motors behavior
        EgnitionSystem.Motors[FL].setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.Motors[BL].setDirection(DcMotorSimple.Direction.REVERSE);

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
        Motors[FL].setPower((adjustedLy + adjustedLx + rx) / maxPower);
        Motors[FR].setPower((adjustedLy - adjustedLx - rx) / maxPower);
        Motors[BL].setPower((adjustedLy - adjustedLx + rx) / maxPower);
        Motors[BR].setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}
