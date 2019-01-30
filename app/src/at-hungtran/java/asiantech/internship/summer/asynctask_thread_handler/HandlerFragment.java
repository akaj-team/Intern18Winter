package asiantech.internship.summer.asynctask_thread_handler;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import asiantech.internship.summer.R;

public class HandlerFragment extends Fragment {
    private static final String TEXT_DOWNLOAD_BUTTON = "Download Images Handler";
    private static final String TAG = HandlerFragment.class.getSimpleName();
    private static final String TITLE_DIALOG = "Handler";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL = "https://cdn.trangcongnghe.com/uploads/posts/2017-04/chia-s-b-nh-nn-200-tm-phn-gii-full-hd-i-gi-pc-ngay-cui-tun-oi-bc_2.jpeg";

    private Button mBtnDownload;
    private ImageView mImgBigResult;

    private ProgressDialog mProgressDialog;

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
        mImgBigResult = view.findViewById(R.id.imgResult);

        mBtnDownload.setText(TEXT_DOWNLOAD_BUTTON);
    }

    private void addListener() {
        mBtnDownload.setOnClickListener(view -> {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(TITLE_DIALOG);
            mProgressDialog.setMessage(MESSAGE_DIALOG);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

            ImageDownloadMessageHandler imageDownloadMessageHandler = new ImageDownloadMessageHandler();
            ImageDownloadThread imageDownloadThread = new ImageDownloadThread(imageDownloadMessageHandler, IMAGE_URL);
            imageDownloadThread.start();
        });
    }

    class ImageDownloadThread extends Thread {
        private ImageDownloadMessageHandler mImageDownloadMessageHandler;
        private String mImageUrl;

        private ImageDownloadThread(ImageDownloadMessageHandler imageDownloadMessageHandler, String imageUrl) {
            mImageDownloadMessageHandler = imageDownloadMessageHandler;
            mImageUrl = imageUrl;
        }

        @Override
        public void run() {
            Bitmap bitmap = LoadImageFromWebOperations(mImageUrl);
            Message message = mImageDownloadMessageHandler.obtainMessage(1, bitmap);
            mImageDownloadMessageHandler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    class ImageDownloadMessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            mImgBigResult.setImageBitmap((Bitmap) message.obj);
            mProgressDialog.dismiss();
        }
    }

    Bitmap LoadImageFromWebOperations(String url) {
        Bitmap bitmap = null;
        try {
            URL aURL = new URL(url);
            URLConnection connection = aURL.openConnection();
            final double length = connection.getContentLength();
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream()) {
                double totalRead;

                @Override
                public synchronized int read(@NonNull byte[] bytes, int off, int len) throws IOException {
                    int bytesRead = super.read(bytes, off, len);

                    if (bytesRead > 0) {
                        totalRead = bytesRead + totalRead;
                        double percent = (totalRead * 100 / length);
                        setProgressDialogValues((int) percent);
                    }
                    return bytesRead;
                }
            };
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, "downloadBitmap " + e);
        }
        return bitmap;
    }

    public void setProgressDialogValues(int values) {
        mProgressDialog.setProgress(values);
    }
}
