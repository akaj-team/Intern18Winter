package asiantech.internship.summer.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Song;
import de.hdodenhof.circleimageview.CircleImageView;

public class MusicService extends Service implements View.OnClickListener {
    public static final int ID_NOTI = 1;
    private MediaPlayer mMediaPlayer;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private WeakReference<ImageButton> mImgBtnPrev;
    private WeakReference<ImageButton> mImgBtnPlay;
    private WeakReference<ImageButton> mImgBtnNext;
    private WeakReference<CircleImageView> mImgDisk;
    private WeakReference<TextView> mTvTotalTime;
    private WeakReference<SeekBar> mSeekBarPlay;
    private WeakReference<TextView> mTvTitleSong;
    private WeakReference<TextView> mTvState;
    private WeakReference<TextView> mTvCurrentTime;
    private RotateAnimation mRotateAnimation;
    private RemoteViews mRemoteViews;
    private Handler mHandler;
    private Runnable mRunnable;
    private List<Song> mListSong = new ArrayList<>();
    private int mPosition = 0;
    private boolean mIsShowNotification = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (Objects.equals(intent.getAction(), getResources().getString(R.string.playAction))) {
                if (mMediaPlayer.isPlaying()) {
                    if (mImgBtnPlay != null) {
                        mImgBtnPlay.get().setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                        mImgDisk.get().clearAnimation();
                    }
                    mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_play_arrow_black_24dp);
                    mMediaPlayer.pause();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                } else {
                    if (mImgBtnPlay != null) {
                        mImgBtnPlay.get().setImageResource(R.drawable.ic_pause_black_24dp);
                        mImgDisk.get().startAnimation(mRotateAnimation);
                    }
                    mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_pause_black_24dp);
                    mMediaPlayer.start();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
            }
            if (Objects.equals(intent.getAction(), getResources().getString(R.string.closeAction))) {
                if (!mMediaPlayer.isPlaying()) {
                    mIsShowNotification = false;
                    if (mNotificationManager != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            mNotificationManager.deleteNotificationChannel(getResources().getString(R.string.channel));
                        }
                        stopForeground(true);
                        mNotificationManager.cancel(ID_NOTI);
                    }
                }
            }
            if (Objects.equals(intent.getAction(), getResources().getString(R.string.move))) {
                initUI();
                mPosition = intent.getIntExtra(getResources().getString(R.string.position), 0);
                saveIntPreference(mPosition, getResources().getString(R.string.position));
                mListSong = Song.listSong();
                setCurrentSong();
                mImgBtnPlay.get().setOnClickListener(this);
                mImgBtnNext.get().setOnClickListener(this);
                mImgBtnPrev.get().setOnClickListener(this);
                seekBarChangeListener();
                initRotateAnimationDisk();
                saveIntPreference(mPosition, getResources().getString(R.string.position));
            }
        } else {
            SharedPreferences pref = getApplicationContext().getSharedPreferences(getResources().getString(R.string.preference), 0);
            mPosition = pref.getInt(getResources().getString(R.string.position), 0);
            mListSong = Song.listSong();
            mMediaPlayer = MediaPlayer.create(this, mListSong.get(mPosition).getFileName());
            mMediaPlayer.seekTo(pref.getInt(getResources().getString(R.string.current), 0));
            mMediaPlayer.start();
            initNotification();
            mNotificationManager.notify(ID_NOTI, mBuilder.build());
            mIsShowNotification = true;
            updateCurrentTime();
        }
        return START_STICKY;
    }

    private void setCurrentSong() {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mListSong.get(mPosition).getFileName());
        if (mTvTitleSong != null) {
            mTvTitleSong.get().setText(mListSong.get(mPosition).getTitle());
            mImgDisk.get().setImageResource(mListSong.get(mPosition).getCdImage());
        }
    }

    private void initUI() {
        mImgBtnPlay = new WeakReference<>(ListenMusicActivity.sImgBtnPlay);
        mImgBtnNext = new WeakReference<>(ListenMusicActivity.sImgBtnNext);
        mImgBtnPrev = new WeakReference<>(ListenMusicActivity.sImgBtnPrev);
        mImgDisk = new WeakReference<>(ListenMusicActivity.sImgDisk);
        mTvTotalTime = new WeakReference<>(ListenMusicActivity.sTvTotalTime);
        mSeekBarPlay = new WeakReference<>(ListenMusicActivity.sSeekBarPlay);
        mTvTitleSong = new WeakReference<>(ListenMusicActivity.sTvTitleSong);
        mTvState = new WeakReference<>(ListenMusicActivity.sTvState);
        mTvCurrentTime = new WeakReference<>(ListenMusicActivity.sTvCurrentTime);
    }

    private void seekBarChangeListener() {
        mSeekBarPlay.get().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
                saveIntPreference(seekBar.getProgress(), getResources().getString(R.string.current));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mImgDisk.get().clearAnimation();
                    mTvState.get().setText(R.string.pause);
                    mImgBtnPlay.get().setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    if (mNotificationManager != null) {
                        mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_play_arrow_black_24dp);
                        mNotificationManager.notify(ID_NOTI, mBuilder.build());
                    }
                } else {
                    mIsShowNotification = true;
                    mMediaPlayer.start();
                    mTvState.get().setText(R.string.play);
                    mImgDisk.get().startAnimation(mRotateAnimation);
                    mImgBtnPlay.get().setImageResource(R.drawable.ic_pause_black_24dp);
                    initNotification();
                    if (mNotificationManager != null) {
                        mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_pause_black_24dp);
                        mNotificationManager.notify(ID_NOTI, mBuilder.build());
                    }
                }
                setTotalTime();
                updateCurrentTime();
                break;
            case R.id.imgBtnNext:
                mPosition++;
                if (mPosition > mListSong.size() - 1) {
                    mPosition = 0;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    setCurrentSong();
                    mMediaPlayer.start();
                } else {
                    setCurrentSong();
                }
                saveIntPreference(mPosition, getResources().getString(R.string.position));
                setTotalTime();
                updateCurrentTime();
                if (mNotificationManager != null) {
                    setRemoteViews();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                break;
            case R.id.imgBtnPrev:
                mPosition--;
                if (mPosition < 0) {
                    mPosition = mListSong.size() - 1;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    setCurrentSong();
                    mMediaPlayer.start();
                } else {
                    setCurrentSong();
                }
                saveIntPreference(mPosition, getResources().getString(R.string.position));
                setTotalTime();
                updateCurrentTime();
                if (mNotificationManager != null) {
                    setRemoteViews();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                break;
        }
    }

    private void saveIntPreference(int i, String name) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getResources().getString(R.string.preference), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, i);
        editor.apply();
    }

    private void setTotalTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFomart = new SimpleDateFormat("mm:ss");
        mTvTotalTime.get().setText(timeFomart.format(mMediaPlayer.getDuration()));
        mSeekBarPlay.get().setMax(mMediaPlayer.getDuration());
    }

    private void initRotateAnimationDisk() {
        mRotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(5000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    private void updateCurrentTime() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                if (mTvCurrentTime != null) {
                    mTvCurrentTime.get().setText(timeFormat.format(mMediaPlayer.getCurrentPosition()));
                }
                if (mNotificationManager != null && mIsShowNotification) {
                    mRemoteViews.setTextViewText(R.id.tvCurrentTime, timeFormat.format(mMediaPlayer.getCurrentPosition()));
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                if (mSeekBarPlay != null) {
                    mSeekBarPlay.get().setProgress(mMediaPlayer.getCurrentPosition());
                }
                saveIntPreference(mMediaPlayer.getCurrentPosition(), getResources().getString(R.string.current));
                mMediaPlayer.setOnCompletionListener(mp -> {
                    mPosition++;
                    if (mPosition > mListSong.size() - 1) {
                        mPosition = 0;
                    }
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        setCurrentSong();
                        mMediaPlayer.start();
                    } else {
                        setCurrentSong();
                    }
                    if (mNotificationManager != null) {
                        setRemoteViews();
                    }
                    saveIntPreference(mPosition, getResources().getString(R.string.position));
                    setTotalTime();
                    updateCurrentTime();
                });
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(mRunnable, 100);
    }

    private void setRemoteViews() {
        mRemoteViews.setImageViewResource(R.id.imgSinger, mListSong.get(mPosition).getCdImage());
        mRemoteViews.setTextViewText(R.id.tvTitleSong, mListSong.get(mPosition).getTitle());
    }

    private PendingIntent setOnClick(String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void initNotification() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_music);
        setRemoteViews();
        mRemoteViews.setOnClickPendingIntent(R.id.imgPlay, setOnClick(getResources().getString(R.string.playAction)));
        mRemoteViews.setOnClickPendingIntent(R.id.imgClose, setOnClick(getResources().getString(R.string.closeAction)));
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ListenMusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.channel));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setCustomBigContentView(mRemoteViews)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getResources().getString(R.string.channel), getResources().getString(R.string.channel_name), importance);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.pause();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        if (mNotificationManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mNotificationManager.deleteNotificationChannel(getResources().getString(R.string.channel));
            }
            mNotificationManager.cancel(ID_NOTI);
        }
        super.onDestroy();
    }
}
