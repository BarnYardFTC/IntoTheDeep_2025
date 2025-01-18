package com.exampleMeepMeep.meepmeeptesting;

import com.exampleMeepMeep.Autonomous.Coordinates.BlueSpecimenCoordinatesMeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueSpecimenPark {
    public static void main(String[] args) {
        com.noahbres.meepmeep.MeepMeep meepMeep = new com.noahbres.meepmeep.MeepMeep(800);

        RoadRunnerBotEntity robot = new DefaultBotBuilder(meepMeep).setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 14).build();

        robot.runAction(robot.getDrive().actionBuilder(BlueSpecimenCoordinatesMeepMeep.getStart())
                .waitSeconds(26)
                .strafeToConstantHeading(BlueSpecimenCoordinatesMeepMeep.getPark().position)

                .build());


        meepMeep.setBackground(com.noahbres.meepmeep.MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(robot).start();
    }
}
