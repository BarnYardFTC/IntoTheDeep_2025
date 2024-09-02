package org.firstinspires.ftc.teamcode;

//Imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EgnitionSystem {
    // Variables
    static private DcMotor fl_wheel;
    static private DcMotor fr_wheel;
    static private DcMotor bl_wheel;
    static private DcMotor br_wheel;
    static private IMU imu;
    static private double maxPower;
    static private double lx;
    static private double ly;
    static private double rx;
    static private double heading;
    static private double adjustedLx;
    static private double adjustedLy;

    // Initializing
    public static void init(DcMotor fl_wheel, DcMotor fr_wheel, DcMotor bl_wheel, DcMotor br_wheel, IMU imu) {
        EgnitionSystem.fl_wheel = fl_wheel;
        EgnitionSystem.fr_wheel = fr_wheel;
        EgnitionSystem.bl_wheel = bl_wheel;
        EgnitionSystem.br_wheel = br_wheel;

        EgnitionSystem.bl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        EgnitionSystem.fl_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        fl_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        EgnitionSystem.imu = imu;

        EgnitionSystem.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        EgnitionSystem.imu.resetYaw();

        //Setting variables
        maxPower = 1;
        lx = 0;
        ly = 0;
        rx = 0;
        heading = 0;
        adjustedLx = 0;
        adjustedLy = 0;
    }

    public static void driveTeleOp(Gamepad gamepad1) {
        //Setting variables
        lx = gamepad1.left_stick_x;
        ly = -gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;

        maxPower = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
        adjustedLy = ly * Math.cos(heading) + lx * Math.sin(heading);

        //Giving power to motors
        fl_wheel.setPower((adjustedLy + adjustedLx + rx) / maxPower);
        bl_wheel.setPower((adjustedLy - adjustedLx + rx) / maxPower);
        fr_wheel.setPower((adjustedLy - adjustedLx - rx) / maxPower);
        br_wheel.setPower((adjustedLy + adjustedLx - rx) / maxPower);
    }
}
