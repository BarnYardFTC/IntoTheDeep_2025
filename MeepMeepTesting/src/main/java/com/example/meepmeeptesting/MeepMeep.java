package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeep {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity botRed = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12).build();
        RoadRunnerBotEntity botBlue = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12).build();

        Pose2d initialPoseRed = new Pose2d(8, -63, Math.toRadians(90));
        Pose2d initialPoseBlue = new Pose2d(-8, 63, Math.toRadians(270));

        botRed.runAction(botRed.getDrive().actionBuilder(initialPoseRed)
                .strafeToConstantHeading(new Vector2d(-4, -33))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(33, -36), Math.toRadians(90))


                .splineToConstantHeading(new Vector2d(33, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(46, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(46, -54), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(46, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(54, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(54, -54), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(54, -13), Math.toRadians(90))

                .splineToConstantHeading(new Vector2d(62, -13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(62, -54), Math.toRadians(90))

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-1, -33), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(46, -59))

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(2, -33), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(46, -59))

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(5, -33), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(46, -59))

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(8, -33), Math.toRadians(90))

                .strafeToConstantHeading(new Vector2d(46, -59))

                .build());

        botBlue.runAction(botBlue.getDrive().actionBuilder(initialPoseBlue)
                .strafeToConstantHeading(new Vector2d(4, 33))

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
                .splineToConstantHeading(new Vector2d(-62, 54), Math.toRadians(270))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(1, 33), Math.toRadians(270))

                .strafeToConstantHeading(new Vector2d(-46, 59))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-2, 33), Math.toRadians(270))

                .strafeToConstantHeading(new Vector2d(-46, 59))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-5, 33), Math.toRadians(270))

                .strafeToConstantHeading(new Vector2d(-46, 59))

                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-8, 33), Math.toRadians(270))

                .strafeToConstantHeading(new Vector2d(-46, 59))

                .build());

        meepMeep.setBackground(com.noahbres.meepmeep.MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botRed).start()
                .addEntity(botBlue).start();
    }
}