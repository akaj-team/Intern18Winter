package asiantech.internship.summer.asynctaskthreadhandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ThreadFragment extends Fragment implements View.OnClickListener {

    private ImageView mImgImageDownload;

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
        new Thread(() -> {
            final Bitmap b = getBitmapFromURL(AsyncTaskFragment.URL_IMAGE);
            mImgImageDownload.post(() -> mImgImageDownload.setImageBitmap(b));
        }).start();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.e(getString(R.string.inputOoutputException), getString(R.string.getBitmapFromURL), e);
            return null;
        }
    }
}
