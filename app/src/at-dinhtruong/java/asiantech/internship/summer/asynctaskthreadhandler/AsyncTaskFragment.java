package asiantech.internship.summer.asynctaskthreadhandler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import asiantech.internship.summer.R;


public class AsyncTaskFragment extends Fragment implements View.OnClickListener {
    public static final String URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Pizigani_1367_Chart_10MB.jpg/1280px-Pizigani_1367_Chart_10MB.jpg";
    private ImageView mImgImageDownload;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asynctask_handler_thread, container, false);
        initView(view);
        DownloadTask();
        return view;
    }

    private void initView(View view) {
        mImgImageDownload = view.findViewById(R.id.imgImageDownload);
        Button btnDownloadAsync = view.findViewById(R.id.btnDownLoadAsync);
        btnDownloadAsync.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        new DownloadImageTask().execute(URL_IMAGE);
    }

    public void DownloadTask() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getString(R.string.asyncTask));
        mProgressDialog.setMessage(getString(R.string.downloadingFile));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        long mTotal = 0;
        int mFileLength = 0;

        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmapImage = null;
            InputStream inputStream;
            HttpURLConnection connection;
            try {
                URL url = new URL(urls[0]);
                inputStream = url.openStream();
                bitmapImage = BitmapFactory.decodeStream(inputStream);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                mFileLength = connection.getContentLength();
                inputStream = connection.getInputStream();
                byte data[] = new byte[1024];
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    if (isCancelled()) {
                        inputStream.close();
                        return null;
                    }
                    mTotal += count;
                    if (mFileLength > 0) {
                        publishProgress((int) (mTotal * 100 / mFileLength));
                    }
                }
            } catch (IOException e) {
                Log.e(getString(R.string.inputOoutputException), getString(R.string.getBitmapFromURL), e);
            }
            return bitmapImage;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        protected void onPostExecute(Bitmap result) {
            mProgressDialog.dismiss();
            mImgImageDownload.setImageBitmap(result);
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }
    }
}
