package com.spring.countdowndemo;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2018/7/9.
 * 自定义倒计时
 */

public class CountDownUtil {

    private static final int MSG = 1;
    /**
     * 倒计时时长已毫秒为单位
     */
    private final long mMillisInFuture;

    /**
     * 每隔多少毫秒记一次时
     */
    private final long mCountdownInterval;
    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    private long mDuration;//时长 毫秒为单位

    public CountDownUtil(long millisInFuture, long countDownInterval, CountDownListener countDownListener) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        mCountDownListener = countDownListener;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * 开始计时
     */
    public synchronized final CountDownUtil start() {
        mCancelled = false;
        mDuration = mMillisInFuture;

        if (mMillisInFuture <= 0) {
            if (mCountDownListener != null) {
                mCountDownListener.onFinish();
            }
            return this;
        }
        if (mCountDownListener != null) {
            mCountDownListener.onTick(mDuration);
        }
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG), mCountdownInterval);
        return this;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mCancelled) {
                return;
            }
            mDuration -= mCountdownInterval;
            if (mDuration <= 0) {
                if (mCountDownListener != null) {
                    mCountDownListener.onFinish();
                }
            } else {
                if (mCountDownListener != null) {
                    mCountDownListener.onTick(mDuration);
                }
                mHandler.sendMessageDelayed(obtainMessage(MSG), mCountdownInterval);
            }

        }
    };
    private CountDownListener mCountDownListener;

    public void setCountDownListener(CountDownListener countDownListener) {
        this.mCountDownListener = countDownListener;
    }

    public interface CountDownListener {
        /**
         * 倒计时
         */
        void onTick(long millisUntilFinished);

        /**
         * 倒计时完成
         */
        void onFinish();

    }
}
