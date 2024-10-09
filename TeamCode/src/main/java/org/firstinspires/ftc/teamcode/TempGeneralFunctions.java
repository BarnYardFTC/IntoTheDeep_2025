package org.firstinspires.ftc.teamcode;

import java.lang.Exception;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class TempGeneralFunctions {
    // Position arrival


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
