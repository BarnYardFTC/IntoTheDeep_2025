package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

@Autonomous
public class Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Differential.initialize(this);
        Differential.move(0, 175);
        Claw.initialize(this);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.y){
                Claw.open();
            }
            if (gamepad1.a){
                Claw.close();
            }
        }
    }
}
