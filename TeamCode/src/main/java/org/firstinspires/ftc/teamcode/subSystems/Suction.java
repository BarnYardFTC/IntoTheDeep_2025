package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Suction {
    private static CRServo suction;

    public Suction(OpMode opMode) {
        suction = opMode.hardwareMap.get(CRServo.class, "suction");
    }

    public static CRServo getSuction() {
        return suction;
    }
}
