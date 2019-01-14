package asiantech.internship.summer.servicesbroadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import asiantech.internship.summer.R;

public class PlayMusicService extends Service {
    public static final String PLAY_OR_PAUSE_NOTIFICATION = "PLAY OR PAUSE";
    public static final String DURATION = "DURATION";
    public static final String CURRENT_TIME = "CURRENT TIME";
    public static final String START_ACTIVITY = "START ACTIVITY";
    private static final String CHANNEL_ID = "TEST_CHANNEL";

    private MediaPlayer mMediaPlayer;
    private CountDownTimer mCountDownTimer;

    public PlayMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        mMediaPlayer = MediaPlayer.create(this, R.raw.music);
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
                                    mCountDownTimer.onFinish();
                                }
                            };
                            mCountDownTimer.start();
                        } else {
                            mMediaPlayer.pause();
                        }
                        showNotification();
                    }
                    break;
                }
                case ServicesBroadcastActivity.SEEK_BAR_PROGRESS_ACTION:
                    if (intent.getExtras() != null) {
                        mMediaPlayer.seekTo((intent.getExtras()).getInt(ServicesBroadcastActivity.SEEK_BAR_PROGRESS));
                        showNotification();
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
                        showNotification();
                    }
                    break;
                case ServicesBroadcastActivity.CLOSE_NOTIFICATION_ACTION:
                    if (mMediaPlayer.isPlaying()) {
                        Toast.makeText(this, "Please pause before close", Toast.LENGTH_LONG).show();
                    } else {
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        if (notificationManager != null) {
                            notificationManager.cancelAll();
                        }
                        stopForeground(true);
                        stopSelf();
                    }
                case ServicesBroadcastActivity.START_SERVICE_ACTION:
                    Intent activityCreateIntent = new Intent(ServicesBroadcastActivity.START_SERVICE_ACTION);
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
        super.onDestroy();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelName);
            String description = getString(R.string.channelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void showNotification() {
        boolean mIsPlay = mMediaPlayer.isPlaying();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.US);
        int currentTime = mMediaPlayer.getCurrentPosition();
        int duration = mMediaPlayer.getDuration();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvCurrentTime, String.valueOf(simpleDateFormat.format(mMediaPlayer.getCurrentPosition())));
        if (mIsPlay) {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_pause_black_36dp);
        } else {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_play_arrow_black_36dp);
        }
        if (duration - currentTime <= 0) {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_play_arrow_black_36dp);
            remoteViews.setTextViewText(R.id.tvCurrentTime, getString(R.string.timeMusicPlayer));
        }
        Intent playIntent = new Intent(this, PlayMusicService.class);
        playIntent.setAction(ServicesBroadcastActivity.PLAY_OR_PAUSE_NOTIFICATION_ACTION);
        playIntent.putExtra(ServicesBroadcastActivity.IS_NOTIFICATION_PLAYING, mIsPlay);
        PendingIntent pendingPauseOrPlayIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btnPlayOrPause, pendingPauseOrPlayIntent);

        Intent closeIntent = new Intent(this, PlayMusicService.class);
        closeIntent.setAction(ServicesBroadcastActivity.CLOSE_NOTIFICATION_ACTION);
        PendingIntent pendingCloseIntent = PendingIntent.getService(this, 0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btnClose, pendingCloseIntent);

        Intent openActivityIntent = new Intent(this, ServicesBroadcastActivity.class);
        openActivityIntent.setAction(Intent.ACTION_MAIN);
        openActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        openActivityIntent.setComponent((Objects.requireNonNull(getPackageManager().getLaunchIntentForPackage(getPackageName()))).getComponent());
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_play_arrow_white_36dp)
                .setCustomContentView(remoteViews)
                .setContentIntent(PendingIntent.getActivity(this, 0, openActivityIntent, 0))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

}

