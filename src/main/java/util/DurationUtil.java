package util;

import java.util.Random;

public class DurationUtil {

    private int duration60 = 60;
    private int duration90 = 90;
    private int duration120 = 120;

    int[] durations = {
            duration60,
            duration90,
            duration120
    };

    public int getDuration() {
        Random random = new Random();
        return durations[random.nextInt(durations.length)];
    }
}
