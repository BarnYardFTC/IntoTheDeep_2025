package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        RoadRunnerBotEntity bot1 = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RoadRunnerBotEntity botTest = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d initialPose1 = new Pose2d(12, 62, Math.toRadians(90));
        Pose2d initialPose2 = new Pose2d(0, 0, Math.toRadians(180));

        bot1.runAction(bot1.getDrive().actionBuilder(initialPose1)
                .lineToYSplineHeading(33, Math.toRadians(0))
                .setTangent(Math.toRadians(90))
                .lineToY(48)
                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5)
                .build());

        botTest.runAction(botTest.getDrive().actionBuilder(initialPose2)
                .setTangent(Math.toRadians(90))
                .splineTo(new Vector2d(-48, 48), Math.toRadians(180))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botTest)
                .start();
    }
}