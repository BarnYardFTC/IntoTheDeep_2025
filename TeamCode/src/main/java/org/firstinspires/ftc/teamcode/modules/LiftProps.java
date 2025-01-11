package org.firstinspires.ftc.teamcode.modules;

public class LiftProps extends MotorProps {
    private final double CHAIN_SIZE;
    private final int SLIDE_COUNT;
    private final int TOOTH_OF_FINAL_PART;
    private final double ENCODER_TO_CM;

    public LiftProps() {
        super();
        this.CHAIN_SIZE = 8;
        this.SLIDE_COUNT = 1;
        this.TOOTH_OF_FINAL_PART = 14;
        this.ENCODER_TO_CM = this.ENCODER_TO_DEGREE * 10 / (this.TOOTH_OF_FINAL_PART * CHAIN_SIZE * SLIDE_COUNT);
    }

    public LiftProps(double CHAIN_SIZE, int SLIDE_COUNT, double ENCODER_RESOLUTION, double OUTER_GEAR_RATIO, int TOOTH_OF_FINAL_PART) {
        super(ENCODER_RESOLUTION, OUTER_GEAR_RATIO);
        this.CHAIN_SIZE = CHAIN_SIZE;
        this.SLIDE_COUNT = SLIDE_COUNT;
        this.TOOTH_OF_FINAL_PART = TOOTH_OF_FINAL_PART;
        this.ENCODER_TO_CM = this.ENCODER_TO_DEGREE * 10 / (this.TOOTH_OF_FINAL_PART * this.CHAIN_SIZE * this.SLIDE_COUNT);
    }

    public double getENCODER_TO_CM() {
        return ENCODER_TO_CM;
    }

    public double getCmToEncoders(double cm) {
        return ENCODER_TO_CM * cm;
    }
}
