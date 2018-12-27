package asiantech.internship.summer.asynctask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.net.URL;
import java.net.URLConnection;
import asiantech.internship.summer.R;

public class FragmentHandler extends Fragment {
    private static final String TAG = "FragmentHandler";
    private static final String TITLE_DIALOG = "Handler";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL = "https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg";
    private Button mBtnDownload;
    private ImageView mImgResultView;
    private ProgressDialog mProgressDialog;

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
        mBtnDownload.setOnClickListener(view -> {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(TITLE_DIALOG);
            mProgressDialog.setMessage(MESSAGE_DIALOG);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ImageDownloadMessageHandler imageDownloadMessageHandler1 = new ImageDownloadMessageHandler();
            ImageDownloadThread imageDownloadThread = new ImageDownloadThread(imageDownloadMessageHandler1, IMAGE_URL);
            imageDownloadThread.start();
        });
    }

    Bitmap LoadImageFromWebOperations(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            final double length = conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream()) {
                double totalRead;

                @Override
                public synchronized int read(@NonNull byte[] b, int off, int len) throws IOException {
                    int bytesRead = super.read(b, off, len);

                    if (bytesRead > 0) {
                        totalRead = bytesRead + totalRead;
                        double percent = (totalRead * 100 / length);
                        setProgressDialogValues((int) percent);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.d(TAG, getString(R.string.interruptedException) + e);
                    }
                    return bytesRead;
                }
            };
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (IOException e) {
            Log.d(TAG, getString(R.string.exception) + e);
        }
        return bm;
    }

    public void setProgressDialogValues(int values) {
        mProgressDialog.setProgress(values);
    }

    class ImageDownloadThread extends Thread {
        ImageDownloadMessageHandler imageDownloadMessageHandler;
        String urlImage;

        private ImageDownloadThread(ImageDownloadMessageHandler imageDownloadMessageHandler, String urlIimage) {
            this.imageDownloadMessageHandler = imageDownloadMessageHandler;
            this.urlImage = urlIimage;
        }

        @Override
        public void run() {
            Bitmap bitmap = LoadImageFromWebOperations(urlImage);
            Message message = imageDownloadMessageHandler.obtainMessage(1, bitmap);
            imageDownloadMessageHandler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    class ImageDownloadMessageHandler extends Handler {

        private ImageDownloadMessageHandler() {
        }

        @Override
        public void handleMessage(Message message) {
            mImgResultView.setImageBitmap((Bitmap) message.obj);
            mProgressDialog.dismiss();
        }
    }
}