package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import asiantech.internship.summer.R;

public class InternalExternalFragment extends Fragment {
    private static final String FILE_NAME = "example.txt";
    private EditText mEdtInputInternal;
    private Button mBtnSaveInternal;
    private Button mBtnLoadInternal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_external, container, false);
        mEdtInputInternal = view.findViewById(R.id.edtIntStorage);
        mBtnSaveInternal = view.findViewById(R.id.btnSaveInternal);
        mBtnLoadInternal = view.findViewById(R.id.btnLoadInternal);
        mBtnSaveInternal.setOnClickListener(view1 -> save());
        mBtnLoadInternal.setOnClickListener(view12 -> load());
        return view;
    }

    public void save() {
        String text = mEdtInputInternal.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            mEdtInputInternal.getText().clear();
            Toast.makeText(getActivity(), "Saved to" + getActivity().getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

        } catch (IOException ignored) {

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public void load() {
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                Log.d("xxx", "load: "+text);
                sb.append(text).append("\n");
                mEdtInputInternal.setText(text);

            }
        } catch (IOException ignored) {
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
        }

    }
}
