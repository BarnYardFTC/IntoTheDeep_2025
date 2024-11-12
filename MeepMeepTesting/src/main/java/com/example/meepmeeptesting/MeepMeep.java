package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeep {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity botTest = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15).build();

        Pose2d initialPose = new Pose2d(-24, -65, Math.toRadians(90));

        botTest.runAction(botTest.getDrive().actionBuilder(initialPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-10, -35.5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-48, -40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-56.5, -56.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-58, -40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-56.5, -56.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-58, -40), Math.toRadians(90))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-25, -16, Math.toRadians(0)), 0)
                .build());

        meepMeep.setBackground(com.noahbres.meepmeep.MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botTest).start();
    }
}