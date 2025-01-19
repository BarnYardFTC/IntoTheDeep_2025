package org.firstinspires.ftc.teamcode;

// Imports.

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
//
//// TeleOp name.
//@TeleOp(name = "Arm_PID")
//
//public class ArmPIDTuning extends LinearOpMode {
//    private final FtcDashboard dash = FtcDashboard.getInstance();
//
//    @Override
//    public void runOpMode() {
//        LiftArm liftArm = new LiftArm(this);
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//
//        waitForStart();
//
//        // Main Loop
//        while (opModeIsActive()) {
//            LiftArm.liftArmPID();
//
//            telemetry.addData("pos", LiftArm.getTargetPos());
//            telemetry.addData("current", LiftArm.getRightMotor().getCurrentPosition());
//            telemetry.addData("powerR", LiftArm.getRightMotor().getPower());
//            telemetry.addData("powerL", LiftArm.getLeftMotor().getPower());
//            telemetry.update();
//        }
//    }
//}