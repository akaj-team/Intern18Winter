package asiantech.internship.summer.asynctaskthreadhandler;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View threadView = inflater.inflate(R.layout.fragment_thread, container, false);
        initView(threadView);
        return threadView;
    }

    private void initView(View threadView) {
        Button mBtnDownloadThread;
        mBtnDownloadThread = threadView.findViewById(R.id.btnThread);
        mImgThread = threadView.findViewById(R.id.imgThread);
        mBtnDownloadThread.setOnClickListener(onClickView -> onCLickDownload());
    }

    private void onCLickDownload() {
        new Thread(() -> {
            final Bitmap bitmap = getImageBitmap(AsyncTaskFragment.IMAGE_URL);
            mImgThread.post(() -> {
                mImgThread.setImageBitmap(bitmap);
                Toast.makeText(getActivity(), R.string.downloadSuccessfully, Toast.LENGTH_LONG).show();
            });
        }).start();
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
}
