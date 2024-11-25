package org.firstinspires.ftc.teamcode.subSystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

public class LiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.

    public static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, 3); // Right's motor props.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, 3); // Left's motor props.

    private static final int VERTICAL = 90; // Angle for moving the lift arm to a vertical position.
    private static final int HORIZONTAL = 0; // Angle for moving the lift arm to a horizontal position.
    private static int targetAngle; // Target angle of the arm.

    private static PIDController controller; // PID controller.
    public static double p = 0.005;
    public static double i = 0;
    public static double d = 0.0002;
    public static double f = 0.03;
    public static int targetPos; // Target position of the right motor.

    /**
     * Initializing all hardware.
     *
     * @param right - Hardware for right motor.
     * @param left  - Hardware for left motor.
     */
    public static void init(DcMotorEx right, DcMotorEx left) {
        // Assigning the given motors to the motors in the class.
        motors[RIGHT] = right;
        motors[LEFT] = left;

        motors[RIGHT].setDirection(DcMotorEx.Direction.REVERSE);
        motors[LEFT].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        makeHorizontal(); // Moves arm to starting position.
        controller = new PIDController(p, i, d);
    }

    public static void liftArmPIDF() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = Math.abs(motors[RIGHT].getCurrentPosition());
        targetPos = RIGHT_MOTOR.getAngleToEncoder(targetAngle);

        // Calculate PIDF values.
        double pid = controller.calculate(currentPos, targetPos);
        double ff = Math.cos(Math.toRadians(targetPos / RIGHT_MOTOR.getENCODER_TO_DEGREE())) * f;

        // Calculate motor power.
        double power = pid + ff;

        // Giving power to motors.
        motors[RIGHT].setPower(power);
        motors[LEFT].setPower(power);
    }

    // Sets arm target position to a horizontal position.
    public static void makeHorizontal() {
        targetAngle = HORIZONTAL;
    }

    // Sets arm target position to a vertical position.
    public static void makeVertical() {
        targetAngle = VERTICAL;
    }

    /**
     * Informs if the arm is horizontal.
     *
     * @return - If the current arm's position is horizontal.
     */
    public static boolean isHorizontal() {
        return motors[RIGHT].getCurrentPosition() < RIGHT_MOTOR.getAngleToEncoder(40);
    }
}
