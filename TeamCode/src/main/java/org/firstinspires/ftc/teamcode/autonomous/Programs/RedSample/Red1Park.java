package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSample;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSampleCoordinates;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.BlueSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSampleCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
//@Autonomous(name = "Red_Sample_1_Park", group = "Autonomous")

public class Red1Park extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSampleCoordinates.getStart());

        Action scorePreLoad = ignitionSystem.actionBuilder(RedSampleCoordinates.getStart())
                .setTangent(RedSampleCoordinates.getScoreTangent())
                .splineToLinearHeading(RedSampleCoordinates.getScore(), RedSampleCoordinates.getIntake2HeadingChange())

                .build();

        Action park = ignitionSystem.actionBuilder(RedSampleCoordinates.getScore())
                .strafeToLinearHeading(RedSampleCoordinates.getPark1().component1(), RedSampleCoordinates.getPark1().heading)

                .strafeToConstantHeading(RedSampleCoordinates.getPark2().component1())
                .build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        scorePreLoad,
                        park
                )
        );
    }
}