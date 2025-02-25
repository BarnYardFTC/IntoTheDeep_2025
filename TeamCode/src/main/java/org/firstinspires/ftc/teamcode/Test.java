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
        Robot.initializeOpMode(this);
        Lift.initialize(this);
        LiftArm.initialize(this);
        Claw.initialize(this);
        Differential.initialize(this);
        Robot.teleopSetup();
        waitForStart();
        Actions.runBlocking(
            new ParallelAction(
                    Lift.liftPID(),
                    LiftArm.liftArmPID(),
                    Robot.displayTelemetry(),
                    new SequentialAction(
                            LiftArm.liftArmVertical(),
//                            Robot.hasElapsed(LiftArm.LIFT_ARM_SETTLE_TIME),
                            Robot.loosenClawGrip(),
//                            Lift.moveLift(Lift.Pos.SPECIMEN_SCORE),
                            Differential.differentialScore(),
                            Claw.openClaw(),
                            Lift.moveLift(Lift.Pos.RESET),
                            LiftArm.liftArmHorizontal()
                    )
            )
        );
    }
}
