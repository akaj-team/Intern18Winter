package asiantech.internship.summer.file_storage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import asiantech.internship.summer.R;

public class StorageFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final String FILENAME_INTERNAL = "input_internal.txt";
    private static final String FILENAME_EXTERNAL = "input_external.txt";
    private static final String TAG = "storageFragment";
    private EditText mEdtInternalStorage;
    private EditText mEdtExternalStorage;
    private String mText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage, container, false);
        mEdtInternalStorage = view.findViewById(R.id.edtInputInternal);
        mEdtExternalStorage = view.findViewById(R.id.edtInputExternal);
        Button btnInternalStorage = view.findViewById(R.id.btnInternalStorage);
        Button btnExternalStorage = view.findViewById(R.id.btnExternalStorage);
        readInternalStorage();
        readFileExternalStorage();
        btnInternalStorage.setOnClickListener(this);
        btnExternalStorage.setOnClickListener(this);
        return view;
    }

    public void saveInternalStorage() {
        mText = mEdtInternalStorage.getText().toString();
        try (FileOutputStream fileOutputStream = Objects.requireNonNull(getActivity()).openFileOutput(FILENAME_INTERNAL, Context.MODE_PRIVATE)) {
            fileOutputStream.write(mText.getBytes(StandardCharsets.UTF_8));
            mEdtInternalStorage.getText().clear();
            Toast.makeText(getActivity(), FILENAME_INTERNAL + getString(R.string.save), Toast.LENGTH_LONG).show();
            readInternalStorage();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void readInternalStorage() {
        try {
            FileInputStream fileInputStream = Objects.requireNonNull(getActivity()).openFileInput(FILENAME_INTERNAL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text).append("\n");
            }
            mEdtInternalStorage.setText(stringBuilder.toString());
            fileInputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(
        );
        if (canWrite) {
            this.writeFileExternalStorage();
        }
    }

    private boolean askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // Check if we have permission
            int permission = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        StorageFragment.REQUEST_ID_WRITE_PERMISSION
                );
                return false;
            }
        }
        return true;
    }

    private void writeFileExternalStorage() {
        File extStore = Environment.getExternalStorageDirectory();
        String path = extStore.getAbsolutePath() + "/" + FILENAME_EXTERNAL;
        mText = mEdtExternalStorage.getText().toString();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            myOutWriter.append(mText);
            myOutWriter.close();
            fileOutputStream.close();
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), FILENAME_EXTERNAL + getString(R.string.save), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), FILENAME_EXTERNAL + getString(R.string.error), Toast.LENGTH_LONG).show();
        }
    }

    private void readFileExternalStorage() {
        File folderPath = Environment.getExternalStorageDirectory();
        String filePath = folderPath.getAbsolutePath() + "/" + FILENAME_EXTERNAL;
        String text;
        StringBuilder fileContent = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((text = myReader.readLine()) != null) {
                fileContent.append(text).append("\n");
            }
            myReader.close();
            this.mEdtExternalStorage.setText(fileContent.toString());
        } catch (IOException ignored) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInternalStorage: {
                saveInternalStorage();
                break;
            }
            case R.id.btnExternalStorage: {
                askPermissionAndWriteFile();
                break;
            }
        }
    }
}
