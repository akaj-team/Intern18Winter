package asiantech.internship.summer.storage;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
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

import asiantech.internship.summer.R;

public class InternalExternalStoreFragment extends Fragment implements View.OnClickListener {
    private Button mBtnInternal;
    private Button mBtnExternal;
    private EditText mEdtInternal;
    private EditText mEdtExternal;
    private final String FILE_NAME = "internalStorage.txt";
    private final String FILE_PATH = "ThuMucCuaToi";
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "note.txt";
    File myInternalFile;

    public InternalExternalStoreFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextWrapper(
                getActivity().getApplicationContext());
        File directory = contextWrapper.getDir(FILE_PATH, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_extarnal_store, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEdtInternal = view.findViewById(R.id.edtInternal);
        mEdtExternal = view.findViewById(R.id.edtExternal);
        mBtnInternal = view.findViewById(R.id.btnInternal);
        mBtnInternal.setOnClickListener(this);
        mBtnExternal = view.findViewById(R.id.btnExternal);
        mBtnExternal.setOnClickListener(this);
        String myData = readInternalFile();
        if (!myData.equals("")) {
            mEdtInternal.setText(myData);
        } else {
            mEdtInternal.setText("");
        }
        readExternalFile();

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

    // Với Android Level >= 23 hỏi người dùng cho phép các quyền với thiết bị
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(getActivity(), permissionName);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // Nếu không có quyền,nhắc người dùng cho phép.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    // Khi yêu cầu hỏi người dùng được trả về (Chấp nhận hoặc không chấp nhận).
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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
            Toast.makeText(getActivity().getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeExternalFile() {
        File extStore = Environment.getExternalStorageDirectory();
        String path = extStore.getAbsolutePath() + "/" + fileName;
        String data = mEdtExternal.getText().toString();
        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getActivity().getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readExternalFile() {
        File extStore = Environment.getExternalStorageDirectory();
        String path = extStore.getAbsolutePath() + "/" + fileName;
        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            while ((s = myReader.readLine()) != null) {
                //fileContent += s + "\n";
                fileContent += s;
            }
            myReader.close();
            mEdtExternal.setText(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity().getApplicationContext(), fileContent, Toast.LENGTH_LONG).show();
    }

    private String readInternalFile() {
        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(myInternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myData;
    }

    private void writeInternalFile() {
        try {
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            fos.write(mEdtInternal.getText().toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
