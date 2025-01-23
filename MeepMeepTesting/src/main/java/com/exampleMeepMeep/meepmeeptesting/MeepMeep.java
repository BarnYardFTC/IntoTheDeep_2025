package com.exampleMeepMeep.meepmeeptesting;

import com.exampleMeepMeep.Autonomous.Coordinates.BlueSampleCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.BlueSpecimenCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.RedSampleCoordinatesMeepMeep;
import com.exampleMeepMeep.Autonomous.Coordinates.RedSpecimenCoordinatesMeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeep {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity botRedSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.4).build();
        RoadRunnerBotEntity botBlueSpecimen = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botRedSample = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();
        RoadRunnerBotEntity botBlueSample = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14).build();

        botRedSpecimen.setDimensions(15, 16.5);
        botBlueSpecimen.setDimensions(15, 16.5);
        botBlueSample.setDimensions(15, 16.5);
        botRedSample.setDimensions(15, 16.5);

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
                .splineToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position, RedSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore2().component1())

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore3().component1())

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore4().component1())

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(RedSpecimenCoordinatesMeepMeep.getScore5().component1())

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
                .splineToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position, BlueSpecimenCoordinatesMeepMeep.getStart().heading)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore2().component1())

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore3().component1())

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore4().component1())

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getIntake().position)

                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getScore5().component1())

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