package com.spring.countdowndemo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/6/15.
 * 倒计时弹框
 */

public class CountDownDialog extends Dialog implements CountDownUtil.CountDownListener {

    private static final long DURATION = 3000;
    private static final long INTERVAL = 1000;

    private TextView tvCountdown;

    private CountDownUtil timerCountDown;
    private VolumeUtil volumeUtil;
    private AlphaAnimation alphaAnimation;
    private ScaleAnimation scaleAnimation;

    public CountDownDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public CountDownDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.dialog_answer_countdown, null);
        setContentView(view);
        tvCountdown = findViewById(R.id.tv_countdown);

        volumeUtil = new VolumeUtil();
        alphaAnimation = new AlphaAnimation(0, 1);
        scaleAnimation = new ScaleAnimation(0.5f, 1.5f, 0.5f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        timerCountDown = new CountDownUtil(DURATION, INTERVAL, this);
        timerCountDown.start();

    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        volumeUtil.releaseMusic();
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
            alphaAnimation = null;
        }
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
            scaleAnimation = null;
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        volumeUtil.playMusic(getContext(), R.raw.audio1);
        tvCountdown.setText(millisUntilFinished / INTERVAL + "");
        // 设置透明度渐变动画

        //设置动画持续时间
        alphaAnimation.setDuration(INTERVAL / 2);
        tvCountdown.startAnimation(alphaAnimation);
        // 设置缩放渐变动画
        scaleAnimation.setDuration(INTERVAL / 2);
        tvCountdown.startAnimation(scaleAnimation);
    }

    @Override
    public void onFinish() {
        volumeUtil.playMusic(getContext(), R.raw.audio2);
        dismiss();
    }
}
