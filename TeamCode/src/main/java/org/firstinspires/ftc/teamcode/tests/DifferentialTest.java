package org.firstinspires.ftc.teamcode.tests;

// Imports.

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DifferentialTest {

    // Tests of the used position of the differential servos in the robot.

    @Test
    void rightReset() {
        Assertions.assertEquals((double) 175 / 355, Differential.getRightServo().getServoTargetPosition(0));
    }

    @Test
    void rightRollSampleUnload() {
        Assertions.assertEquals((double) 175 / 355 + ((double) 180 / 355) - ((double) 90 / 355), Differential.getRightServo().getServoTargetPosition(Differential.getRollAngleSampleUnload()));
    }

    @Test
    void rightPitchSpecimenIntake() {
        assertEquals(0.0, Differential.getRightServo().getServoTargetPosition(-Differential.getPitchAngleSpecimenIntake()));
    }

    @Test
    void rightRollSpecimenUnload() {
        assertEquals((double) 175 / 355 + (double) 180 / 355, Differential.getRightServo().getServoTargetPosition(Differential.getRollAngleSpecimenIntake()));
    }

    @Test
    void rightPitchSpecimenUnload() {
        assertEquals((double) 175 / 355 - (double) 90 / 355, Differential.getRightServo().getServoTargetPosition(-Differential.getPitchAngleSpecimenUnload()));
    }

    @Test
    void leftReset() {
        Assertions.assertEquals(0, Differential.getLeftServo().getServoTargetPosition(0));
    }

    @Test
    void leftRollSampleUnload() {
        Assertions.assertEquals(((double) 180 / 355) - ((double) 90 / 355), Differential.getLeftServo().getServoTargetPosition(Differential.getRollAngleSampleUnload()));
    }

    @Test
    void leftPitchSpecimenIntake() {
        assertEquals((double) 175 / 355, Differential.getLeftServo().getServoTargetPosition(Differential.getPitchAngleSpecimenIntake()));
    }

    @Test
    void leftRollSpecimenUnload() {
        assertEquals((double) 180 / 355, Differential.getLeftServo().getServoTargetPosition(Differential.getRollAngleSpecimenIntake()));
    }

    @Test
    void leftPitchSpecimenUnload() {
        assertEquals((double) 90 / 355, Differential.getLeftServo().getServoTargetPosition(Differential.getPitchAngleSpecimenUnload()));
    }
}