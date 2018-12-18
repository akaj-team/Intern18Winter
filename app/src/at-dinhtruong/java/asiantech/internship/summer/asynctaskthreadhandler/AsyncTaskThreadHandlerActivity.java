package asiantech.internship.summer.asynctaskthreadhandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import asiantech.internship.summer.R;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_IMAGE = "https://9mobi.vn/cf/images/2015/03/nkk/anh-dep-1.jpg";
    private ImageView mImgImageDownload;
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
        new DownloadImageTask(mImgImageDownload).execute(URL_IMAGE);
    }

    /*public DownloadTask(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);

        mProgressDialog.setMessage("Downloading file..");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }
*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream inputStream = new URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
