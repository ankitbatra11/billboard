package com.abatra.billboard.admob;

import androidx.annotation.Nullable;

import com.abatra.android.wheelie.lifecycle.ILifecycleObserver;
import com.abatra.android.wheelie.pattern.Observable;
import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RewardedInterstitialAdOptOutTimer implements Runnable,
        Observable<RewardedInterstitialAdOptOutTimer.Listener>, ILifecycleObserver {

    private final int seconds;
    private final Observable<Listener> listeners = Observable.copyOnWriteArraySet();
    private final AtomicBoolean paused = new AtomicBoolean(false);
    @Nullable
    private ScheduledExecutorService scheduledExecutorService;
    @Nullable
    private ScheduledFuture<?> scheduledFuture;
    @Nullable
    private AtomicInteger secondsLeft;

    public RewardedInterstitialAdOptOutTimer(int seconds) {
        this.seconds = seconds;
        Preconditions.checkArgument(this.seconds > 0, "Invalid seconds field");
    }

    @Override
    public void addObserver(Listener observer) {
        listeners.addObserver(observer);
    }

    @Override
    public void removeObserver(Listener observer) {
        listeners.removeObserver(observer);
    }

    public void start() {

        paused.set(false);

        // if we few seconds left from previous display use them
        if (secondsLeft == null) {
            secondsLeft = new AtomicInteger(seconds);
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        if (!paused.get()) {
            if (secondsLeft != null) {
                int seconds = secondsLeft.getAndDecrement();
                if (seconds > 0) {
                    listeners.forEachObserver(type -> type.onTimeLeft(seconds));
                } else {
                    listeners.forEachObserver(Listener::onTimerEnded);
                }
            }
        }
    }

    @Override
    public void onPause() {
        paused.set(true);
    }

    @Override
    public void onResume() {
        paused.set(false);
    }

    @Override
    public void onDestroy() {
        listeners.removeObservers();
        destroyThread();
    }

    private void destroyThread() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            scheduledExecutorService = null;
        }
    }

    public interface Listener {

        void onTimeLeft(int seconds);

        void onTimerEnded();
    }
}
