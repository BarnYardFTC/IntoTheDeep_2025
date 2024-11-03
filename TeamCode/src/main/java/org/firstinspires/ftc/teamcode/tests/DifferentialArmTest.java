package org.firstinspires.ftc.teamcode.tests;

// Imports.

import org.firstinspires.ftc.teamcode.subSystems.DifferentialArm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DifferentialArmTest {

    // Tests of the used position of the differential arm servos in the robot.

    @Test
    void rightUnload() {
        Assertions.assertEquals(1, DifferentialArm.getRightServo().getServoTargetPosition(DifferentialArm.getAngleSpecimenUnload()));
    }

    @Test
    void rightReset() {
        Assertions.assertEquals(0.5, DifferentialArm.getRightServo().getServoTargetPosition(0));
    }

    @Test
    void leftUnload() {
        Assertions.assertEquals(1, DifferentialArm.getLeftServo().getServoTargetPosition(DifferentialArm.getAngleSpecimenUnload()));
    }

    @Test
    void leftReset() {
        Assertions.assertEquals(0.5, DifferentialArm.getLeftServo().getServoTargetPosition(0));
    }
}