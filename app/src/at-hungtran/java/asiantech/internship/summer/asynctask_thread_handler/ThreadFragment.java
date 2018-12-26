package asiantech.internship.summer.asynctask_thread_handler;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import asiantech.internship.summer.R;

public class ThreadFragment extends Fragment {
    private static final String TEXT_DOWNLOAD_BUTTON = "Download Images Thread";
    private static final String TAG = ThreadFragment.class.getSimpleName();
    private static final String TITLE_DIALOG = "Thread";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL = "https://cdn.trangcongnghe.com/uploads/posts/2017-04/chia-s-b-nh-nn-200-tm-phn-gii-full-hd-i-gi-pc-ngay-cui-tun-oi-bc_2.jpeg";

    private Button mBtnDownload;
    private ImageView mImgResult;
    private Bitmap mBitmap;

    private ProgressDialog mDialog;
    private Handler mHandler;
    private Thread mDownloadThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_images, container, false);
        initView(view);
        addListener();
        return view;
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mImgResult = view.findViewById(R.id.imgResult);

        mBtnDownload.setText(TEXT_DOWNLOAD_BUTTON);
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

    private Bitmap downloadBitmap(String... strings) {
        Bitmap bitmapImage = null;
        int total = 0;
        int fileLength;
        try {
            URL url = new URL(strings[0]);
            InputStream inputStream = url.openStream();
            bitmapImage = BitmapFactory.decodeStream(inputStream);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            fileLength = connection.getContentLength();
            inputStream = connection.getInputStream();
            byte data[] = new byte[1024];
            int count;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                if (fileLength > 0) {
                    setProgressDialogValues((total * 100 / fileLength));
                }
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: ", e);
        }
        return bitmapImage;
    }

    public void setProgressDialogValues(int values) {
        mDialog.setProgress(values);
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            mBitmap = (downloadBitmap(IMAGE_URL));
            mHandler.post(new MyRunnable());
        }
    }

    public class MyRunnable implements Runnable {
        public void run() {
            mImgResult.setImageBitmap(mBitmap);
            mDialog.dismiss();
        }
    }
}