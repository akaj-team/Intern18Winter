package asiantech.internship.summer.asynctaskthreadhandler;

import android.annotation.SuppressLint;
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

    @SuppressLint("HandlerLeak")
    private Handler mMessageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImgHandler.setImageBitmap(mBitmap);
            Toast.makeText(getActivity(), R.string.downloadSuccessfully, Toast.LENGTH_LONG).show();
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
        ThreadFragment threadFragment = new ThreadFragment();
        new Thread() {
            public void run() {
                mBitmap = threadFragment.getImageBitmap(AsyncTaskFragment.IMAGE_URL);
                mMessageHandler.sendEmptyMessage(0);
            }
        }.start();
    }
}
