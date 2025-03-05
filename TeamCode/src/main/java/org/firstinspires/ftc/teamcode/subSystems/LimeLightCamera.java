package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class LimeLightCamera {
    public static Limelight3A limelight;
    public static int angle;

    public static void initialize(OpMode opMode) {
        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(75);
    }

    public static int getAngle() {
        limelight.start();
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            angle = (int) result.getPythonOutput()[5];
            return angle;
        }
        return angle;
    }
}
