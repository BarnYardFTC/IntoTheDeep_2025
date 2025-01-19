package org.firstinspires.ftc.teamcode;

// Imports.
import com.google.ar.core.exceptions.DataInvalidFormatException;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {

        Robot.initializeTeleop(this);

        Differential.reset();
        Claw.open();

        waitForStart();

        while (opModeIsActive()) {

            Robot.activateAll();
            Robot.displayTelemetry();
        }
    }
}