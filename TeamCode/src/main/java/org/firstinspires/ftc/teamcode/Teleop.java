package org.firstinspires.ftc.teamcode;

// Imports.

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;

import java.io.PrintWriter;
import java.io.StringWriter;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {
//        Initialization.initializeAll();

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            // We use a try & catch block so that any error in the main loop will stop the robot and add the error line to the telemetry.
            try {
//                Initialization.resetRobot();
//                Functions.runAll();
                Drivetrain.move(Functions.gamepadEx1.gamepad);
                if (Functions.gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
                    Drivetrain.resetImu();
                }
                telemetry.log().clear();
                telemetry.addData("Heading", Drivetrain.getRobotHeading());
                telemetry.update();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                telemetry.log().clear();
                telemetry.addData("stackTrace", stackTrace);
                telemetry.update();
                throw e;
            }
        }
    }
}