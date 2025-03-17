package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.ServoProps;

@Config
public class Differential {
    public static final Servo[] servos = new Servo[2]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps((double) 175 / 355); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(0); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.
    // Angles for moving the differential.
    public static int SAMPLE_PITCH = 5;
    public static int RESET_PITCH = 170;
    public static  int SCORE_BASKET_PITCH = 140;

    public static int PREPARE_SPECIMEN_PITCH = 130;
    public static int SCORE_SPECIMEN_PITCH = 160;

    public static int MOVEMENT_DURATION = 400;

    public static int DIFFERENTIAL_0_ROLL = 0;
    public static int DIFFERENTIAL_45_ROLL = 45;
    public static int DIFFERENTIAL_90_ROLL = 100;
    public static int DIFFERENTIAL_135_ROLL = 160;

    public static int DIFFERENTIAL_45_PITCH = 10;
    public static int DIFFERENTIAL_90_PITCH = 10;
    public static int DIFFERENTIAL_135_PITCH = 10;

    public static int DEFAULT_ROLL = 100;
    public static int defaultRoll;
    public static int DEFAULT_PITCH = 100;

    public static int currentRollAngle;
    public static int currentPitchAngle;

    public static void initialize(OpMode opMode) {
        servos[RIGHT] = opMode.hardwareMap.get(Servo.class, "rightDifferential");
        servos[LEFT] = opMode.hardwareMap.get(Servo.class, "leftDifferential");

        currentRollAngle = 0;
        currentPitchAngle = 0;

        defaultRoll = 0;
    }

    /**
     * Move each servo based on a given target angle and an axis for movement.
     * The logic for the movement is in the class ServoProps.
     * The action set the servos position once in a loop until the moved value is changed.
     *
     * @param angleRoll  - Wanted end angle of the differential on the roll axis.
     * @param anglePitch - Wanted end angle of the differential on the pitch axis.
     */
    public static void move(int angleRoll, int anglePitch) {
        servos[RIGHT].setPosition(RIGHT_SERVO.getTargetPosition(angleRoll - anglePitch));
        servos[LEFT].setPosition(LEFT_SERVO.getTargetPosition(angleRoll + anglePitch));
        currentPitchAngle = anglePitch;
        currentRollAngle = angleRoll;
    }

    /**
     * Resets differential to it's starting position.
     * The action set the servos position once in a loop until the reseted value is changed.
     */
    public static void reset() {
        move(0, RESET_PITCH);
        servos[RIGHT].setDirection(Servo.Direction.FORWARD);
        servos[LEFT].setDirection(Servo.Direction.FORWARD);
    }

    public static void resetRoll(){
        move(0, currentPitchAngle);
    }

    public static void prepareSpecimen(){
        move(180, PREPARE_SPECIMEN_PITCH);
    }

    public static void scoreSpecimen(){
        move(currentRollAngle, SCORE_SPECIMEN_PITCH);
    }

    /**
     * Moves differential to the sample intake position.
     */
    public static void collectSample() {
        move(currentRollAngle, SAMPLE_PITCH);
    }

    public static void moveToDefault(){
        move(defaultRoll, DEFAULT_PITCH);
    }

    public static void scoreBasket(){
        move(90, SCORE_BASKET_PITCH);
    }

    /**
     * Checks if the differential is in the reseted position.
     *
     * @return - If the differential is reseted.
     */
    public static boolean isDefault(){
        return currentPitchAngle == DEFAULT_PITCH;
    }
    public static boolean isReset() {
        return currentPitchAngle == RESET_PITCH;
    }
    public static boolean isScoreBasket(){
        return currentPitchAngle == SCORE_BASKET_PITCH;
    }
    public static boolean isCollectSample() {
        return currentPitchAngle < 50;
    }
    public static boolean isSpecimenPrepared(){
        return currentPitchAngle == PREPARE_SPECIMEN_PITCH;
    }
    public static boolean isSpecimenScored(){
        return currentPitchAngle == SCORE_SPECIMEN_PITCH;
    }
    public static boolean isResetRoll(){
        return currentRollAngle == 0;
    }


    public static void printData(Telemetry telemetry){
        telemetry.addData("current pitch angle: ", currentPitchAngle);
        telemetry.addData("current roll angle: ", currentRollAngle);
    }

    /**
     * Autonomous Actions - Actions which can be used in the autonomous programs.
     */
    private static class ResetRollAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.resetRoll();
            return !Differential.isResetRoll();
        }
    }
    public static Action resetRollAction(){
        return new ResetRollAction();
    }

    private static class MoveToDefaultAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.moveToDefault();
            return !Differential.isDefault();
        }
    }
    public static Action moveToDefaultAction(){
        return new MoveToDefaultAction();
    }

    private static class moveToLimeLightAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.move(0, 20);
            return false;
        }
    }
    public static Action  moveToLimeLightAction(){
        return new moveToLimeLightAction();
    }

    private static class DifferentialPrepareSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.prepareSpecimen();
            return !Differential.isSpecimenPrepared();
        }
    }
    public static Action differentialPrepareSpecimen(){
        return new DifferentialPrepareSpecimen();
    }

    private static class DifferentialScoreSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.scoreSpecimen();
            return !Differential.isSpecimenScored();
        }
    }
    public static Action differentialScoreSpecimen(){
        return new DifferentialScoreSpecimen();
    }
    private static class CollectSampleAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.collectSample();
            return !isCollectSample();
        }
    }
    public static Action CollectSampleAction(){
        return new CollectSampleAction();
    }

    private static class CollectSample4Action implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.move(45, 0);
            return false;
        }
    }
    public static Action CollectSample4Action(){
        return new CollectSample4Action();
    }

    private static class DifferentialReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.reset();
            return !Differential.isReset();
        }
    }
    public static Action differentialReset(){
        return new DifferentialReset();
    }


    private static class DifferentialScoreBasket implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.scoreBasket();
            return !Differential.isScoreBasket();
        }
    }

    public static Action differentialScoreBasket(){
        return new DifferentialScoreBasket();
    }

}