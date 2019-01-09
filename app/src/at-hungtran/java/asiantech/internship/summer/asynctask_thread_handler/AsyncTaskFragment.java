package asiantech.internship.summer.asynctask_thread_handler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class AsyncTaskFragment extends Fragment {

    private static final String TAG = AsyncTaskFragment.class.getSimpleName();
    private static final String TITLE_DIALOG = "AsyncTask";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL = "https://cdn.trangcongnghe.com/uploads/posts/2017-04/chia-s-b-nh-nn-200-tm-phn-gii-full-hd-i-gi-pc-ngay-cui-tun-oi-bc_2.jpeg";

    private Button mBtnDownload;
    private ImageView mImgResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_images, container, false);

        initView(view);
        addListener();
        return view;
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mImgResult = view.findViewById(R.id.imgResult);
    }

    private void addListener() {
        mBtnDownload.setOnClickListener(view -> {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(IMAGE_URL);
        });
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(TITLE_DIALOG);
            mProgressDialog.setMessage(MESSAGE_DIALOG);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmapImage = null;
            int total = 0;
            int fileLength;
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openStream();
                bitmapImage = BitmapFactory.decodeStream(inputStream);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                fileLength = connection.getContentLength();
                inputStream = connection.getInputStream();
                byte data[] = new byte[1024];
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    if (fileLength > 0) {
                        publishProgress((int) (total * 100 / fileLength));
                    }
                }
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
            }
            return bitmapImage;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setProgress(values[0]);
            mProgressDialog.setMax(100);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressDialog.dismiss();
            mImgResult.setImageBitmap(bitmap);
        }
    }
}
