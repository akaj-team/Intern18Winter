package asiantech.internship.summer.asynctaskthreadhandler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class HandlerFragment extends Fragment {
    private ImageView mImgHandler;
    private Bitmap mBitmap;
    private ProgressDialog mProgressDialog;

    @SuppressLint("HandlerLeak")
    private Handler mMessageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImgHandler.setImageBitmap(mBitmap);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View handlerView = inflater.inflate(R.layout.fragment_handler, container, false);
        initView(handlerView);
        return handlerView;
    }

    private void initView(View handlerView) {
        Button mBtnDownloadHandler;
        mBtnDownloadHandler = handlerView.findViewById(R.id.btnHandler);
        mImgHandler = handlerView.findViewById(R.id.imgHandler);
        mBtnDownloadHandler.setOnClickListener(onCLickView -> onClickDownload());
    }

    private void onClickDownload() {
        initProgressDialog();
        ThreadFragment threadFragment = new ThreadFragment();
        new Thread() {
            public void run() {
                try {
                    while (mProgressDialog.getProgress() <= mProgressDialog.getMax()) {
                        mImgHandler.post(() -> mProgressDialog.incrementProgressBy(1));
                        if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                            mBitmap = threadFragment.getImageBitmap(AsyncTaskFragment.IMAGE_URL);
                            mMessageHandler.sendEmptyMessage(0);
                            mProgressDialog.dismiss();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }.start();
        Toast.makeText(getActivity(), R.string.downloadSuccessfully, Toast.LENGTH_LONG).show();
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
