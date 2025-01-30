package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;

@TeleOp
public class TrainingBot extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain.initialize(this);
        GamepadEx gamepad = new GamepadEx(this.gamepad1);
        waitForStart();
        while (opModeIsActive()){
            Drivetrain.move(gamepad);
        }
    }
}
