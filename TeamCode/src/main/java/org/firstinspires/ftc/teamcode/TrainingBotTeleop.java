package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "trainingbotTeleop")
public class TrainingBotTeleop extends LinearOpMode {
        private DcMotor frontLeft, frontRight, backLeft, backRight;
        private TouchSensor touchSensor;
    private final ElapsedTime timer = new ElapsedTime();
    public static double power = 1;
    private DcMotor rightArm;
    private DcMotor leftArm;
        @Override
        public void runOpMode() {
            initialize();
            waitForStart();
            while (opModeIsActive()) {
                moveOpmode();
                if(gamepad1.right_bumper){

                    rightArm.setPower(power);
                    leftArm.setPower(power);
                    rightArm.setTargetPosition(500);
                    leftArm.setTargetPosition(500);
                    rightArm.getTargetPosition();
                    leftArm.getTargetPosition();
                    rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                } else if (touchSensor.isPressed()) {
                    timer.reset();
                    while(timer.seconds()<=1){
                        frontLeft.setPower(0.5);
                        backLeft.setPower(0.5);
                        frontRight.setPower(0.5);
                        backRight.setPower(0.5);

                    }


                } else{
                    rightArm.setPower(0);
                    leftArm.setPower(0);
                    telemetry.addData("right arm power", rightArm.getPower());
                    telemetry.addData("left arm power", leftArm.getPower());
                    telemetry.addData("left arm pos", leftArm.getCurrentPosition());
                    telemetry.addData("right arm pos", rightArm.getCurrentPosition());
                    telemetry.update();
                }
            }
        }
    private void initialize() {
        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        rightArm = hardwareMap.get(DcMotor.class, "rightArm");
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);

        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");


        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void moveOpmode(){
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;
        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(backLeftPower),
                Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));
        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
}
