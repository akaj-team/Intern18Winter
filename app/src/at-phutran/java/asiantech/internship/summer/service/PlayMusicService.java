package asiantech.internship.summer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.Objects;
import asiantech.internship.summer.R;

public class PlayMusicService extends Service {
    public static final String KEY_DURATION_TIME = "Duration time";
    public static final String KEY_CURRENT_TIME = "Current time";
    public static final String KEY_PAUSE_NOTIFICATION = "Key pause notification";
    public static final String KEY_PLAY_NOTIFICATION = "Key play notification";
    public static final String KEY_CLOSE_NOTIFICATION = "Key close notification";
    public static final String KEY_CHECK_PLAY = "is playing";
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;
    private Intent mIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.hongkong1_nguyentrongtai);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ServiceActivity.ACTION_PLAY:
                    mMediaPlayer.start();
                    mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mIntent = new Intent(ServiceActivity.ACTION_UPDATE_SEEK_BAR);
                            mIntent.putExtra(KEY_DURATION_TIME, mMediaPlayer.getDuration());
                            mIntent.putExtra(KEY_CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                            sendBroadcast(mIntent);
                        }

                        @Override
                        public void onFinish() {
                        }
                    };
                    mCountDownTimer.start();
                    break;
                case ServiceActivity.ACTION_PAUSE:
                    mMediaPlayer.pause();
                    break;
                case ServiceActivity.ACTION_SEEK_BAR:
                    mMediaPlayer.seekTo(Objects.requireNonNull(intent.getExtras()).getInt(ServiceActivity.KEY_PASS_PROGRESS));
                    break;
                case ServiceActivity.ACTION_FOCUS_IMAGE_NOTIFICATION:
                    if (Objects.requireNonNull(intent.getExtras()).getBoolean(ServiceActivity.KEY_IS_PLAYING)) {
                        mMediaPlayer.pause();
                        mIntent = new Intent(ServiceActivity.ACTION_PAUSE_NOTIFICATION);
                        mIntent.putExtra(KEY_PAUSE_NOTIFICATION, true);
                        sendBroadcast(mIntent);
                    } else {
                        mMediaPlayer.start();
                        Intent musicPlayIntent = new Intent(ServiceActivity.ACTION_NOTIFICATION_PLAY);
                        musicPlayIntent.putExtra(KEY_PLAY_NOTIFICATION, false);
                        sendBroadcast(musicPlayIntent);
                    }
                    break;
                case ServiceActivity.ACTION_CLOSE_NOTIFICATION:
                    mIntent = new Intent(ServiceActivity.ACTION_CLOSE_NOTIFICATION);
                    mIntent.putExtra(KEY_CLOSE_NOTIFICATION, mMediaPlayer.isPlaying());
                    sendBroadcast(mIntent);
                    break;
                case ServiceActivity.ACTION_CHECK:
                    mIntent = new Intent(ServiceActivity.ACTION_CHECK);
                    mIntent.putExtra(KEY_CHECK_PLAY, mMediaPlayer.isPlaying());
                    sendBroadcast(mIntent);
                    break;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mCountDownTimer.cancel();
        mMediaPlayer.stop();
        super.onDestroy();
    }
}
