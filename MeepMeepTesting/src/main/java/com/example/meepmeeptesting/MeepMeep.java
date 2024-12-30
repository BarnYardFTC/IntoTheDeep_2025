package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeep {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity botRedSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botBlueSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botRedSample = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botBlueSample = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();

        Pose2d initialPoseRedSpecimen = new Pose2d(8, -63, Math.toRadians(90));
        Pose2d initialPoseBlueSpecimen = new Pose2d(-8, 63, Math.toRadians(270));
        Pose2d initialPoseRedSample = new Pose2d(-30, -63, Math.toRadians(90));
        Pose2d initialPoseBlueSample = new Pose2d(30, 63, Math.toRadians(270));

        botRedSpecimen.runAction(botRedSpecimen.getDrive().actionBuilder(initialPoseRedSpecimen)
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

        botBlueSpecimen.runAction(botBlueSpecimen.getDrive().actionBuilder(initialPoseBlueSpecimen)
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

        botRedSample.runAction(botRedSample.getDrive().actionBuilder(initialPoseRedSample)
                .waitSeconds(26)
                .strafeToConstantHeading(new Vector2d(33, -63))

                .build());

        botBlueSample.runAction(botBlueSample.getDrive().actionBuilder(initialPoseBlueSample)
                .waitSeconds(26)
                .strafeToConstantHeading(new Vector2d(-33, 63))

                .build());

        meepMeep.setBackground(com.noahbres.meepmeep.MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botRedSpecimen).start()
                .addEntity(botBlueSpecimen).start()
                .addEntity(botRedSample).start()
                .addEntity(botBlueSample).start();
    }
}