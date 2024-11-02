package org.firstinspires.ftc.teamcode.tests;

// Imports.

import org.firstinspires.ftc.teamcode.subSystems.IntakeArm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntakeArmTest {

    // Tests of the used position of the intake arm servos in the robot.

    @Test
    void rightCollect() {
        Assertions.assertEquals(0.5 + (double) 30 / 180, IntakeArm.getRightServo().getServoTargetPosition(IntakeArm.getAngleIntake()));
    }

    @Test
    void rightReset() {
        Assertions.assertEquals(0.5, IntakeArm.getRightServo().getServoTargetPosition(0));
    }
}