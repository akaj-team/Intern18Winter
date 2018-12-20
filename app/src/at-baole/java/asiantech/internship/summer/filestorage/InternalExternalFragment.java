package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import asiantech.internship.summer.R;

public class InternalExternalFragment extends Fragment {
    private static final String FILE_NAME = "example.txt";
    private EditText mEdtInputInternal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_external, container, false);
        mEdtInputInternal = view.findViewById(R.id.edtIntStorage);
        Button mBtnSaveInternal = view.findViewById(R.id.btnSaveInternal);
        Button mBtnLoadInternal = view.findViewById(R.id.btnLoadInternal);
        mBtnSaveInternal.setOnClickListener(intSaveView -> saveDataInternal());
        mBtnLoadInternal.setOnClickListener(intLoadView -> loadDataInternal());
        return view;
    }

    public void saveDataInternal() {
        String text = mEdtInputInternal.getText().toString();
        if (mEdtInputInternal.getText().length() != 0) {
            try (FileOutputStream fos = Objects.requireNonNull(getActivity()).openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes());
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
            InputStreamReader isr = new InputStreamReader(fis);
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
}
