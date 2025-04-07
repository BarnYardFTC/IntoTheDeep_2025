package org.firstinspires.ftc.teamcode;


import android.hardware.Sensor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;

@Autonomous (name= "Basic Autonomi" )
@Config
public class TrainingBot extends LinearOpMode {

    public static int targetPos = 0;
    public static double power = 1;
    private DcMotor rightArm;
    private DcMotor leftArm;


    @Override
    public void runOpMode() throws InterruptedException {

        rightArm = hardwareMap.get(DcMotor.class, "rightArm");
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);

        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            rightArm.setTargetPosition(targetPos);
            leftArm.setTargetPosition(targetPos);
            rightArm.setPower(power);
            leftArm.setPower(power);
            rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
    }
}

