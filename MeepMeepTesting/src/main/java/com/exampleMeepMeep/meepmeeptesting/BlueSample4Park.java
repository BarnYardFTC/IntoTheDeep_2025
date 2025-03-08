package com.exampleMeepMeep.meepmeeptesting;

import com.exampleMeepMeep.Autonomous.Coordinates.BlueSampleCoordinatesMeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class BlueSample4Park {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(500);

        RoadRunnerBotEntity robot = new DefaultBotBuilder(meepMeep).setConstraints(100, 100, Math.toRadians(720), Math.toRadians(720), 14).build();
        robot.setDimensions(15, 16.5);

        robot.runAction(robot.getDrive().actionBuilder(BlueSampleCoordinatesMeepMeep.getStart())
                .setTangent(BlueSampleCoordinatesMeepMeep.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getScore0(), BlueSampleCoordinatesMeepMeep.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getIntake2(), BlueSampleCoordinatesMeepMeep.getIntake2HeadingChange())

                .setTangent(BlueSampleCoordinatesMeepMeep.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getScore(), BlueSampleCoordinatesMeepMeep.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getIntake3(), BlueSampleCoordinatesMeepMeep.getIntake2HeadingChange())

                .setTangent(BlueSampleCoordinatesMeepMeep.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getScore(), BlueSampleCoordinatesMeepMeep.getIntake2HeadingChange())

                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getIntake4(), BlueSampleCoordinatesMeepMeep.getIntake4HeadingChange())

                .setTangent(BlueSampleCoordinatesMeepMeep.getScoreTangent())
                .splineToLinearHeading(BlueSampleCoordinatesMeepMeep.getScore(), BlueSampleCoordinatesMeepMeep.getIntake4HeadingChange())

                .strafeToLinearHeading(BlueSampleCoordinatesMeepMeep.getPark1().component1(), BlueSampleCoordinatesMeepMeep.getPark1().heading)

                .strafeToConstantHeading(BlueSampleCoordinatesMeepMeep.getPark2().component1())

                .build());


        meepMeep.setBackground(com.noahbres.meepmeep.MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(robot).start();
    }
}
