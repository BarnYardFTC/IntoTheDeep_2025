package org.firstinspires.ftc.teamcode.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ServoPropsTest {

    @Test
    void servoPosTo1() {
        ServoProps servoProps = new ServoProps();
        assertEquals(1, servoProps.getServoTargetPosition(355));
    }

    @Test
    void servoPosTo0() {
        ServoProps servoProps = new ServoProps();
        assertEquals(0, servoProps.getServoTargetPosition(0));
    }

    @Test
    void servoPosToMinus1() {
        ServoProps servoProps = new ServoProps();
        assertThrows(IllegalArgumentException.class, () -> {
            servoProps.getServoTargetPosition(-355);
        });
    }
}