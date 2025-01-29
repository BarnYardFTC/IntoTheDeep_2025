package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TimerHelper {
    private final ElapsedTime timer = new ElapsedTime();
    private boolean isRunning = false;

    public boolean hasElapsed(int durationMilliseconds){
        if (!isRunning) {
            timer.reset();  // Start the timer
            isRunning = true;
            return false;
        }
        if (timer.milliseconds() >= durationMilliseconds) {
            isRunning = false;  // Reset state for next use
            return true;
        }
        return false;
    }
}
