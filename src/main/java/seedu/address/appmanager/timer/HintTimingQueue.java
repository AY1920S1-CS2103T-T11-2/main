package seedu.address.appmanager.timer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that represents a queue of timestamps for the GameTimer to request for more hints.
 */
public class HintTimingQueue {
    private Queue<Long> hintTimesQueue;

    HintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion) {
        this.hintTimesQueue = new LinkedList<>();
        int numberOfTimings = hintFormatSize;

        // Rate of Hint Characters being shown is linear (time between each hint character is constant).
        // Hints are only showed after 1/4 * 8000ms have passed.
        long delta = (long) (8000 * 0.75) / (numberOfTimings - 1);

        // Populate the hintTimesQueue with timings that are evenly spaced out.
        for (int i = 0; i < numberOfTimings; i++) {
            long currentTiming = (long) (timeAllowedPerQuestion * 0.75) - (i * delta);

            if (currentTiming <= 0) {
                hintTimesQueue.add(50L); // Last Hint Shown at 50ms
            } else {
                hintTimesQueue.add(currentTiming);
            }
        }
    }

    public long pollNextTimeToUpdate() {
        System.out.println("pollNextTimeToUpdate <---------is queue empty?: " + hintTimesQueue.isEmpty());
        return hintTimesQueue.poll();
    }

    public boolean isEmpty() {
        return hintTimesQueue.isEmpty();
    }
}
