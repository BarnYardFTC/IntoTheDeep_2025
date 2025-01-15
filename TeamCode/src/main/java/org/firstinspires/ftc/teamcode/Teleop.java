package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
import org.firstinspires.ftc.teamcode.subSystems.Suction;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    // TODO: Change names of all hardware in configuration

    /**
     * Initialize all hardware.
     */
    private void initializeAll() {
        TeleOpFunctions.setReseted(false);

        Suction suction = new Suction(this);
        Differential differential = new Differential(this);
        Drivetrain drivetrain = new Drivetrain(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);
        Claw claw = new Claw(this);
    }

    @Override
    public void runOpMode() {
        initializeAll();
        Differential.move(0, 175);
        LiftArm.move(LiftArm.Angle.HORIZONTAL);
        Lift.targetPosCm = 0;

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            TeleOpFunctions.runAll(gamepad1);

            if (gamepad1.a) {
                if (Differential.isReseted()) {
                    Differential.collect();
                }
                else {
                    Differential.reset();
                }
            }

            if (gamepad1.x) {
                Differential.move(0, 20);
            }

            if (gamepad1.y) {
                if (Claw.isOpen()) {
                    Claw.close();
                }
                else {
                    Claw.open();
                }
            }

            if (gamepad1.b) {
                if (Lift.HIGH_BASKET_POS == Lift.getTargetPosCm()) {
                    Lift.move(Lift.Pos.LOW_BASKET);
                }
                else {
                    Lift.move(Lift.Pos.HIGH_BASKET);
                }
            }

            if (gamepad1.right_trigger > 0.1 && LiftArm.isHorizontal() && Lift.targetPosCm + 2 <= 44) {
                Lift.targetPosCm += 2;
            }
            if (gamepad1.right_trigger > 0.1 && !LiftArm.isHorizontal() && Lift.targetPosCm + 2 <= 52) {
                Lift.targetPosCm += 2;
            }

            if (gamepad1.left_trigger > 0.1 && Lift.targetPosCm - 2 >= 0) {
                Lift.targetPosCm -= 2;
            }

            if (gamepad1.right_bumper) {
                LiftArm.move(LiftArm.Angle.VERTICAL);
                Lift.move(Lift.Pos.RESET);
                Differential.reset();
            }
            if (gamepad1.left_bumper) {
                LiftArm.move(LiftArm.Angle.HORIZONTAL);
                Differential.reset();
            }

            telemetry.addData("angle", LiftArm.getRightMotor().getCurrentPosition() / LiftArm.RIGHT_MOTOR.getENCODERS_PER_DEGREE());
            telemetry.addData("rD", Differential.servos[0].getPosition());
            telemetry.addData("lD", Differential.servos[1].getPosition());
            telemetry.addData("LiftCM", Lift.targetPosCm);
            telemetry.update();
        }
    }
}