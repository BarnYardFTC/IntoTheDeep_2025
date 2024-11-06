package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity botTest = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15).build();

        Pose2d initialPose = new Pose2d(-24, -58, Math.toRadians(90));

        botTest.runAction(botTest.getDrive().actionBuilder(initialPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-10, -37), Math.toRadians(90))
                .waitSeconds(2)
                .setTangent(Math.toRadians(0))
                .strafeTo(new Vector2d(-48, -40))
                .waitSeconds(2)
                .turn(Math.toRadians(-45))
                .strafeTo(new Vector2d(-55, -55))
                .waitSeconds(2)
                .lineToY(-50)
                .turn(Math.toRadians(45))
                .strafeTo(new Vector2d(-24, -58))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botTest).start();
    }
}