package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class RedSampleTrajectories {
    public static MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSampleCoordinates.getStart());
    public static TrajectoryActionBuilder parkFromStart = ignitionSystem.actionBuilder(RedSampleCoordinates.getStart())
            .waitSeconds(26)
            .strafeToConstantHeading(RedSampleCoordinates.getPark1().position);
}
