package asiantech.internship.summer.servicesbroadcastreceiver;

import android.app.Notification;
import android.app.NotificationChannel;
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
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String ACTION_PASS_PROGRESS_SEEK_BAR = "Action pass progress";
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
    private TextView mTvPlayOrPause;
    private TextView mTvTotalTime;
    private TextView mTvMusicName;
    private ImageView mImgAction;
    private Animation mRotateAnimation;

    private boolean mIsPlay = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case ACTION_UPDATE_SEEK_BAR:
                        updateUI(intent);
                        break;
                    case ACTION_NOTIFICATION_PAUSE:
                        mImgAction.setBackgroundResource(R.drawable.ic_pause_music);
                        mRotateAnimation.cancel();
                        mIsPlay = !Objects.requireNonNull(intent.getExtras()).getBoolean(getString(R.string.keyPauseNotification));
                        showNotification();
                        break;
                    case ACTION_NOTIFICATION_PLAY:
                        mImgAction.setBackgroundResource(R.drawable.ic_play_music);
                        mImgCD.startAnimation(mRotateAnimation);
                        mIsPlay = !Objects.requireNonNull(intent.getExtras()).getBoolean(getString(R.string.keyPlayNotification));
                        showNotification();
                        break;
                    case ACTION_CLOSE_NOTIFICATION:
                        boolean isClose = Objects.requireNonNull(intent.getExtras()).getBoolean(getString(R.string.keyCloseNotification));
                        if (isClose) {
                            Toast.makeText(getApplicationContext(), R.string.pauseMusicBeforeClosing, Toast.LENGTH_SHORT).show();
                        } else {
                            mImgAction.setBackgroundResource(R.drawable.ic_pause_music);
                            mRotateAnimation.cancel();
                            Intent stopServiceIntent = new Intent(getApplicationContext(), MusicService.class);
                            stopService(stopServiceIntent);
                        }
                        break;
                    case MusicActivity.ACTION_CREATE_ACTIVITY:
                        mIsPlay = Objects.requireNonNull(intent.getExtras()).getBoolean(getString(R.string.keyStartActivity));
                        if (mIsPlay) {
                            mImgAction.setBackgroundResource(R.drawable.ic_play_music);
                            mImgCD.startAnimation(mRotateAnimation);
                        } else {
                            mImgAction.setBackgroundResource(R.drawable.ic_pause_music);
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initView();
        addListener();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_SEEK_BAR);
        intentFilter.addAction(ACTION_NOTIFICATION_PLAY);
        intentFilter.addAction(ACTION_NOTIFICATION_PAUSE);
        intentFilter.addAction(ACTION_CLOSE_NOTIFICATION);
        intentFilter.addAction(ACTION_CREATE_ACTIVITY);
        this.registerReceiver(mReceiver, intentFilter);

        Intent startActivityIntent = new Intent(this, MusicService.class);
        startActivityIntent.setAction(ACTION_CREATE_ACTIVITY);
        startService(startActivityIntent);
    }

    private void initView() {
        mImgCD = findViewById(R.id.img_cd);
        mSeekBar = findViewById(R.id.seekBarMusicTime);
        mTvCurrentTime = findViewById(R.id.tvCurrentTime);
        mTvPlayOrPause = findViewById(R.id.tvPlayOrPause);
        mTvTotalTime = findViewById(R.id.tvTotalTime);
        mTvMusicName = findViewById(R.id.tvMusicName);
        mImgAction = findViewById(R.id.imgAction);

        mTvMusicName.setText(R.string.musicName);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar);
        mImgCD.setImageBitmap(bitmap);
        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_image);
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
                    mImgAction.setBackgroundResource(R.drawable.ic_pause_music);
                    mTvPlayOrPause.setText(R.string.musicPause);
                    mTvPlayOrPause.setTextColor(getResources().getColor(R.color.colorAccent));
                    mRotateAnimation.cancel();

                    Intent intentPause = new Intent(this, MusicService.class);
                    intentPause.setAction(ACTION_PAUSE);
                    startService(intentPause);
                } else {
                    mImgAction.setBackgroundResource(R.drawable.ic_play_music);
                    mTvPlayOrPause.setText(R.string.musicPlaying);
                    mTvPlayOrPause.setTextColor(getResources().getColor(R.color.colorGreen));
                    mImgCD.startAnimation(mRotateAnimation);

                    Intent intentPlay = new Intent(this, MusicService.class);
                    intentPlay.setAction(ACTION_PLAY);
                    startService(intentPlay);
                }
                mIsPlay = !mIsPlay;
                showNotification();
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
        Intent dataIntent = new Intent(this, MusicService.class);
        dataIntent.putExtra(KEY_PASS_PROGRESS, seekBar.getProgress());
        dataIntent.setAction(ACTION_PASS_PROGRESS_SEEK_BAR);
        startService(dataIntent);
    }

    private void updateUI(Intent intent) {
        int durationTime = Objects.requireNonNull(intent.getExtras()).getInt(getString(R.string.keyDurationTime));
        int currentTime = intent.getExtras().getInt(getString(R.string.keyCurrentTime));
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
    }

    public void showNotification() {
        String channelId = getString(R.string.channelId);
        Intent intent = new Intent(getApplicationContext(), MusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_play_arrow_white_36dp);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ic_play_arrow_black_36dp));
        mBuilder.setContentTitle(getString(R.string.playingMusic));
        mBuilder.setContentText(getString(R.string.musicName));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId,
                    " ",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        if (notificationManager != null) {
            notificationManager.notify(0, mBuilder.build());
        }
    }
}
