package asiantech.internship.summer.asynctaskthreadhandler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import asiantech.internship.summer.R;

public class ThreadFragment extends Fragment {
    private ImageView mImgThread;
    private ProgressDialog mProgressDialog;
    private Bitmap mBitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View threadView = inflater.inflate(R.layout.fragment_thread, container, false);
        initView(threadView);
        return threadView;
    }

    private void initView(View threadView) {
        Button mBtnThread;
        mBtnThread = threadView.findViewById(R.id.btnThread);
        mImgThread = threadView.findViewById(R.id.imgThread);
        mBtnThread.setOnClickListener(onClickView -> onCLickDownload());
    }

    private void onCLickDownload() {
        initProgressDialog();

        new Thread() {
            public void run() {

                try {
                    while (mProgressDialog.getProgress() <= mProgressDialog.getMax()) {
                        mImgThread.post(() -> mProgressDialog.incrementProgressBy(1));
                        if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                            mBitmap = getImageBitmap(AsyncTaskFragment.IMAGE_URL);
                            mImgThread.setImageBitmap(mBitmap);
                            mProgressDialog.dismiss();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }.start();
        Toast.makeText(getActivity(), R.string.downloadSuccessfully, Toast.LENGTH_LONG).show();
    }

    public Bitmap getImageBitmap(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException ignored) {
            return null;
        }
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(R.string.handler);
        mProgressDialog.setMessage(getString(R.string.downloading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.show();
    }
}
