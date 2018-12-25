package asiantech.internship.summer.filestorage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import asiantech.internship.summer.R;

public class InternalExternalFragment extends Fragment {
    private static final String FILE_NAME = "example.txt";
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final int SDK_VERSION_CHECK = 23;
    private EditText mEdtInputInternal;
    private EditText mEdtInputExternal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_external, container, false);
        mEdtInputInternal = view.findViewById(R.id.edtIntStorage);
        mEdtInputExternal = view.findViewById(R.id.edtExtStorage);
        Button mBtnSaveInternal = view.findViewById(R.id.btnSaveInternal);
        Button mBtnLoadInternal = view.findViewById(R.id.btnLoadInternal);
        Button mBtnSaveExternal = view.findViewById(R.id.btnSaveExternal);
        Button mBtnLoadExternal = view.findViewById(R.id.btnLoadExternal);
        mBtnSaveInternal.setOnClickListener(saveInternalView -> saveDataInternal());
        mBtnLoadInternal.setOnClickListener(loadInternalView -> loadDataInternal());
        mBtnSaveExternal.setOnClickListener(saveExternalView -> {
            askPermissionAndWriteFile();
            saveDataExternal();
        });
        mBtnLoadExternal.setOnClickListener(loadExternalView -> loadDataExternal());
        return view;
    }

    public void saveDataInternal() {
        String text = mEdtInputInternal.getText().toString();
        if (mEdtInputInternal.getText().length() != 0) {
            try (FileOutputStream fos = Objects.requireNonNull(getActivity()).openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes("utf-8"));
                mEdtInputInternal.getText().clear();
                Toast.makeText(getActivity(), getString(R.string.saveToDir) + " " + getActivity().getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            } catch (IOException ignored) {
            }
        } else {
            Toast.makeText(getActivity(), R.string.inputNull, Toast.LENGTH_LONG).show();
        }
    }

    public void loadDataInternal() {
        try (FileInputStream fis = Objects.requireNonNull(getActivity()).openFileInput(FILE_NAME)) {
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb;
            sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
                mEdtInputInternal.setText(text);
                Toast.makeText(getActivity(), getString(R.string.loadFromDir) + " " + getActivity().getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (IOException ignored) {
        }
    }

    private void saveDataExternal() {
        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/MyAppFile");
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            File file = new File(Dir, "MyMessage.txt");
            String Message = mEdtInputExternal.getText().toString();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Message.getBytes());
                fos.close();
                mEdtInputExternal.setText("");
                Log.d("xxx", "saveDataExternal: " +Dir);
                Toast.makeText(getActivity(), "Message Saved", Toast.LENGTH_LONG).show();
            } catch (IOException ignored) {
            }
        } else {
            Toast.makeText(getActivity(), "SD card Not Found", Toast.LENGTH_LONG).show();
        }
    }

    private void loadDataExternal() {
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File(Root.getAbsolutePath() + "/MyAppFile");
        File file = new File(Dir, "MyMessage.txt");
        String Message;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((Message = br.readLine()) != null) {
                sb.append(Message).append("\n");
            }
            mEdtInputExternal.setText(sb.toString());
        } catch (IOException ignored) {
        }
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission();
        //
        if (canWrite) {
            this.loadDataExternal();
        }
    }

    private boolean askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= SDK_VERSION_CHECK) {
            // Check if we have permission
            int permission = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        InternalExternalFragment.REQUEST_ID_WRITE_PERMISSION);
                return false;
            }
        }
        return true;
    }
}
