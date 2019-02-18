package asiantech.internship.summer.servicesbroadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Objects;

import asiantech.internship.summer.R;

public class MusicService extends Service {
    public static final String KEY_DURATION_TIME = "Key duration time";
    public static final String KEY_CURRENT_TIME = "Key current time";
    public static final String KEY_PAUSE_NOTIFICATION = "Key pause notification";
    public static final String KEY_PLAY_NOTIFICATION = "Key play notification";
    public static final String KEY_CLOSE_NOTIFICATION = "Key close notification";
    public static final String KEY_START_ACTIVITY = "Key start activity";

    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.dung_quen_ten_anh_hoa_vinh);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case MusicActivity.ACTION_PLAY:
                    mMediaPlayer.start();
                    mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Intent timePlayerIntent = new Intent(MusicActivity.ACTION_UPDATE_SEEK_BAR);
                            timePlayerIntent.putExtra(KEY_DURATION_TIME, mMediaPlayer.getDuration());
                            timePlayerIntent.putExtra(KEY_CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                            sendBroadcast(timePlayerIntent);
                        }

                        @Override
                        public void onFinish() {
                        }
                    };
                    mCountDownTimer.start();
                    break;
                case MusicActivity.ACTION_PAUSE:
                    mMediaPlayer.pause();
                    break;
                case MusicActivity.ACTION_PASS_PROGRESS_SEEK_BAR:
                    mMediaPlayer.seekTo(Objects.requireNonNull(intent.getExtras()).getInt(MusicActivity.KEY_PASS_PROGRESS));
                    break;
                case MusicActivity.ACTION_FOCUS_IMAGE_NOTIFICATION:
                    if (Objects.requireNonNull(intent.getExtras()).getBoolean(MusicActivity.KEY_IS_PLAYING)) {
                        mMediaPlayer.pause();
                        Intent musicPauseIntent = new Intent(MusicActivity.ACTION_NOTIFICATION_PAUSE);
                        musicPauseIntent.putExtra(KEY_PAUSE_NOTIFICATION, true);
                        sendBroadcast(musicPauseIntent);
                    } else {
                        mMediaPlayer.start();
                        Intent musicPlayIntent = new Intent(MusicActivity.ACTION_NOTIFICATION_PLAY);
                        musicPlayIntent.putExtra(KEY_PLAY_NOTIFICATION, false);
                        sendBroadcast(musicPlayIntent);
                    }
                    break;
                case MusicActivity.ACTION_CLOSE_NOTIFICATION:
                    Intent notificationCloseIntent = new Intent(MusicActivity.ACTION_CLOSE_NOTIFICATION);
                    notificationCloseIntent.putExtra(KEY_CLOSE_NOTIFICATION, mMediaPlayer.isPlaying());
                    sendBroadcast(notificationCloseIntent);
                    break;
                case MusicActivity.ACTION_CREATE_ACTIVITY:
                    Intent activityCreateIntent = new Intent(MusicActivity.ACTION_CREATE_ACTIVITY);
                    activityCreateIntent.putExtra(KEY_START_ACTIVITY, mMediaPlayer.isPlaying());
                    sendBroadcast(activityCreateIntent);
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
