package asiantech.internship.summer.servicesbroadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class ServicesBroadcastActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String CHANNEL_ID = "TEST_CHANNEL";
    public static final String SEEK_BAR_PROGRESS_ACTION = "Action pass progress";
    public static final String PLAY_ACTION = "Action play";
    public static final String PAUSE_ACTION = "Action pause";
    public static final String FOCUS_IMAGE_NOTIFICATION_ACTION = "Action focus image";
    public static final String CLOSE_ACTION = "Action close notification";
    public static final String CREATE_ACTIVITY_ACTION = "Action create activity";
    public static final String ACTION_NOTIFICATION_PAUSE = "Music notification pause";
    public static final String ACTION_NOTIFICATION_PLAY = "Music notification play";
    public static final String ACTION_UPDATE_SEEK_BAR = "Action update seek bar";
    public static final String KEY_PASS_PROGRESS = "Pass progress seek bar";
    public static final String KEY_IS_PLAYING = "Is play";
    private TextView mTvFinalTime;
    private TextView mTvTimeElapsed;
    private ImageView mImgImage;
    private SeekBar mSeekBarProgress;
    private ImageView mImgPlay;
    private Animation mAnimation;
    private NotificationManager mNotificationManager;

    private boolean mIsPlay = false;
    private int mDurationTime = 0;
    private int mCurrentTime = 0;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case ACTION_UPDATE_SEEK_BAR:
                        onUpdateSeekBarProgress(intent);
                        break;
                    case ACTION_NOTIFICATION_PAUSE:
                        mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                        mImgImage.clearAnimation();
                        if (intent.getExtras() != null) {
                            mIsPlay = !intent.getExtras().getBoolean(PlayMusicService.PAUSE_NOTIFICATION);
                        }
                        showNotification();
                        break;
                    case ACTION_NOTIFICATION_PLAY:
                        mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
                        mImgImage.startAnimation(mAnimation);
                        if (intent.getExtras() != null) {
                            mIsPlay = !(intent.getExtras()).getBoolean(PlayMusicService.PLAY_NOTIFICATION);
                        }
                        showNotification();
                        break;
                    case CLOSE_ACTION:
                        boolean isClose = false;
                        if (intent.getExtras() != null) {
                            isClose = intent.getExtras().getBoolean(PlayMusicService.CLOSE_NOTIFICATION);
                        }
                        if (isClose) {
                            Toast.makeText(getApplicationContext(), "please close before close app", Toast.LENGTH_SHORT).show();
                        } else {
                            mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                            mImgImage.clearAnimation();
                            Intent stopServiceIntent = new Intent(getApplicationContext(), PlayMusicService.class);
                            stopService(stopServiceIntent);
                            mNotificationManager.cancelAll();
                        }
                        break;
                    case ServicesBroadcastActivity.CREATE_ACTIVITY_ACTION:
                        if (intent.getExtras() != null) {
                            mIsPlay = (intent.getExtras()).getBoolean(PlayMusicService.START_ACTIVITY);
                        }
                        if (mIsPlay) {
                            mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
                            mImgImage.startAnimation(mAnimation);
                        } else {
                            mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_broadcast);
        initView();
        createNotificationChannel();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_SEEK_BAR);
        intentFilter.addAction(ACTION_NOTIFICATION_PLAY);
        intentFilter.addAction(ACTION_NOTIFICATION_PAUSE);
        intentFilter.addAction(CLOSE_ACTION);
        intentFilter.addAction(CREATE_ACTIVITY_ACTION);
        this.registerReceiver(mReceiver, intentFilter);
        Intent startActivityIntent = new Intent(this, PlayMusicService.class);
        startActivityIntent.setAction(CREATE_ACTIVITY_ACTION);
        startService(startActivityIntent);
    }

    private void initView() {
        mSeekBarProgress = findViewById(R.id.sbTimeElapsed);
        mImgPlay = findViewById(R.id.btnPlay);
        mTvFinalTime = findViewById(R.id.tvFinalTime);
        mTvTimeElapsed = findViewById(R.id.tvTimeElapsed);
        mImgImage = findViewById(R.id.imgImage);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.image_rotate);
        mSeekBarProgress.setOnSeekBarChangeListener(this);
        mImgPlay.setOnClickListener(this);
    }

    private void onUpdateSeekBarProgress(Intent intent) {
        if (intent.getExtras() != null) {
            mDurationTime = (intent.getExtras()).getInt(PlayMusicService.DURATION_TIME);
            mCurrentTime = intent.getExtras().getInt(PlayMusicService.CURRENT_TIME);
        }
        mSeekBarProgress.setMax(mDurationTime);
        mSeekBarProgress.setProgress(mCurrentTime);
        mTvFinalTime.setText(convertTime(mDurationTime / 1000));
        mTvTimeElapsed.setText(convertTime(mCurrentTime / 1000));
        if (mDurationTime - mCurrentTime <= 0) {
            mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
            mTvFinalTime.setText(convertTime(mDurationTime / 1000));
            mTvTimeElapsed.setText(getString(R.string.timeMusicPlayer));
            mImgImage.clearAnimation();
        }
        showNotification();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (!mIsPlay) {
                    mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
                    mImgImage.startAnimation(mAnimation);
                    Intent intentPlay = new Intent(this, PlayMusicService.class);
                    intentPlay.setAction(PLAY_ACTION);
                    startService(intentPlay);
                } else {
                    mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                    mImgImage.clearAnimation();
                    Intent intentPause = new Intent(this, PlayMusicService.class);
                    intentPause.setAction(PAUSE_ACTION);
                    startService(intentPause);
                }
                mIsPlay = !mIsPlay;
                showNotification();
        }
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
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvCurrentTime, mTvTimeElapsed.getText());
        if (mIsPlay) {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_pause_black_36dp);
        } else {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_play_arrow_black_36dp);
        }
        if (mDurationTime - mCurrentTime <= 0) {
            remoteViews.setImageViewResource(R.id.btnPlayOrPause, R.drawable.ic_play_arrow_black_36dp);
            remoteViews.setTextViewText(R.id.tvCurrentTime, getString(R.string.timeMusicPlayer));
        }
        Intent playIntent = new Intent(getApplicationContext(), PlayMusicService.class);
        playIntent.setAction(FOCUS_IMAGE_NOTIFICATION_ACTION);
        playIntent.putExtra(KEY_IS_PLAYING, mIsPlay);
        PendingIntent pendingPauseOrPlayIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btnPlayOrPause, pendingPauseOrPlayIntent);

        Intent closeIntent = new Intent(this, PlayMusicService.class);
        closeIntent.setAction(CLOSE_ACTION);
        PendingIntent pendingCloseIntent = PendingIntent.getService(this, 0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btnClose, pendingCloseIntent);

        Intent openActivityIntent = new Intent(this, ServicesBroadcastActivity.class);
        openActivityIntent.setAction(Intent.ACTION_MAIN);
        openActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        if (getPackageManager().getLaunchIntentForPackage(getPackageName()) != null) {
            openActivityIntent.setComponent((getPackageManager().getLaunchIntentForPackage(getPackageName())).getComponent());
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_play_arrow_white_36dp)
                .setCustomContentView(remoteViews)
                .setContentIntent(PendingIntent.getActivity(this, 0, openActivityIntent, 0))
                .build();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, notification);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mSeekBarProgress.setProgress(seekBar.getProgress());
        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra(KEY_PASS_PROGRESS, seekBar.getProgress());
        intent.setAction(SEEK_BAR_PROGRESS_ACTION);
        startService(intent);
    }

    private String convertTime(int timeTotal) {
        int min = timeTotal / 60;
        int second = timeTotal - min * 60;
        String currentPosition;
        if (timeTotal < 10) {
            currentPosition = "00:0" + timeTotal;
        } else if (timeTotal < 60) {
            currentPosition = "00:" + timeTotal;
        } else {
            if (second < 10) {
                currentPosition = "0" + min + ":0" + second;
            } else {
                currentPosition = "0" + min + ":" + second;
            }
        }
        return currentPosition;
    }
}
