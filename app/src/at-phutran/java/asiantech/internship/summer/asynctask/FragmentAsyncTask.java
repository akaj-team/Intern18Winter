package asiantech.internship.summer.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import asiantech.internship.summer.R;

public class FragmentAsyncTask extends Fragment {
    private Button mButton;
    private ImageView mImageView;
    private ProgressDialog mProgressDialog;
    String image_url = "https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_async_task, container, false);

        mButton = view.findViewById(R.id.btnDownload);
        mImageView = view.findViewById(R.id.imgContent);
        mButton.setOnClickListener(view1 -> {
            DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
            downloadAsyncTask.execute(image_url);
        });
        return view;
    }
    class DownloadAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Download in process ...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            int fileLength = 0;
            try {
                URL url = new URL(path);
//                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                URLConnection urlConnection = url.openConnection();
                Log.i("aaa", "doInBackground: 1 " + urlConnection);

                urlConnection.connect();
                Log.i("aaa", "doInBackground: 2");
                fileLength = urlConnection.getContentLength();
                Log.i("aaa", "doInBackground: 2");


                File newFolder = new File("sdcard/Pictures");
                if(!newFolder.exists()){
                    newFolder.mkdir();
                }
                File inputFile = new File(newFolder, "downloaded_image.jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream outputStream = new FileOutputStream(inputFile);
                while ((count = inputStream.read(data)) != -1){
                    total += count;
//                    Log.i("ssss", "doInBackground: "+ count);
                    outputStream.write(data, 0, count);
                    int process = (int) total * 100 / fileLength;
                    publishProgress(process);
                }
                Log.i("ssss", "doInBackground: "+ total);
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Dowload complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.hide();
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            String path = "sdcard/Pictures/downloaded_image.jpg";
            mImageView.setImageDrawable(Drawable.createFromPath(path));
        }
    }
}
