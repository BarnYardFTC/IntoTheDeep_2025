package org.firstinspires.ftc.teamcode.tests;

// Imports.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.firstinspires.ftc.teamcode.modules.ServoProps;
import org.junit.jupiter.api.Test;

class ServoPropsTest {

    private static final ServoProps servoProps = new ServoProps(); // Object fo testing.

    // Tests for general functionality and edge cases of the getServoTargetPosition method.

    @Test
    void positionTo1Default() {
        assertEquals(1, servoProps.getServoTargetPosition(355));
    }

    @Test
    void positionTo0Default() {
        assertEquals(0, servoProps.getServoTargetPosition(0));
    }

    @Test
    void positionToHalfDefault() {
        assertEquals((double) 180 / 355, servoProps.getServoTargetPosition(180));
    }

    @Test
    void positionToMinus1Error() {
        assertThrows(IllegalArgumentException.class, () -> servoProps.getServoTargetPosition(-355));
    }

    @Test
    void positionTo2Error() {
        assertThrows(IllegalArgumentException.class, () -> servoProps.getServoTargetPosition(710));
    }

    @Test
    void positionTo1GearRatio() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(1, servoProps.getServoTargetPosition(177.5));
    }

    @Test
    void positionTo0GearRatio() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(0, servoProps.getServoTargetPosition(0));
    }

    @Test
    void positionToHalfGearRatio() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(0.5, servoProps.getServoTargetPosition(88.75));
    }

    @Test
    void positionToHalfStartPosition() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(0.5, servoProps.getServoTargetPosition(88.75));
    }

    @Test
    void positionTo0MaxRotation() {
        ServoProps servoProps = new ServoProps(180, 0, 1);
        assertEquals(0, servoProps.getServoTargetPosition(0));
    }

    @Test
    void positionToHalfMaxRotation() {
        ServoProps servoProps = new ServoProps(180, 0, 1);
        assertEquals(0.5, servoProps.getServoTargetPosition(90));
    }

    @Test
    void positionTo1StartPosition() {
        ServoProps servoProps = new ServoProps(180, 0, 1);
        assertEquals(1, servoProps.getServoTargetPosition(180));
    }
}