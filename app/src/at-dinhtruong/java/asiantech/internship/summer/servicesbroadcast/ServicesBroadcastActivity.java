package asiantech.internship.summer.servicesbroadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class ServicesBroadcastActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBarProgress;
    private int mediaFileLengthInMilliseconds;
    private Button mBtnPlay;
    private final Handler mHandler = new Handler();
    private TextView mTvFinalTime;
    private TextView mTvTimeElapsed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_broadcast);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mSeekBarProgress = findViewById(R.id.sbTimeElapsed);
        mBtnPlay = findViewById(R.id.btnPlay);
        mTvFinalTime = findViewById(R.id.tvFinalTime);
        mTvTimeElapsed = findViewById(R.id.tvTimeElapsed);

        mSeekBarProgress.setMax(99);
        mSeekBarProgress.setOnTouchListener(this);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaFileLengthInMilliseconds = mMediaPlayer.getDuration();

        mBtnPlay.setOnClickListener(this);
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
            mBtnPlay.setText(R.string.pause);
        } else {
            mMediaPlayer.pause();
            mBtnPlay.setText(R.string.play);
        }

        showNotification();
        onUpdateSeekBarProgress();
    }

    public void showNotification() {
        Intent intent = new Intent(getApplicationContext(), ServicesBroadcastActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_play_arrow_white_36dp);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ic_play_arrow_white_36dp));
        mBuilder.setContentTitle("Play Music");
        mBuilder.setContentText("Da da di da");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
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
        mBtnPlay.setText(R.string.play);
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

    private String convertInttoString(int num) {
        int sophut = num / 60;
        int sogiay = num - sophut * 60;
        String currentPositionString;
        if (num < 10) {
            currentPositionString = "00:0" + num;
        } else if (num < 60) {
            currentPositionString = "00:" + num;
        } else {
            if (sogiay < 10) {
                currentPositionString = "0" + sophut + ":0" + sogiay;
            } else {
                currentPositionString = "0" + sophut + ":" + sogiay;
            }
        }
        return currentPositionString;
    }
}
