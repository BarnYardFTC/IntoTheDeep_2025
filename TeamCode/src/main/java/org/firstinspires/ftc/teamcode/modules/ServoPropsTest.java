package org.firstinspires.ftc.teamcode.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ServoPropsTest {

    private static final ServoProps servoProps = new ServoProps();

    @Test
    void servoPosTo1Default() {
        assertEquals(1, servoProps.getServoTargetPosition(355));
    }

    @Test
    void servoPosTo0Default() {
        assertEquals(0, servoProps.getServoTargetPosition(0));
    }

    @Test
    void servoPosToHalfDefault() {
        assertEquals((double) 180 / 355, servoProps.getServoTargetPosition(180));
    }

    @Test
    void servoPosToMinus1Error() {
        assertThrows(IllegalArgumentException.class, () -> servoProps.getServoTargetPosition(-355));
    }

    @Test
    void servoPosTo2Error() {
        assertThrows(IllegalArgumentException.class, () -> servoProps.getServoTargetPosition(710));
    }

    @Test
    void servoPosTo1() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(1, servoProps.getServoTargetPosition(177.5));
    }

    @Test
    void servoPost1Quarter() {
        ServoProps servoProps = new ServoProps(355, 0, 0.25);
        assertEquals(1, servoProps.getServoTargetPosition(88.75));
    }

    @Test
    void servoPosToHalf() {
        ServoProps servoProps = new ServoProps(355, 0, 0.5);
        assertEquals(0.5, servoProps.getServoTargetPosition(88.75));
    }
}