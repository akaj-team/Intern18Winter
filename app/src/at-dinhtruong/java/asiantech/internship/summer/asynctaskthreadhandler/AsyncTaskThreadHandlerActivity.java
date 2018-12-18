package asiantech.internship.summer.asynctaskthreadhandler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import asiantech.internship.summer.R;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_IMAGE = "https://9mobi.vn/cf/images/2015/03/nkk/anh-dep-1.jpg";
    private ImageView mImgImageDownload;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        mImgImageDownload = findViewById(R.id.imgImageDownload);
        Button btnDownloadAsync = findViewById(R.id.btnDownLoadAsync);
        btnDownloadAsync.setOnClickListener(this);
        DownloadTask();

    }

    @Override
    public void onClick(View view) {
        new DownloadImageTask().execute(URL_IMAGE);
    }

    public void DownloadTask() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading file..");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        long total = 0;
        int fileLength = 0;

        protected Bitmap doInBackground(String... urls) {
            Bitmap bmp = null;
            InputStream input = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urls[0]);
                InputStream inputStream = url.openStream();
                bmp = BitmapFactory.decodeStream(inputStream);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                fileLength = connection.getContentLength();
                input = connection.getInputStream();
                byte data[] = new byte[1024 / 2];

                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        publishProgress((int) (total * 100 / fileLength));
                    }
                }
            } catch (Exception e) {
            }
            return bmp;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        protected void onPostExecute(Bitmap result) {
            mProgressDialog.dismiss();
            if (total == fileLength) {
                mImgImageDownload.setImageBitmap(result);
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }
    }

}
