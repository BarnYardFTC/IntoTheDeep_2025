package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class BlueSampleTrajectories {
    public static MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, BlueSampleCoordinates.getStart());
    public static TrajectoryActionBuilder parkFromStart = ignitionSystem.actionBuilder(BlueSampleCoordinates.getStart())
            .waitSeconds(26)
            .strafeToConstantHeading(BlueSampleCoordinates.getPark().position);
}
