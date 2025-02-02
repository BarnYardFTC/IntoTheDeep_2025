
/*
    This class is not needed anymore
 */


//package org.firstinspires.ftc.teamcode.autonomous;
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//
//import org.firstinspires.ftc.teamcode.Robot;
//import org.firstinspires.ftc.teamcode.subSystems.Claw;
//import org.firstinspires.ftc.teamcode.subSystems.Differential;
//import org.firstinspires.ftc.teamcode.subSystems.Lift;
//import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
//
//public class AutoFunctions {
//
//    public Action displayTelemetry(){
//        return new autonomousTelemtry();
//    }
//
//    public Action openClaw(){
//        return new OpenClaw();
//    }
//    public Action closeClaw() {
//        return new CloseClaw();
//    }
//
//    public Action liftArmPID() {
//        return new LiftArmPID();
//    }
//    public Action liftArmHorizontal () {
//        return new LiftArmHorizontal();
//    }
//    public Action liftArmVertical() {
//        return new LiftArmVertical();
//    }
//
//    public Action differentialCollectSpecimen(){
//        return new DifferentialCollectSpecimen();
//    }
//    public Action differentialCollectSample(){
//        return new DifferentialCollectSample();
//    }
//    public Action differentialReset(){
//        return new DifferentialReset();
//    }
//
//    public Action liftPID(){
//        return new LiftPID();
//    }
//    public Action moveLift(Lift.Pos pos){
//        if (pos == Lift.Pos.HIGH_CHAMBER){
//            return new LiftHighChamber();
//        }
//        else if (pos == Lift.Pos.POST_SCORE_HIGH_CHAMBER){
//            return new LiftPostScoreHighChamber();
//        }
//        else if (pos == Lift.Pos.RESET){
//            return new LiftReset();
//        }
//        else if (pos == Lift.Pos.LOW_BASKET){
//            return new LiftLowBasket();
//        }
//        else if (pos == Lift.Pos.HIGH_BASKET){
//            return new LiftHighBasket();
//        }
//        else if (pos == Lift.Pos.SAMPLE_COLLECTION){
//            return new LiftSampleCollection();
//        }
//        return new LiftReset();
//    }
//
//
//    public Action setup(){
//        return new Setup();
//    }
//
//
//    public class Setup implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Robot.autonomousSetup();
//            return false;
//        }
//    }
//
//    public class autonomousTelemtry implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Robot.activateTelemetry();
//            return true;
//        }
//    }
//
//    public class LiftPID implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.PID();
//            return true;
//        }
//    }
//    public class LiftHighChamber implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.HIGH_CHAMBER);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//    public class LiftHighBasket implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.HIGH_BASKET);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//    public class LiftLowBasket implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.LOW_BASKET);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//    public class LiftSampleCollection implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.LOW_BASKET);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//    public class LiftPostScoreHighChamber implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.POST_SCORE_HIGH_CHAMBER);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//    public class LiftReset implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Lift.move(Lift.Pos.RESET);
//            return !Lift.arrivedTargetPos();
//        }
//    }
//
//    public class OpenClaw implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Claw.open();
//            return !Claw.isOpen();
//        }
//    }
//
//    public class CloseClaw implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Claw.close();
//            return !Claw.isClose();
//        }
//    }
//
//    public class LiftArmPID implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            LiftArm.PID();
//            return true;
//        }
//    }
//
//    public class LiftArmVertical implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            LiftArm.move(LiftArm.Angle.VERTICAL);
//            return !LiftArm.isVertical();
//        }
//    }
//
//    public class LiftArmHorizontal implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            LiftArm.move(LiftArm.Angle.HORIZONTAL);
//            return !LiftArm.isHorizontal();
//        }
//    }
//    public class DifferentialCollectSpecimen implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Differential.collectSpecimen();
//            return Differential.isReset();
//        }
//    }
//    public class DifferentialCollectSample implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Differential.collectSample();
//            return Differential.isReset();
//        }
//    }
//    public class DifferentialReset implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            Differential.reset();
//            return !Differential.isReset();
//        }
//    }
//
//}
