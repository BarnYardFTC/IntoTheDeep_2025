package org.firstinspires.ftc.teamcode.autonomous.Trajactories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

public class BlueSpecimen {
    public static Pose2d initialPose = new Pose2d(-8, 63, Math.toRadians(270));
    public static MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, initialPose);

    public static TrajectoryActionBuilder trajectoryBuilder = ignitionSystem.actionBuilder(initialPose)
            .strafeToConstantHeading(new Vector2d(-8, 33))

            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-33, 36), Math.toRadians(270))


            .splineToConstantHeading(new Vector2d(-33, 13), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(-46, 13), Math.toRadians(270))
            .splineToConstantHeading(new Vector2d(-46, 54), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(-46, 13), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(-54, 13), Math.toRadians(270))
            .splineToConstantHeading(new Vector2d(-54, 54), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(-54, 13), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(-62, 13), Math.toRadians(270))
            .splineToConstantHeading(new Vector2d(-62, 54), Math.toRadians(270));
}
