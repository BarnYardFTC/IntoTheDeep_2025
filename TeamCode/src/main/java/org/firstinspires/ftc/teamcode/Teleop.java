package org.firstinspires.ftc.teamcode;

// Imports.
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
    /**
     * Initialize all hardware.
     */
    private void initializeAll() {
        Differential differential = new Differential(this);
        Drivetrain drivetrain = new Drivetrain(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);
        Claw claw = new Claw(this);
    }

    @Override
    public void runOpMode() {
        initializeAll();
        Claw.close();

        waitForStart();

        Differential.reset();
        LiftArm.move(LiftArm.Angle.HORIZONTAL);
        Lift.move(Lift.Pos.RESET);

        // ToDo: In General, why don't the buttons used in here align with the buttons we said we'll use in the TeleOp Flaw Chart?

        /**
        Buttons documentation:
         A: move differential to collect/reset position
         Y: Open/Close claw
         B/RB?/LB?: Movement of Lift
         */
        boolean a_pressed = false,
                x_pressed = false,
                y_pressed = false,
                b_pressed = false;

        // Main Loop
        while (opModeIsActive()) {
            TeleOpFunctions.runAll(gamepad1);

            if (gamepad1.a && !a_pressed) {
                if (Differential.isReseted()) {
                    Differential.collect();
                }
                else {
                    Differential.reset();
                }
            }

// ToDo: Why do we need two buttons to move the differential?
//            if (gamepad1.x && !x_pressed) {
//                if (Differential.isReseted()){
//                    Differential.move(0, 20);
//                }
//                else{
//                    Differential.reset();
//                }
//            }

            if (gamepad1.y && !y_pressed) {
                if (Claw.isOpen()) {
                    Claw.close();
                }
                else {
                    Claw.open();
                }
            }

            if (gamepad1.b && !b_pressed) {
                if (Lift.HIGH_BASKET_POS == Lift.getTargetPosCm()) {
                    Lift.move(Lift.Pos.LOW_BASKET);
                }
                else {
                    Lift.move(Lift.Pos.HIGH_BASKET);
                }
            }
// ToDo: Don't we already move the lift using b???
            if (gamepad1.right_trigger > 0.1 && LiftArm.isHorizontal() && Lift.targetPosCm + 2 <= 44) {
                Lift.move(1);
            }
            if (gamepad1.right_trigger > 0.1 && !LiftArm.isHorizontal() && Lift.targetPosCm + 2 <= 52) {
                Lift.move(1);
            }

            if (gamepad1.left_trigger > 0.1 && Lift.targetPosCm - 2 >= 0) {
                Lift.move(-1);
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

            a_pressed = gamepad1.a;
            x_pressed = gamepad1.x;
            y_pressed = gamepad1.y;
            b_pressed = gamepad1.b;

            telemetry.addData("angle", LiftArm.getRightMotor().getCurrentPosition() / LiftArm.RIGHT_MOTOR.getENCODERS_PER_DEGREE());
            telemetry.addData("rD", Differential.servos[0].getPosition());
            telemetry.addData("lD", Differential.servos[1].getPosition());
            telemetry.addData("LiftCM", Lift.targetPosCm);
            telemetry.update();
        }
    }
}