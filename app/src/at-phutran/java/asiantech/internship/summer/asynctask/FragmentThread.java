package asiantech.internship.summer.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import asiantech.internship.summer.R;

public class FragmentThread extends Fragment {
    private static final String TAG = "FragmentThread";
    private static final String TITLE_DIALOG = "Thread";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private final static String IMAGE_URL = "https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg";
    private Button mBtnDownload;
    private ImageView mImgResultView;
    private ProgressDialog mDialog;
    private Bitmap mBitmap;
    private Handler mHandler;
    private Thread mDownloadThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        initView(view);
        addListener();
        return view;
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mImgResultView = view.findViewById(R.id.imgContent);
    }

    private void addListener() {
        mHandler = new Handler();
        mBtnDownload.setOnClickListener(view -> {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setTitle(TITLE_DIALOG);
            mDialog.setMessage(MESSAGE_DIALOG);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setMax(100);
            mDialog.setProgress(0);
            mDialog.setCancelable(true);
            mDialog.show();
            mDownloadThread = new MyThread();
            mDownloadThread.start();
        });
    }

    @Override
    public void onDestroy() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        super.onDestroy();
    }

    private Bitmap downloadBitmap(String... urls) {
        HttpURLConnection connection;
        Bitmap bitmap;
        try {
            URL currentURL = new URL(urls[0]);
            connection = (HttpURLConnection) currentURL.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            Log.d(TAG, getString(R.string.exception) + e);
            return null;
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            mBitmap = downloadBitmap(IMAGE_URL);
            mHandler.post(new MyRunnable());
        }
    }

    public class MyRunnable implements Runnable {
        public void run() {
            mImgResultView.setImageBitmap(mBitmap);
            mDialog.dismiss();
        }
    }
}
