package com.spring.countdowndemo;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Administrator on 2018/7/9.
 * 声音播放
 */

public class VolumeUtil {


    private MediaPlayer mediaPlayer;

    public void playMusic(Context context,int music) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(context, music);
        mediaPlayer.start();
        mediaPlayer.setVolume(1f, 1f);
    }

    public void releaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
