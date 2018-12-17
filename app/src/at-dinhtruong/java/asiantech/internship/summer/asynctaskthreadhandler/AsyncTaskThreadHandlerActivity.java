package asiantech.internship.summer.asynctaskthreadhandler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import asiantech.internship.summer.R;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImgImageDownload;
    String URL = "https://9mobi.vn/cf/images/2015/03/nkk/anh-dep-1.jpg";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        mImgImageDownload = findViewById(R.id.imgImageDownload);
        Button btnDownloadAsync = findViewById(R.id.btnDownLoadAsync);
        btnDownloadAsync.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Picasso.get().load(URL).into(mImgImageDownload);
    }
}
