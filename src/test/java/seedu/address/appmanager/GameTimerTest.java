package seedu.address.appmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.application.Platform;
import javafx.stage.Stage;

import seedu.address.appmanager.timer.GameTimer;

@ExtendWith(ApplicationExtension.class)
public class GameTimerTest {

    private GameTimer dummyTimer;
    private AppManagerStub appManagerStub;

    /**
     * Initializes the JavaFX Application Thread when this test starts.
     */
    @Start
    public void start(Stage stage) {

    }

    @Test
    public void run_noHints_success() {
        CountDownLatch cd = new CountDownLatch(1);
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            appManagerStub.setCountDownLatch(cd);
            dummyTimer.run();
        });
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(appManagerStub.skipCalled);
        assertTrue(appManagerStub.timerDisplayUpdated);

        // todo: create own implementation of clock that can support manual elapsing of time, to avoid using
    }

    @Test
    public void run_durationIsNegative() {
        CountDownLatch cd = new CountDownLatch(1);
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    -100, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            appManagerStub.setCountDownLatch(cd);
            dummyTimer.run();
        });
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(appManagerStub.skipCalled);
        assertFalse(appManagerStub.timerDisplayUpdated);
        assertEquals(dummyTimer.getElapsedMillis(), 0);
    }

    @Test
    public void run_durationIsZero() {
        CountDownLatch cd = new CountDownLatch(1);
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    0, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            appManagerStub.setCountDownLatch(cd);
            dummyTimer.run();
        });
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(appManagerStub.skipCalled);
        assertTrue(appManagerStub.timerDisplayUpdated);
        assertEquals(dummyTimer.getElapsedMillis(), 0);
    }


    @Test
    public void abortTimer() {
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.abortTimer();
            // abortTimer() is supposed to pass timeLeft = 0 to the timerDisplay.
            assertTrue(appManagerStub.timeLeftIsZero);
        });
    }

    @Test
    public void run_withHints_success() {
        CountDownLatch cd = new CountDownLatch(1);
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    70, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.initHintTimingQueue(10, 70);
            appManagerStub.setCountDownLatch(cd);
            dummyTimer.run();
        });
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(appManagerStub.hintsUpdated);
        assertTrue(appManagerStub.skipCalled);
        assertTrue(appManagerStub.timerDisplayUpdated);
    }

    @Test
    public void getElapsedMillis() {
        CountDownLatch cd = new CountDownLatch(1);
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    70, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            appManagerStub.setCountDownLatch(cd);
            dummyTimer.run();
        });
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(dummyTimer.getElapsedMillis(), 70);
    }



    // Stub Class for AppManager's methods that will be called from GameTimer
    private class AppManagerStub {

        private boolean skipCalled = false;
        private boolean hintsUpdated = false;
        private boolean timerDisplayUpdated = false;
        private boolean timeLeftIsZero = false;

        /** Utility to ensure that all timerTasks within a dummyTimer (on the JavaFX Applicaiton Thread)
         *  are run before test worker thread is allowed to progress. */
        private CountDownLatch countDownLatch;

        /**
         * Sets the countDownlatch of this AppManagerStub as {@code cd}.
         */
        private void setCountDownLatch(CountDownLatch cd) {
            countDownLatch = cd;
        }

        /**
         * Decreases the countDownLatch by 1.
         */
        private void decreaseCountDown() {
            if (countDownLatch != null) {
                this.countDownLatch.countDown();
            }
        }

        /**
         * Notifies the stub that the Skip call back has been executed, and to allow the test worker thread
         * to progress by decreasing the countDownLatch.
         */
        private void skipOver() {
            skipCalled = true;
            if (countDownLatch != null) {
                decreaseCountDown();
            }
        }

        private void updateHints() {
            hintsUpdated = true;
        }

        private void updateTimer(String timerMessage, long timeLeft, long totalTimeGiven) {
            timeLeftIsZero = timeLeft == 0 ? true : false;
            timerDisplayUpdated = true;
        }

    }

}
