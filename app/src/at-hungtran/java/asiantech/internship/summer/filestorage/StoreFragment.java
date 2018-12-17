package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import asiantech.internship.summer.R;

public class StoreFragment extends Fragment {
    File myInternalFile;
    private EditText mEdtInternal;
    private EditText mEdtExternal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initView(view);
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        //Thư mục do mình đặt
        String filepath = "MyDir";
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        String filename = "internalStorage.txt";
        myInternalFile = new File(directory, filename);
        return view;
    }

    private void initView(View view) {
        mEdtInternal = view.findViewById(R.id.edtInternal);
        mEdtExternal = view.findViewById(R.id.edtExternal);
        Button mBtnInternal = view.findViewById(R.id.btnInternal);
        Button mBtnExternal = view.findViewById(R.id.btnExternal);
        mBtnInternal.setOnClickListener(v -> onClickInternal());
    }

    private void onClickInternal() {
        String internal = mEdtInternal.getText().toString();
        try {
            //Mở file
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            //Ghi dữ liệu vào file
            fos.write(internal.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "Save successfully",
                Toast.LENGTH_LONG).show();
    }
}
