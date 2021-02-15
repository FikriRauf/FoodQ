package com.example.entreprenuershipproject.fragment.tester;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.entreprenuershipproject.R;

import java.util.Locale;

public class TesterTimer extends Fragment {
    private static final long START_TIME_IN_MILLISECONDS = 6000;

    TextView
            testerTimer,
            changeWord;
    Button
            timer_start_button,
            timer_rest_button;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMilliSeconds = START_TIME_IN_MILLISECONDS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tester_timer, container, false);

        setLayoutViewsToLocalVariables(root);
        startTimer();

//        timer_start_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTimerRunning) {
//                    pauseTimer();
//                } else {
//                    startTimer();
//                }
//
//            }
//        });
//
//        timer_rest_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetTimer();
//            }
//        });

        updateCountDownText();

        return root;
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMilliSeconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                changeWord.setText("hello");
                mTimerRunning = false;
                timer_start_button.setText("start");
                timer_start_button.setVisibility(View.INVISIBLE);
                timer_rest_button.setVisibility(View.VISIBLE);

            }
        }.start();

        mTimerRunning = true;
        timer_start_button.setText("pause");
        timer_rest_button.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMilliSeconds / 1000) / 60;
        int seconds = (int) (mTimeLeftInMilliSeconds / 1000) % 60;

        String timeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        testerTimer.setText(timeLeftFormat);
    }



    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        timer_start_button.setText("start");
        timer_rest_button.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMilliSeconds = START_TIME_IN_MILLISECONDS;
        updateCountDownText();
        timer_rest_button.setVisibility(View.INVISIBLE);
        timer_start_button.setVisibility(View.VISIBLE);
    }

    private void setLayoutViewsToLocalVariables(View root) {
        testerTimer = root.findViewById(R.id.testerTimer);
        changeWord = root.findViewById(R.id.changeWord);
        timer_start_button = root.findViewById(R.id.timer_start_button);
        timer_rest_button = root.findViewById(R.id.timer_reset_button);
    }
}
