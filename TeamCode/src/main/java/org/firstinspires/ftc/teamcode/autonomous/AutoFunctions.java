package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

public class AutoFunctions {

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

    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.close();
            return false;
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
            if (LiftArm.isVertical()){
                return false;
            }
            return true;
        }
    }

    public class LiftArmHorizontal implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
            return false;
        }
    }
}
