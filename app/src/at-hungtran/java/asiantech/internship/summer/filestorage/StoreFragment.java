package asiantech.internship.summer.filestorage;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import asiantech.internship.summer.R;

public class StoreFragment extends Fragment {
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final int VERSION_SDK = 23;
    private static final String TAG = "StoreFragment";
    private final String mFileNameExternal = "externalStorage.txt";
    private final String mFilenameInternal = "internalStorage.txt";
    private File mMyFile;
    private String mFilepath = "MyDir";
    private EditText mEdtInternal;
    private EditText mEdtExternal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initView(view);
        readFileInternal();
        readFileExternal();
        return view;
    }

    private void initView(View view) {
        mEdtInternal = view.findViewById(R.id.edtInternal);
        mEdtExternal = view.findViewById(R.id.edtExternal);
        Button mBtnInternal = view.findViewById(R.id.btnInternal);
        Button mBtnExternal = view.findViewById(R.id.btnExternal);
        mBtnInternal.setOnClickListener(v -> onClickInternal());
        mBtnExternal.setOnClickListener(v -> onCLickExternal());
    }

    private void onCLickExternal() {
        boolean canWrite = this.askPermission(
        );
        if (canWrite) {
            this.writeFileExternal();
        }
    }

    private boolean askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= VERSION_SDK) {
            int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        StoreFragment.REQUEST_ID_WRITE_PERMISSION
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readFileExternal();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        writeFileExternal();
                    }
                }
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.permissionCancel, Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFileExternal() {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File extStore = contextWrapper.getDir(mFilepath, Context.MODE_PRIVATE);
        String path = extStore.getAbsolutePath() + "/" + mFileNameExternal;
        String external = mEdtExternal.getText().toString();

        try {
            mMyFile = new File(path);
            mMyFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(mMyFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut,String.valueOf(R.string.utf8));
            myOutWriter.append(external);
            myOutWriter.close();
            fOut.close();

            Toast.makeText(getActivity().getApplicationContext(), mFileNameExternal + R.string.saved, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void onClickInternal() {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File directory = contextWrapper.getDir(mFilepath, Context.MODE_PRIVATE);
        mMyFile = new File(directory, mFilenameInternal);
        String internal = mEdtInternal.getText().toString();
        try {
            FileOutputStream fos = new FileOutputStream(mMyFile);
            fos.write(internal.getBytes(Charset.forName(String.valueOf(R.string.utf8))));
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        Toast.makeText(getActivity(), R.string.saveSuccessfully,
                Toast.LENGTH_LONG).show();
    }

    private void readFileInternal() {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File directory = contextWrapper.getDir(mFilepath, Context.MODE_PRIVATE);
        mMyFile = new File(directory, mFilenameInternal);
        StringBuilder myData = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(mMyFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in,String.valueOf(R.string.utf8)));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData.append(strLine);
            }
            in.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        mEdtInternal.setText(myData.toString());
    }

    private void readFileExternal() {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File extStore = contextWrapper.getDir(mFilepath, Context.MODE_PRIVATE);
        String path = extStore.getAbsolutePath() + "/" + mFileNameExternal;
        String s;
        StringBuilder fileContent = new StringBuilder();
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn,String.valueOf(R.string.utf8)));

            while ((s = myReader.readLine()) != null) {
                fileContent.append(s).append("\n");
            }
            myReader.close();
            mEdtExternal.setText(fileContent.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
