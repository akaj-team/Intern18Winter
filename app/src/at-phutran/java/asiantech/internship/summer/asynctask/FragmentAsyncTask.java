package asiantech.internship.summer.asynctask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import asiantech.internship.summer.R;

public class FragmentAsyncTask extends Fragment {
    private static final String TAG = "FragmentAsyncTask";
    private final static String IMAGE_URL = "https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg";
    private static final String TITLE_DIALOG = "AsyncTask";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String PATH = "sdcard/Pictures/downloaded_image.jpg";
    private ImageView mImgResultView;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        Button btnDownload = view.findViewById(R.id.btnDownload);
        mImgResultView = view.findViewById(R.id.imgContent);
        btnDownload.setOnClickListener(view1 -> {
            DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
            downloadAsyncTask.execute(IMAGE_URL);
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(TITLE_DIALOG);
            mProgressDialog.setMessage(MESSAGE_DIALOG);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            int fileLength;
            try {
                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                fileLength = urlConnection.getContentLength();
                File newFolder = new File(getString(R.string.folder));
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                File inputFile = new File(newFolder, getString(R.string.image));
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count;
                OutputStream outputStream = new FileOutputStream(inputFile);
                Log.i("xxx", "doInBackground: " + outputStream);
                while ((count = inputStream.read(data)) != -1) {
                    Log.i("xxx", "doInBackground: ");
                    total += count;
                    outputStream.write(data, 0, count);
                    int process = total * 100 / fileLength;
                    publishProgress(process);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                Log.d(TAG, getString(R.string.exception) + e);
            }
            return getString(R.string.complete);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.hide();
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            mImgResultView.setImageDrawable(Drawable.createFromPath(PATH));
        }
    }
}
