package asiantech.internship.summer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Objects;
import asiantech.internship.summer.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static final String ACTION_SEEK_BAR = "Action seek bar";
    public static final String ACTION_PLAY = "Action play";
    public static final String ACTION_PAUSE = "Action pause";
    public static final String ACTION_FOCUS_IMAGE_NOTIFICATION = "Action focus image";
    public static final String ACTION_CLOSE_NOTIFICATION = "Action close notification";
    public static final String ACTION_CREATE_ACTIVITY = "Action create activity";
    public static final String ACTION_NOTIFICATION_PAUSE = "Music notification pause";
    public static final String ACTION_NOTIFICATION_PLAY = "Music notification play";
    public static final String ACTION_UPDATE_SEEK_BAR = "Action update seek bar";
    public static final String KEY_PASS_PROGRESS = "Pass progress seek bar";
    public static final String KEY_IS_PLAYING = "Is play";
    private ImageView mImgCD;
    private SeekBar mSeekBar;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private TextView mTvMusicName;
    private ImageView mImgAction;
    private Animation mRotateAnimation;
    private NotificationManager mNotificationManager;
    private boolean mIsPlay = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case ACTION_UPDATE_SEEK_BAR:
                        updateTimeSong(intent);
                        break;
                    case ACTION_NOTIFICATION_PAUSE:
                        mImgAction.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                        mImgCD.clearAnimation();
                        mIsPlay = !Objects.requireNonNull(intent.getExtras()).getBoolean(PlayMusicService.KEY_PAUSE_NOTIFICATION);
                        createNotification();
                        break;
                    case ACTION_NOTIFICATION_PLAY:
                        mImgAction.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
                        mImgCD.startAnimation(mRotateAnimation);
                        mIsPlay = !Objects.requireNonNull(intent.getExtras()).getBoolean(PlayMusicService.KEY_PLAY_NOTIFICATION);
                        createNotification();
                        break;
                    case ACTION_CLOSE_NOTIFICATION:
                        boolean isCloseNotification = Objects.requireNonNull(intent.getExtras()).getBoolean(PlayMusicService.KEY_CLOSE_NOTIFICATION);
                        if (!isCloseNotification) {
                            mImgAction.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                            mRotateAnimation.cancel();
                            Intent stopServiceIntent = new Intent(getApplicationContext(), PlayMusicService.class);
                            stopService(stopServiceIntent);
                            mNotificationManager.cancelAll();
                        }
                        break;
                    case ServiceActivity.ACTION_CREATE_ACTIVITY:
                        mIsPlay = Objects.requireNonNull(intent.getExtras()).getBoolean(PlayMusicService.KEY_START_ACTIVITY);
                        if (mIsPlay) {
                            mImgAction.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
                            mImgCD.startAnimation(mRotateAnimation);
                        } else {
                            mImgAction.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mapping();
        addListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_SEEK_BAR);
        intentFilter.addAction(ACTION_NOTIFICATION_PLAY);
        intentFilter.addAction(ACTION_NOTIFICATION_PAUSE);
        intentFilter.addAction(ACTION_CLOSE_NOTIFICATION);
        intentFilter.addAction(ACTION_CREATE_ACTIVITY);
        this.registerReceiver(mReceiver, intentFilter);
        Intent startActivityIntent = new Intent(this, PlayMusicService.class);
        startActivityIntent.setAction(ACTION_CREATE_ACTIVITY);
        ContextCompat.startForegroundService(this, startActivityIntent);
    }

    private void mapping() {
        mImgCD = findViewById(R.id.img_cd);
        mSeekBar = findViewById(R.id.seekBarMusicTime);
        mTvCurrentTime = findViewById(R.id.tvCurrentTime);
        mTvTotalTime = findViewById(R.id.tvTotalTime);
        mTvMusicName = findViewById(R.id.tvNameOfMusic);
        mImgAction = findViewById(R.id.imgAction);
        mTvMusicName.setText(R.string.name_music);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc);
        mImgCD.setImageBitmap(bitmap);
        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
    }

    private void addListener() {
        mImgAction.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAction:
                if (mIsPlay) {
                    mImgAction.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                    mImgCD.clearAnimation();
                    Intent intentPause = new Intent(this, PlayMusicService.class);
                    intentPause.setAction(ACTION_PAUSE);
                    startService(intentPause);
                } else {
                    mImgAction.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    mImgCD.startAnimation(mRotateAnimation);
                    Intent intentPlay = new Intent(this, PlayMusicService.class);
                    intentPlay.setAction(ACTION_PLAY);
                    startService(intentPlay);
                }
                mIsPlay = !mIsPlay;
                createNotification();
                break;
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
        mSeekBar.setProgress(seekBar.getProgress());
        Intent dataIntent = new Intent(this, PlayMusicService.class);
        dataIntent.putExtra(KEY_PASS_PROGRESS, seekBar.getProgress());
        dataIntent.setAction(ACTION_SEEK_BAR);
        startService(dataIntent);
    }

    private void updateTimeSong(Intent intent) {
        int durationTime = Objects.requireNonNull(intent.getExtras()).getInt(PlayMusicService.KEY_DURATION_TIME);
        int currentTime = intent.getExtras().getInt(PlayMusicService.KEY_CURRENT_TIME);
        mSeekBar.setMax(durationTime);
        mSeekBar.setProgress(currentTime);
        int minuteDuration = durationTime / 60000;
        int secondDuration = (durationTime % 60000) / 1000;
        int minuteCurrent = currentTime / 60000;
        int secondCurrent = (currentTime % 60000) / 1000;
        if (secondCurrent < 10) {
            mTvTotalTime.setText(String.valueOf("0" + minuteDuration + ":" + secondDuration));
            mTvCurrentTime.setText(String.valueOf("0" + minuteCurrent + ":" + "0" + secondCurrent));
        } else {
            mTvTotalTime.setText(String.valueOf("0" + minuteDuration + ":" + secondDuration));
            mTvCurrentTime.setText(String.valueOf("0" + minuteCurrent + ":" + secondCurrent));
        }
        createNotification();
    }

    private void createNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification);
        remoteViews.setImageViewResource(R.id.imgLogoNotification, R.drawable.img_music);
        remoteViews.setTextViewText(R.id.tvNameMusicNotification, mTvMusicName.getText());
        remoteViews.setTextViewText(R.id.tvTimeNotification, mTvCurrentTime.getText());
        if (mIsPlay) {
            remoteViews.setImageViewResource(R.id.imgActionNotification, R.drawable.ic_pause_circle_filled_black_24dp);
        } else {
            remoteViews.setImageViewResource(R.id.imgActionNotification, R.drawable.ic_play_circle_filled_black_24dp);
        }
        Intent pauseOrPlayNotificationIntent = new Intent(getApplicationContext(), PlayMusicService.class);
        pauseOrPlayNotificationIntent.setAction(ACTION_FOCUS_IMAGE_NOTIFICATION);
        pauseOrPlayNotificationIntent.putExtra(KEY_IS_PLAYING, mIsPlay);
        PendingIntent pendingPauseOrPlayIntent = PendingIntent.getService(this, 0, pauseOrPlayNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.imgActionNotification, pendingPauseOrPlayIntent);
        Intent closeNotificationIntent = new Intent(this, PlayMusicService.class);
        closeNotificationIntent.setAction(ACTION_CLOSE_NOTIFICATION);
        PendingIntent pendingCloseIntent = PendingIntent.getService(this, 0, closeNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.imgCloseNotification, pendingCloseIntent);
        Intent openActivityIntent = new Intent(this, PlayMusicService.class);
        openActivityIntent.setAction(Intent.ACTION_MAIN);
        openActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        openActivityIntent.setComponent(Objects.requireNonNull(getPackageManager().getLaunchIntentForPackage(getPackageName())).getComponent());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info_outline_black_24dp)
                .setContentIntent(PendingIntent.getActivity(this, 0, openActivityIntent, 0))
                .setCustomBigContentView(remoteViews);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }
}
