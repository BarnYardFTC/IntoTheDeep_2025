package org.firstinspires.ftc.teamcode.modules;

public class LiftProps extends MotorProps{
    private final double PITCH_LENGTH;
    private final int SLIDE_COUNT;
    private final double ENCODER_TO_CM;

    public LiftProps() {
        super();
        PITCH_LENGTH = 1188;
        SLIDE_COUNT = 1;
        ENCODER_TO_CM = this.ENCODER_RESOLUTION * this.OUTER_GEAR_RATIO * PITCH_LENGTH * SLIDE_COUNT / 10;
    }

    public LiftProps(double PITCH_LENGTH, int SLIDE_COUNT, double ENCODER_RESOLUTION, double OUTER_GEAR_RATIO) {
        super(ENCODER_RESOLUTION, OUTER_GEAR_RATIO);
        this.PITCH_LENGTH = PITCH_LENGTH;
        this.SLIDE_COUNT = SLIDE_COUNT;
        ENCODER_TO_CM = this.ENCODER_RESOLUTION * this.OUTER_GEAR_RATIO * this.PITCH_LENGTH * this.SLIDE_COUNT / 10;
    }

    public double getEncodersToCm(double cm) {
        return ENCODER_TO_CM * cm;
    }
}
