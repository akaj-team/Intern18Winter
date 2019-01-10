package asiantech.internship.summer.service;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Song;

public class ServiceActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mTvNameOfMusic = findViewById(R.id.tvNameOfMusic);
        mTvTimeSong = findViewById(R.id.tvTimeSong);
        mTvTimeTotal = findViewById(R.id.tvTimeTotal);
        mSeekbarSong = findViewById(R.id.seekbarSong);
        mapping();
        initSong();
        mImgPlay.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(ServiceActivity.this, mListSong.get(mPosition).getFile());
            mediaPlayer.start();
            mTvNameOfMusic.setText(mListSong.get(mPosition).getName());
        });
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

    private void mapping() {
        mTvNameOfMusic = findViewById(R.id.tvNameOfMusic);
        mTvTimeSong = findViewById(R.id.tvTimeSong);
        mTvTimeTotal = findViewById(R.id.tvTimeTotal);
        mSeekbarSong = findViewById(R.id.seekbarSong);
        mImgBack = findViewById(R.id.btnBack);
        mImgNext = findViewById(R.id.btnNext);
        mImgPlay = findViewById(R.id.btnPlay);
        mImgStop = findViewById(R.id.btnStop);
    }
}
