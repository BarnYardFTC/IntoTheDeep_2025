package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

public class AutoFunctions {

    public Action openClaw(){
        return new OpenClaw();
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public Action liftArmPID() {
        return new LiftArmPID();
    }
    public Action liftArmHorizontal () {
        return new LiftArmHorizontal();
    }
    public Action liftArmVertical() {
        return new LiftArmVertical();
    }

    public Action differentialCollectSpecimen(){
        return new DifferentialCollectSpecimen();
    }
    public Action differentialCollectSample(){
        return new DifferentialCollectSample();
    }
    public Action differentialReset(){
        return new DifferentialReset();
    }

    public Action liftPID(){
        return new LiftPID();
    }
    public Action moveLift(Lift.Pos pos){
        if (pos == Lift.Pos.HIGH_CHAMBER){
            return new LiftHighChamber();
        }
        else if (pos == Lift.Pos.RESET){
            return new LiftReset();
        }
        return new LiftReset();
    }

    public class LiftPID implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Lift.liftPID();
            return true;
        }
    }
    public class LiftHighChamber implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

        }
    }
    public class LiftReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

        }
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.open();
            return !Claw.isOpen();
        }
    }

    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.close();
            return !Claw.isClose();
        }
    }

    public class LiftArmPID implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LiftArm.liftArmPID();
            return true;
        }
    }

    public class LiftArmVertical implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
            return !LiftArm.isVertical();
        }
    }

    public class LiftArmHorizontal implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
            return !LiftArm.isHorizontal();
        }
    }
    public class DifferentialCollectSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.collectSpecimen();
            return Differential.isReseted();
        }
    }
    public class DifferentialCollectSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.collectSample();
            return Differential.isReseted();
        }
    }
    public class DifferentialReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.reset();
            return Differential.isReseted();
        }
    }


}
