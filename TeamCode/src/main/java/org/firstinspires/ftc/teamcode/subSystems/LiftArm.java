package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

@Config
public class LiftArm {
    private static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.

    private static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, 1); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, 1); // Left's motor props.

    private static final int VERTICAL_POS = 90; // Angle for moving the lift arm to a vertical position.
    private static final int HORIZONTAL_POS = 0; // Angle for moving the lift arm to a horizontal position.

    //ToDo: set correct values.
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double f = 0.01;
    public static int targetAngle; // Target angle of the arm.
    private static PIDController controller; // PID controller.
    private static int targetPos; // Target position of the right motor.

    public LiftArm(OpMode opMode) {
        motors[RIGHT] = opMode.hardwareMap.get(DcMotorEx.class, "right");
        motors[LEFT] = opMode.hardwareMap.get(DcMotorEx.class, "left");

        motors[LEFT].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        controller = new PIDController(p, i, d);
    }

    public static int getTargetPos() {
        return targetPos;
    }

    public static void setTargetPos(int targetPos) {
        LiftArm.targetPos = targetPos;
    }

    /**
     * Informs if the arm is horizontal.
     *
     * @return - If the current arm's position is horizontal.
     */
    public static boolean getHorizontalPos() {
        return motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(45);
    }

    public static int getTargetAngle() {
        return targetAngle;
    }

    public static DcMotorEx getRightMotor() {
        return motors[RIGHT];
    }

    public static DcMotorEx getLeftMotor() {
        return motors[LEFT];
    }

    public static void liftArmPID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = Math.abs(motors[RIGHT].getCurrentPosition());
        targetPos = RIGHT_MOTOR.getAngleToEncoder(targetAngle);

        // Calculate PIDF values.
        double pid = controller.calculate(currentPos, targetPos);
        double ff = Math.cos(targetAngle) * f;

        // Calculate motor power.
        double power = pid + ff;

        // Giving power to motors.
        motors[RIGHT].setPower(power);
        motors[LEFT].setPower(power);
    }

    public static void move(Angle angle) {
        switch (angle) {
            case VERTICAL:
                targetAngle = VERTICAL_POS;
                break;
            case HORIZONTAL:
                targetAngle = HORIZONTAL_POS;
                break;
        }
    }

    public enum Angle {
        VERTICAL, HORIZONTAL
    }
}
