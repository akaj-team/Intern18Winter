package asiantech.internship.summer.asynctask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.InputStream;
import asiantech.internship.summer.R;

public class FragmentAsyncTask extends Fragment {
    private final static String IMAGE_URL = "https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private ImageView mImgAsyncTask;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View asyncTaskView = inflater.inflate(R.layout.fragment_download, container, false);
        initView(asyncTaskView);
        return asyncTaskView;
    }

    private void initView(View asyncTaskView) {
        Button mBtnDownload;
        mBtnDownload = asyncTaskView.findViewById(R.id.btnDownload);
        mImgAsyncTask = asyncTaskView.findViewById(R.id.imgContent);
        mBtnDownload.setOnClickListener(onClickView -> new DownloadImage().execute(IMAGE_URL));
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(R.string.asyncTask);
        mProgressDialog.setMessage(MESSAGE_DIALOG);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initProgressDialog();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                new Thread(() -> {
                    try {
                        while (mProgressDialog.getProgress() <= mProgressDialog.getMax()) {
                            mImgAsyncTask.post(() -> mProgressDialog.incrementProgressBy(1));
                            if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                                mProgressDialog.dismiss();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }).start();
            } catch (Exception ignored) {
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mImgAsyncTask.setImageBitmap(result);
            Toast.makeText(getActivity(), getString(R.string.complete), Toast.LENGTH_LONG).show();
        }
    }
}