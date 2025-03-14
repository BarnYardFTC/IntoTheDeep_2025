package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Timer {
    private final ElapsedTime timer = new ElapsedTime();
    private boolean isRunning = false;

    public boolean hasElapsed(int durationMilliseconds){
        if (!isRunning) {
            reset();  // Start the timer
            isRunning = true;
            return false;
        }
        if (timer.milliseconds() >= durationMilliseconds) {
            isRunning = false;  // Reset state for next use
            return true;
        }
        return false;
    }

    public void reset(){
        timer.reset();
    }
}
