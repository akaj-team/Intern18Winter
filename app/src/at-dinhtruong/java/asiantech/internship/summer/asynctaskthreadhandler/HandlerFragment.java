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

import asiantech.internship.summer.R;

public class HandlerFragment extends Fragment implements View.OnClickListener {
    private ImageView mImgImageDownload;
    private Bitmap mBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asynctask_handler_thread, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button btnDownload = view.findViewById(R.id.btnDownLoadAsync);
        mImgImageDownload = view.findViewById(R.id.imgImageDownload);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ThreadFragment threadFragment = new ThreadFragment();
        new Thread() {
            public void run() {
                mBitmap = threadFragment.getBitmapFromURL(AsyncTaskFragment.URL_IMAGE);
                messageHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImgImageDownload.setImageBitmap(mBitmap);
        }
    };
}
