package asiantech.internship.summer.servicesbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import asiantech.internship.summer.R;

public class ServicesBroadcastActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String SEEK_BAR_PROGRESS_ACTION = "ACTION SEEK BAR PROGRESS";
    public static final String PLAY_OR_PAUSE_ACTION = "ACTION PLAY OR PAUSE";
    public static final String PLAY_OR_PAUSE_NOTIFICATION_ACTION = "ACTION PLAY OR PAUSE NOTIFICATION";
    public static final String CLOSE_NOTIFICATION_ACTION = "ACTION CLOSE NOTIFICATION";
    public static final String START_SERVICE_ACTION = "ACTION CREATE ACTIVITY";
    public static final String NOTIFICATION_PLAY_OR_PAUSE_ACTION = "ACTION MUSIC NOTIFICATION";
    public static final String UPDATE_SEEK_BAR_ACTION = "ACTION UPDATE SEEKBAR";
    public static final String SEEK_BAR_PROGRESS = "SEEK BAR PROGRESS";
    public static final String IS_NOTIFICATION_PLAYING = "NOTIFICATION IS PLAYING";
    private TextView mTvFinalTime;
    private TextView mTvTimeElapsed;
    private ImageView mImgImage;
    private SeekBar mSeekBarProgress;
    private ImageView mImgPlay;
    private Animation mAnimation;

    private boolean mIsPlay = false;
    private int mDuration = 0;
    private int mCurrentTime = 0;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case UPDATE_SEEK_BAR_ACTION:
                        onUpdateSeekBarProgress(intent);
                        break;
                    case NOTIFICATION_PLAY_OR_PAUSE_ACTION:
                        if (intent.getExtras() != null) {
                            if (intent.getExtras().getBoolean(PlayMusicService.PLAY_OR_PAUSE_NOTIFICATION)) {
                                mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                                mImgImage.clearAnimation();
                            } else {
                                mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
                                mImgImage.startAnimation(mAnimation);
                            }
                            mIsPlay = !intent.getExtras().getBoolean(PlayMusicService.PLAY_OR_PAUSE_NOTIFICATION);
                        }
                        break;
                    case ServicesBroadcastActivity.START_SERVICE_ACTION:
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATE_SEEK_BAR_ACTION);
        intentFilter.addAction(NOTIFICATION_PLAY_OR_PAUSE_ACTION);
        intentFilter.addAction(CLOSE_NOTIFICATION_ACTION);
        intentFilter.addAction(START_SERVICE_ACTION);
        registerReceiver(mReceiver, intentFilter);
        Intent startActivityIntent = new Intent(this, PlayMusicService.class);
        startActivityIntent.setAction(START_SERVICE_ACTION);
        ContextCompat.startForegroundService(this, startActivityIntent);
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
            mDuration = (intent.getExtras()).getInt(PlayMusicService.DURATION);
            mCurrentTime = intent.getExtras().getInt(PlayMusicService.CURRENT_TIME);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.US);
        mSeekBarProgress.setMax(mDuration);
        mSeekBarProgress.setProgress(mCurrentTime);
        mTvFinalTime.setText(simpleDateFormat.format(mDuration));
        mTvTimeElapsed.setText(simpleDateFormat.format(mCurrentTime));
        if (mDuration - mCurrentTime <= 0) {
            mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
            mTvFinalTime.setText(simpleDateFormat.format(mDuration));
            mTvTimeElapsed.setText(getString(R.string.timeMusicPlayer));
            mImgImage.clearAnimation();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (!mIsPlay) {
                    mImgPlay.setImageResource(R.drawable.ic_pause_black_36dp);
                    mImgImage.startAnimation(mAnimation);
                } else {
                    mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                    mImgImage.clearAnimation();
                }
                Intent intentPlay = new Intent(this, PlayMusicService.class);
                intentPlay.putExtra(PLAY_OR_PAUSE_ACTION, mIsPlay);
                intentPlay.setAction(PLAY_OR_PAUSE_ACTION);
                startService(intentPlay);
                mIsPlay = !mIsPlay;
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
        intent.putExtra(SEEK_BAR_PROGRESS, seekBar.getProgress());
        intent.setAction(SEEK_BAR_PROGRESS_ACTION);
        startService(intent);
    }
}
