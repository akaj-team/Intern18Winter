package asiantech.internship.summer.filestorage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
    private static final String INTERNAL_FILE_NAME = "internalExample.txt";
    private static final String EXTERNAL_FILE_NAME = "externalExample.txt";
    private static final String EXTERNAL_DIRECTORY_NAME = "MyAppFile";
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final int SDK_VERSION_CHECK = 23;
    private EditText mEdtInputInternal;
    private EditText mEdtInputExternal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_external, container, false);

        mEdtInputInternal = view.findViewById(R.id.edtIntStorage);
        mEdtInputExternal = view.findViewById(R.id.edtExtStorage);
        Button btnSaveInternal = view.findViewById(R.id.btnSaveInternal);
        Button btnLoadInternal = view.findViewById(R.id.btnLoadInternal);
        Button btnSaveExternal = view.findViewById(R.id.btnSaveExternal);
        Button btnLoadExternal = view.findViewById(R.id.btnLoadExternal);

        btnSaveInternal.setOnClickListener(saveInternalView -> saveDataInternal());
        btnLoadInternal.setOnClickListener(loadInternalView -> loadDataInternal());
        btnSaveExternal.setOnClickListener(saveExternalView -> {
            askPermissionAndWriteFile();
            saveDataExternal();
        });
        btnLoadExternal.setOnClickListener(loadExternalView -> loadDataExternal());
        return view;
    }

    public void saveDataInternal() {
        String text = mEdtInputInternal.getText().toString();
        if (mEdtInputInternal.getText().length() != 0) {
            try (FileOutputStream fos = Objects.requireNonNull(getActivity()).openFileOutput(INTERNAL_FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes(getString(R.string.encoding)));
                mEdtInputInternal.getText().clear();
                Toast.makeText(getActivity(), getString(R.string.saveToDir) + " " + getActivity().getFilesDir() + "/" + INTERNAL_FILE_NAME, Toast.LENGTH_LONG).show();
            } catch (IOException ignored) {
            }
        } else {
            Toast.makeText(getActivity(), R.string.inputNull, Toast.LENGTH_LONG).show();
        }
    }

    public void loadDataInternal() {
        try (FileInputStream fis = Objects.requireNonNull(getActivity()).openFileInput(INTERNAL_FILE_NAME)) {
            InputStreamReader isr = new InputStreamReader(fis, getString(R.string.encoding));
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
                mEdtInputInternal.setText(text);
                Toast.makeText(getActivity(), getString(R.string.loadFromDir) + " " + getActivity().getFilesDir() + "/" + INTERNAL_FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (IOException ignored) {
        }
    }

    private void saveDataExternal() {
        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/" + EXTERNAL_DIRECTORY_NAME);
            File file = new File(Dir, EXTERNAL_FILE_NAME);
            String Message = mEdtInputExternal.getText().toString();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Message.getBytes(getString(R.string.encoding)));
                fos.close();
                mEdtInputExternal.setText("");
                Toast.makeText(getActivity(), R.string.inputSaved, Toast.LENGTH_LONG).show();
            } catch (IOException ignored) {
            }
        } else {
            Toast.makeText(getActivity(), R.string.sdCardNotFound, Toast.LENGTH_LONG).show();
        }
    }

    private void loadDataExternal() {
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File(Root.getAbsolutePath() + "/" + EXTERNAL_DIRECTORY_NAME);
        File file = new File(Dir, EXTERNAL_FILE_NAME);
        String input;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, getString(R.string.encoding));
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((input = br.readLine()) != null) {
                sb.append(input).append("\n");
            }
            mEdtInputExternal.setText(sb.toString());
        } catch (IOException ignored) {
        }
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission();
        if (canWrite) {
            this.loadDataExternal();
        }
    }

    private boolean askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= SDK_VERSION_CHECK) {
            int permission = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        InternalExternalFragment.REQUEST_ID_WRITE_PERMISSION);
                return false;
            }
        }
        return true;
    }
}
