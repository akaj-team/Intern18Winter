package asiantech.internship.summer.service;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Song;

@SuppressLint("SimpleDateFormat")
public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvNameOfMusic;
    private TextView mTvTimeSong;
    private TextView mTvTimeTotal;
    private SeekBar mSeekbarSong;
    private ImageView mImgBack;
    private ImageView mImgStop;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private List<Song> mListSong;
    private int mPosition = 0;
    private MediaPlayer mMediaPlayer;
    private Animation mAnimation;
    private ImageView mImgDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mapping();
        initSong();
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        creatMediaPlayer();
        mImgPlay.setOnClickListener(this);
        mImgStop.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        mSeekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(mSeekbarSong.getProgress());
            }
        });

    }

    private void mapping() {
        mTvNameOfMusic = findViewById(R.id.tvNameOfMusic);
        mTvTimeSong = findViewById(R.id.tvTimeSong);
        mTvTimeTotal = findViewById(R.id.tvTimeTotal);
        mSeekbarSong = findViewById(R.id.seekbarSong);
        mImgBack = findViewById(R.id.imgBack);
        mImgNext = findViewById(R.id.imgNext);
        mImgPlay = findViewById(R.id.imgPlay);
        mImgStop = findViewById(R.id.imgStop);
        mImgDisc = findViewById(R.id.imgDisc);
    }

    private void creatMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(ServiceActivity.this, mListSong.get(mPosition).getFile());
        mTvNameOfMusic.setText(mListSong.get(mPosition).getName());
        showTime();
        updateTimeSong();
    }

    private void initSong() {
        mListSong = new ArrayList<>();
        mListSong.add(new Song("Cô gái m52", R.raw.co_gai_m52));
        mListSong.add(new Song("Có một nơi như thế", R.raw.co_mot_noi_nhu_the_phan_manh_quynh));
        mListSong.add(new Song("Đừng quên tên anh", R.raw.dung_quen_ten_anh_hoa_vinh));
        mListSong.add(new Song("Hồi ức", R.raw.hoi_uc_phan_manh_quynh));
        mListSong.add(new Song("Hongkong1", R.raw.hongkong1_nguyentrongtai));
        mListSong.add(new Song("Lạc trôi", R.raw.lactroi_sontungmtp));
        mListSong.add(new Song("Người âm phủ", R.raw.nguoiamphu));
        mListSong.add(new Song("Người lạ ơi", R.raw.nguoilaoi_karik));
        mListSong.add(new Song("Nơi này có anh", R.raw.noinaycoanh_sontungmtp));
    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                mTvTimeSong.setText(simpleDateFormat.format(mMediaPlayer.getCurrentPosition()));
                //update seekbar
                mSeekbarSong.setProgress(mMediaPlayer.getCurrentPosition());
                //set next song
                mMediaPlayer.setOnCompletionListener(mp -> playNextSong());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }


    private void showTime() {
        SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
        mTvTimeTotal.setText(formatTime.format(mMediaPlayer.getDuration()));
        mSeekbarSong.setMax(mMediaPlayer.getDuration());
    }

    private void playNextSong() {
        mPosition++;
        if (mPosition > mListSong.size() - 1) {
            mPosition = 0;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mImgPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        creatMediaPlayer();
        mMediaPlayer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mImgPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    mImgDisc.clearAnimation();
                } else {
                    mMediaPlayer.start();
                    mImgPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    mImgDisc.startAnimation(mAnimation);
                }
                break;
            case R.id.imgStop:
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mImgPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                creatMediaPlayer();
                break;
            case R.id.imgNext:
                playNextSong();
                break;
            case R.id.imgBack:
                mPosition--;
                if (mPosition < 0) {
                    mPosition = mListSong.size() - 1;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                }
                mImgPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                creatMediaPlayer();
                mMediaPlayer.start();
                break;
        }
    }
}
