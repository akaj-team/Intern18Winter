package asiantech.internship.summer.servicesbroadcast;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import asiantech.internship.summer.R;

public class PlayMusicService extends Service {
    public static final String PLAY_OR_PAUSE_NOTIFICATION = "PLAY OR PAUSE";
    public static final String CLOSE_NOTIFICATION = "CLOSE";
    public static final String DURATION = "DURATION";
    public static final String CURRENT_TIME = "CURRENT TIME";
    public static final String START_ACTIVITY = "START ACTIVITY";

    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;

    public PlayMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.music);
        mMediaPlayer.setVolume(100, 100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ServicesBroadcastActivity.PLAY_OR_PAUSE_ACTION: {
                    if (intent.getExtras() != null) {
                        if (!intent.getExtras().getBoolean(ServicesBroadcastActivity.PLAY_OR_PAUSE_ACTION)) {
                            mMediaPlayer.start();
                            mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    Intent timePlayerIntent = new Intent(ServicesBroadcastActivity.UPDATE_SEEK_BAR_ACTION);
                                    timePlayerIntent.putExtra(DURATION, mMediaPlayer.getDuration());
                                    timePlayerIntent.putExtra(CURRENT_TIME, mMediaPlayer.getCurrentPosition());
                                    sendBroadcast(timePlayerIntent);
                                }

                                @Override
                                public void onFinish() {
                                }
                            };
                            mCountDownTimer.start();
                        } else {
                            mMediaPlayer.pause();
                        }
                    }
                    break;
                }
                case ServicesBroadcastActivity.SEEK_BAR_PROGRESS_ACTION:
                    if (intent.getExtras() != null) {
                        mMediaPlayer.seekTo((intent.getExtras()).getInt(ServicesBroadcastActivity.SEEK_BAR_PROGRESS));
                    }
                    break;
                case ServicesBroadcastActivity.PLAY_OR_PAUSE_NOTIFICATION_ACTION:
                    if (intent.getExtras() != null) {
                        boolean isPlayNotification;
                        if ((intent.getExtras()).getBoolean(ServicesBroadcastActivity.IS_NOTIFICATION_PLAYING)) {
                            mMediaPlayer.pause();
                            isPlayNotification = true;
                        } else {
                            mMediaPlayer.start();
                            isPlayNotification = false;
                        }
                        Intent musicPauseIntent = new Intent(ServicesBroadcastActivity.NOTIFICATION_PLAY_OR_PAUSE_ACTION);
                        musicPauseIntent.putExtra(PLAY_OR_PAUSE_NOTIFICATION, isPlayNotification);
                        sendBroadcast(musicPauseIntent);
                    }
                    break;
                case ServicesBroadcastActivity.CLOSE_NOTIFICATION_ACTION:
                    Intent notificationCloseIntent = new Intent(ServicesBroadcastActivity.CLOSE_NOTIFICATION_ACTION);
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
        mMediaPlayer.release();
        super.onDestroy();
    }
}

