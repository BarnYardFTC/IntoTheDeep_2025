package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Suction {
    private static CRServo suction;

    public Suction(OpMode opMode) {
        suction = opMode.hardwareMap.get(CRServo.class, "suction");
        suction.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public static CRServo getSuction() {
        return suction;
    }
}
