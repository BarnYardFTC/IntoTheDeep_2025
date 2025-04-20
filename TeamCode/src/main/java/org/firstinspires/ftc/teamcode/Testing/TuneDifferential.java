package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Differential;

@TeleOp(name = "TuneDifferential", group="test")
public class TuneDifferential extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Differential.initialize(this);
        telemetry.addLine("Waiting For Start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            Differential.move(0,0);
            telemetry.addData("AnglePitch", Differential.currentPitchAngle);
            telemetry.addData("AngleRoll", Differential.currentRollAngle);
            telemetry.update();
        }
    }
}
