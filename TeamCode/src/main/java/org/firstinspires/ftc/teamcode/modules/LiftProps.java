package org.firstinspires.ftc.teamcode.modules;

public class LiftProps extends MotorProps {
    private final double CHAIN_SIZE;
    private final int SLIDE_COUNT;
    private final int TOOTH_OF_FINAL_PART;
    private final double ENCODERS_PER_CM;

    public LiftProps() {
        super();
        this.CHAIN_SIZE = 8;
        this.SLIDE_COUNT = 1;
        this.TOOTH_OF_FINAL_PART = 14;
        this.ENCODERS_PER_CM = this.ENCODERS_PER_DEGREE * 10 / (this.TOOTH_OF_FINAL_PART * CHAIN_SIZE * SLIDE_COUNT);
    }

    public LiftProps(double CHAIN_SIZE, int SLIDE_COUNT, double ENCODER_RESOLUTION, double OUTER_GEAR_RATIO, int TOOTH_OF_FINAL_PART) {
        super(ENCODER_RESOLUTION, OUTER_GEAR_RATIO);
        this.CHAIN_SIZE = CHAIN_SIZE;
        this.SLIDE_COUNT = SLIDE_COUNT;
        this.TOOTH_OF_FINAL_PART = TOOTH_OF_FINAL_PART;
        this.ENCODERS_PER_CM = this.ENCODERS_PER_DEGREE * 10 / (this.TOOTH_OF_FINAL_PART * this.CHAIN_SIZE * this.SLIDE_COUNT);
    }

    public double getENCODERS_PER_CM() {
        return ENCODERS_PER_CM;
    }

    public double getCmToEncoders(double cm) {
        return ENCODERS_PER_CM * cm;
    }
}
