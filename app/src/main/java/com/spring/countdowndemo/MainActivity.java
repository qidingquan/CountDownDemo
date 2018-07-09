package com.spring.countdowndemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements CountDownUtil.CountDownListener {

    private static final long DURATION = 10000;
    private static final long INTERVAL = 1000;

    private CountTimeView viewCount;
    private CountDownDialog countDownDialog;
    private CountDownUtil countDownUtil;
    private VolumeUtil volumeUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCount = findViewById(R.id.view_count);
        countDownUtil = new CountDownUtil(DURATION, INTERVAL, this);
        volumeUtil = new VolumeUtil();
    }

    public void startCountDown(View view) {
        countDownUtil.start();
    }

    public void startDialogCountDown(View view) {
        countDownDialog = new CountDownDialog(this);
        countDownDialog.show();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        viewCount.setProgress(DURATION / INTERVAL, (long) (((double) millisUntilFinished / DURATION) * (DURATION / INTERVAL)));
        volumeUtil.playMusic(this, R.raw.audio1);
    }

    @Override
    public void onFinish() {
        viewCount.setProgress(DURATION / INTERVAL, 0);
        volumeUtil.playMusic(this, R.raw.audio2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        volumeUtil.releaseMusic();
    }
}
