package org.firstinspires.ftc.teamcode.autonomous.Programs.RedSpecimen;

// Import

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Coordinates.RedSpecimenCoordinates;
import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Config
//@Autonomous(name = "Red_Specimen_Park", group = "Autonomous")

public class RedSpecimenPark extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive ignitionSystem = new MecanumDrive(hardwareMap, RedSpecimenCoordinates.getStart());

        Action park = ignitionSystem.actionBuilder(RedSpecimenCoordinates.getStart())
                .strafeToConstantHeading(RedSpecimenCoordinates.getPark().position).build();

        waitForStart();


        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        park
                )
        );
    }
}