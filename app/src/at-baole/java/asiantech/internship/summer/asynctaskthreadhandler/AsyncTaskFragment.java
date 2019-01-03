package asiantech.internship.summer.asynctaskthreadhandler;

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

public class AsyncTaskFragment extends Fragment {
    public static final String IMAGE_URL = "https://scontent.fdad2-1.fna.fbcdn.net/v/t1.0-9/36467672_850353798490967_3085372405137276928_n.jpg?_nc_cat=108&_nc_ht=scontent.fdad2-1.fna&oh=276751dbdd9faea47552fd0e79c3761c&oe=5C9184B4";
    private ImageView mImgAsyncTask;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View asyncTaskView = inflater.inflate(R.layout.fragment_asynctask, container, false);
        initView(asyncTaskView);
        return asyncTaskView;
    }

    private void initView(View asyncTaskView){
        Button mBtnDownload;
        mBtnDownload = asyncTaskView.findViewById(R.id.btnAsyncTask);
        mImgAsyncTask = asyncTaskView.findViewById(R.id.imgAsyncTask);
        mBtnDownload.setOnClickListener(onClickView -> new DownloadImage().execute(IMAGE_URL));
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(R.string.asyncTask);
        mProgressDialog.setMessage(getString(R.string.downloading));
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
            Toast.makeText(getActivity(), R.string.downloadSuccessfully, Toast.LENGTH_LONG).show();
        }
    }
}
