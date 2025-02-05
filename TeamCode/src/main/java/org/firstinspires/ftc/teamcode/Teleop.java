package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.initializeTeleop(this);
        Robot.teleopSetup();
        waitForStart();
        while(opModeIsActive()){

        }
    }
}