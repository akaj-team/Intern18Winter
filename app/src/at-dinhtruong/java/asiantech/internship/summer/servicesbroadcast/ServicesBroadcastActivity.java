package asiantech.internship.summer.servicesbroadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class ServicesBroadcastActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    private static final String CHANNEL_ID = "TEST_CHANNEL";
    public static final String ACTION_NOTIFICATION_BUTTON_CLICK = "ACTION_CLICK";
    public static final String EXTRA_BUTTON_CLICKED = "EXTRA_CLICK";
    private MediaPlayer mMediaPlayer;
    private int mediaFileLengthInMilliseconds;
    private final Handler mHandler = new Handler();
    private TextView mTvFinalTime;
    private TextView mTvTimeElapsed;
    private ImageView mImgImage;
    private SeekBar mSeekBarProgress;
    private ImageView mImgPlay;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_broadcast);
        initView();
        createNotificationChannel();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mSeekBarProgress = findViewById(R.id.sbTimeElapsed);
        mImgPlay = findViewById(R.id.btnPlay);
        mTvFinalTime = findViewById(R.id.tvFinalTime);
        mTvTimeElapsed = findViewById(R.id.tvTimeElapsed);
        mImgImage = findViewById(R.id.imgImage);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.image_rotate);
        mSeekBarProgress.setMax(99);
        mSeekBarProgress.setOnTouchListener(this);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaFileLengthInMilliseconds = mMediaPlayer.getDuration();
        mImgPlay.setOnClickListener(this);
    }

    private void onUpdateSeekBarProgress() {
        mSeekBarProgress.setProgress((int) (((float) mMediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
        int currentPosition = mMediaPlayer.getCurrentPosition() / 1000;
        int getDuration = mMediaPlayer.getDuration() / 1000;
        mTvTimeElapsed.setText(convertInttoString(currentPosition));
        mTvFinalTime.setText(convertInttoString(getDuration));
        if (mMediaPlayer.isPlaying()) {
            Runnable notification = this::onUpdateSeekBarProgress;
            mHandler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onClick(View view) {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
            mImgImage.startAnimation(mAnimation);
        } else {
            mMediaPlayer.pause();
            mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
            mImgImage.clearAnimation();
        }
        showNotification();
        onUpdateSeekBarProgress();
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

    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(ACTION_NOTIFICATION_BUTTON_CLICK);
        intent.putExtra(EXTRA_BUTTON_CLICKED, id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }

    public void showNotification() {
        Intent notificationIntent = new Intent(this, ServicesBroadcastActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.custom_notification);
        notificationLayout.setOnClickPendingIntent(R.id.btnPlayOrPause, onButtonNotificationClick(R.id.btnPlayOrPause));
        notificationLayout.setOnClickPendingIntent(R.id.btnClose, onButtonNotificationClick(R.id.btnClose));
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_play_arrow_white_36dp)
                .setCustomContentView(notificationLayout)
                .setContentIntent(pendingNotificationIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.sbTimeElapsed) {
            if (mMediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) view;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mMediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        mSeekBarProgress.setSecondaryProgress(i);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * seekBar.getProgress();
        mMediaPlayer.seekTo(playPositionInMillisecconds);
    }

    private String convertInttoString(int timeTotal) {
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
