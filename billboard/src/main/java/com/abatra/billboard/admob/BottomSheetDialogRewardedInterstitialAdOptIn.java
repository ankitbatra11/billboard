package com.abatra.billboard.admob;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.abatra.android.wheelie.lifecycle.ILifecycleOwner;
import com.abatra.billboard.databinding.DialogOptInRewardedInterstitialAdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.concurrent.Callable;

import bolts.Task;

public class BottomSheetDialogRewardedInterstitialAdOptIn implements RewardedInterstitialAdOptIn {

    private final RewardedInterstitialAdOptOutTimer optOutTimer;

    @Nullable
    private String title;
    private String rewardDetails;
    private String optOutActionText;
    private OptOutTimeLeftMessageFactory optOutTimeLeftMessageFactory = OptOutTimeLeftMessageFactory.DEFAULT_FACTORY;

    public BottomSheetDialogRewardedInterstitialAdOptIn(RewardedInterstitialAdOptOutTimer optOutTimer) {
        this.optOutTimer = optOutTimer;
    }

    public BottomSheetDialogRewardedInterstitialAdOptIn setTitle(@Nullable String title) {
        this.title = title;
        return this;
    }

    public BottomSheetDialogRewardedInterstitialAdOptIn setRewardDetails(String rewardDetails) {
        this.rewardDetails = rewardDetails;
        return this;
    }

    public BottomSheetDialogRewardedInterstitialAdOptIn setOptOutActionText(String optOutActionText) {
        this.optOutActionText = optOutActionText;
        return this;
    }

    public BottomSheetDialogRewardedInterstitialAdOptIn setOptOutTimeLeftMessageFactory(OptOutTimeLeftMessageFactory optOutTimeLeftMessageFactory) {
        this.optOutTimeLeftMessageFactory = optOutTimeLeftMessageFactory;
        return this;
    }

    @Override
    public void observeLifecycle(ILifecycleOwner lifecycleOwner) {
        optOutTimer.observeLifecycle(lifecycleOwner);
    }

    @Override
    public void show(Context context, Listener listener) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DialogOptInRewardedInterstitialAdBinding binding = DialogOptInRewardedInterstitialAdBinding.inflate(layoutInflater);
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.title.setText(title);
        binding.rewardDetails.setText(rewardDetails);
        binding.optOut.setText(optOutActionText);
        binding.optOut.setOnClickListener(v -> {
            listener.onUserOptedOut();
            dismiss(bottomSheetDialog);
        });

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();

        optOutTimer.addObserver(new RewardedInterstitialAdOptOutTimer.Listener() {

            @Override
            public void onTimeLeft(int seconds) {
                onUiThread(() -> {
                    binding.optOutTimer.setText(optOutTimeLeftMessageFactory.createMessage(seconds));
                    return null;
                });
            }

            @Override
            public void onTimerEnded() {
                onUiThread(() -> {
                    listener.onOptOutTimeEnded();
                    dismiss(bottomSheetDialog);
                    return null;
                });
            }
        });
        optOutTimer.start();
    }

    private <V> void onUiThread(Callable<V> callable) {
        Task.call(callable, Task.UI_THREAD_EXECUTOR);
    }

    private void dismiss(BottomSheetDialog dialog) {
        optOutTimer.onDestroy();
        dialog.dismiss();
    }

    public interface OptOutTimeLeftMessageFactory {

        OptOutTimeLeftMessageFactory DEFAULT_FACTORY = new OptOutTimeLeftMessageFactory() {
        };

        default String createMessage(int seconds) {
            return String.valueOf(seconds);
        }
    }
}
