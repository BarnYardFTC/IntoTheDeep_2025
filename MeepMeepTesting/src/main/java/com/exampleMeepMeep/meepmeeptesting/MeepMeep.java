package com.exampleMeepMeep.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.exampleMeepMeep.Autonomous.Coordinates.BlueSampleCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.BlueSpecimenCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.RedSampleCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.RedSpecimenCoordinatesMeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeep {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity botRedSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botBlueSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botRedSample = new DefaultBotBuilder(meepMeep).setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botBlueSample = new DefaultBotBuilder(meepMeep).setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 14).build();

        botRedSpecimen.runAction(botRedSpecimen.getDrive().actionBuilder(RedSpecimenCoordinatesMeepMeep.getStart())
                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore1().position)

                .setTangent(RedSpecimenCoordinatesMeepMeep.getMidWayMoveSpecimensTangent())
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMidWayMoveSpecimens().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimensStart0().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenStart1().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd1().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenStart1().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenStart2().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd2().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenStart2().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenStart3().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd3().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .setTangent(RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore2().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore3().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore4().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(RedSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore5().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getPark().position)

                .build());

        botBlueSpecimen.runAction(botBlueSpecimen.getDrive().actionBuilder(BlueSpecimenCoordinatesMeepMeep.getStart())
                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore1().position)

                .setTangent(BlueSpecimenCoordinatesMeepMeep.getMidWayMoveSpecimensTangent())
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMidWayMoveSpecimens().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimensStart0().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenStart1().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd1().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenStart1().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenStart2().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd2().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenStart2().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenStart3().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getMoveSpecimenEnd3().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .setTangent(BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore2().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore3().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore4().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .setTangent(BlueSpecimenCoordinatesMeepMeep.getStart().heading)
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore5().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getPark().position)

                .build());

        botRedSample.runAction(botRedSample.getDrive().actionBuilder(RedSampleCoordinatesMeepMeep.getStart())
                .waitSeconds(26)
                .strafeToConstantHeading(RedSampleCoordinatesMeepMeep.getPark().position)

                .build());

        botBlueSample.runAction(botBlueSample.getDrive().actionBuilder(BlueSampleCoordinatesMeepMeep.getStart())
                .waitSeconds(26)
                .strafeToConstantHeading(BlueSampleCoordinatesMeepMeep.getPark().position)

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