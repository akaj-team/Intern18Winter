package asiantech.internship.summer.storage;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import java.util.Objects;

import asiantech.internship.summer.R;

public class InternalExternalStoreFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtInternal;
    private EditText mEdtExternal;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private final String FILE_NAME = "note.txt";
    private File mMyInternalFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextWrapper(
                Objects.requireNonNull(getActivity()).getApplicationContext());
        String filePath = "ThuMucCuaToi";
        File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
        String fileNameInternal = "internalStorage.txt";
        mMyInternalFile = new File(directory, fileNameInternal);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_extarnal_store, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEdtInternal = view.findViewById(R.id.edtInternal);
        mEdtExternal = view.findViewById(R.id.edtExternal);
        Button btnInternal = view.findViewById(R.id.btnInternal);
        Button btnExternal = view.findViewById(R.id.btnExternal);
        btnInternal.setOnClickListener(this);
        btnExternal.setOnClickListener(this);
        String myDataInternal = readInternalFile();
        if (!myDataInternal.equals("")) {
            mEdtInternal.setText(myDataInternal);
        } else {
            mEdtInternal.setText("");
        }
        askPermissionAndReadFile();
        String myDataExternal = readExternalFile();
        if (!myDataExternal.equals("")) {
            mEdtExternal.setText(myDataExternal);
        } else {
            mEdtExternal.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInternal:
                writeInternalFile();
                break;
            case R.id.btnExternal:
                askPermissionAndWriteFile();
                writeExternalFile();
                break;
        }
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (canWrite) {
            this.writeExternalFile();
        }
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (canRead) {
            this.readExternalFile();
        }
    }

    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), permissionName);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readExternalFile();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        writeExternalFile();
                    }
                }
            }
        } else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeExternalFile() {
        File extStore = Environment.getExternalStorageDirectory();
        String path = extStore.getAbsolutePath() + "/" + FILE_NAME;
        String data = mEdtExternal.getText().toString();
        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), FILE_NAME + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readExternalFile() {
        File extStore = Environment.getExternalStorageDirectory();
        String path = extStore.getAbsolutePath() + "/" + FILE_NAME;
        String s;
        StringBuilder fileContent = new StringBuilder();
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            while ((s = myReader.readLine()) != null) {
                fileContent.append(s);
            }
            myReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    private String readInternalFile() {
        StringBuilder myData = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(mMyInternalFile);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String strLine;
            while (null != (strLine = bufferedReader.readLine())) {
                myData.append(strLine);
            }
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myData.toString();
    }

    private void writeInternalFile() {
        try {
            FileOutputStream fos = new FileOutputStream(mMyInternalFile);
            fos.write(mEdtInternal.getText().toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
