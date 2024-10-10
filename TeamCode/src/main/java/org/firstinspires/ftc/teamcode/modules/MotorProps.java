package org.firstinspires.ftc.teamcode.modules;

// Imports.
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorProps {

    // Find if encoder is smaller, bigger or equal to a given position.
    public static boolean isEncoderPosition(DcMotorEx motorEx, int position, String direction) throws Exception {
        switch (direction) {
            case "<":
                return motorEx.getCurrentPosition() < position;
            case ">":
                return motorEx.getCurrentPosition() > position;
            case "=":
                return motorEx.getCurrentPosition() == position;
        }

        throw new Exception("No such operation");
    }
}
