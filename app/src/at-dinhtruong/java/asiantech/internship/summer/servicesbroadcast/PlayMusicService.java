package asiantech.internship.summer.servicesbroadcast;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class PlayMusicService extends Service {
    public static final String PAUSE_NOTIFICATION = "Pause";
    public static final String PLAY_NOTIFICATION = "Play";
    public static final String CLOSE_NOTIFICATION = "Close";
    public static final String DURATION_TIME = "Duration time";
    public static final String CURRENT_TIME = "Current time";
    public static final String START_ACTIVITY = "Start activity";

    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;

    public PlayMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.music);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ServicesBroadcastActivity.PLAY_ACTION: {
                    mMediaPlayer.start();
                    mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Intent timePlayerIntent = new Intent(ServicesBroadcastActivity.ACTION_UPDATE_SEEK_BAR);
                            timePlayerIntent.putExtra(DURATION_TIME, mMediaPlayer.getDuration());
                            timePlayerIntent.putExtra(CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                            sendBroadcast(timePlayerIntent);
                        }

                        @Override
                        public void onFinish() {
                        }
                    };
                    mCountDownTimer.start();
                    break;
                }
                case ServicesBroadcastActivity.PAUSE_ACTION:
                    mMediaPlayer.pause();
                    break;
                case ServicesBroadcastActivity.SEEK_BAR_PROGRESS_ACTION:
                    if (intent.getExtras() != null) {
                        mMediaPlayer.seekTo((intent.getExtras()).getInt(ServicesBroadcastActivity.KEY_PASS_PROGRESS));
                    }
                    break;
                case ServicesBroadcastActivity.FOCUS_IMAGE_NOTIFICATION_ACTION:
                    if (intent.getExtras() != null) {
                        if ((intent.getExtras()).getBoolean(ServicesBroadcastActivity.KEY_IS_PLAYING)) {
                            mMediaPlayer.pause();
                            Intent musicPauseIntent = new Intent(ServicesBroadcastActivity.ACTION_NOTIFICATION_PAUSE);
                            musicPauseIntent.putExtra(PAUSE_NOTIFICATION, true);
                            sendBroadcast(musicPauseIntent);
                        } else {
                            mMediaPlayer.start();
                            Intent musicPlayIntent = new Intent(ServicesBroadcastActivity.ACTION_NOTIFICATION_PLAY);
                            musicPlayIntent.putExtra(PLAY_NOTIFICATION, false);
                            sendBroadcast(musicPlayIntent);
                        }
                    }
                    break;
                case ServicesBroadcastActivity.CLOSE_ACTION:
                    Intent notificationCloseIntent = new Intent(ServicesBroadcastActivity.CLOSE_ACTION);
                    notificationCloseIntent.putExtra(CLOSE_NOTIFICATION, mMediaPlayer.isPlaying());
                    sendBroadcast(notificationCloseIntent);
                    break;
                case ServicesBroadcastActivity.CREATE_ACTIVITY_ACTION:
                    Intent activityCreateIntent = new Intent(ServicesBroadcastActivity.CREATE_ACTIVITY_ACTION);
                    activityCreateIntent.putExtra(START_ACTIVITY, mMediaPlayer.isPlaying());
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

