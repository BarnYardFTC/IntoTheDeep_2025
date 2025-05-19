package org.firstinspires.ftc.teamcode.Testing;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;

@TeleOp(name="Drivetrain test", group="test")
public class TestDrivetrain extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain.initialize(this);
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        waitForStart();
        while(opModeIsActive()){
            Drivetrain.move(gamepadEx);
            if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)) Drivetrain.resetImu();
        }
    }
}
