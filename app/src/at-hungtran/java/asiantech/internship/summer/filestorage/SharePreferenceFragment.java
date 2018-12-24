package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.content.SharedPreferences;
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

import asiantech.internship.summer.R;

public class SharePreferenceFragment extends Fragment {
    private EditText mUsername;
    private EditText mPassword;
    private SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_preference, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mUsername = view.findViewById(R.id.edtUserName);
        mPassword = view.findViewById(R.id.edtPassword);
        Button mLogin = view.findViewById(R.id.btnLoginShare);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginref", Context.MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
        mLogin.setOnClickListener(v -> login());
        Boolean saveLogin = sharedPreferences.getBoolean("savelogin", true);
        if (saveLogin) {
            mUsername.setText(sharedPreferences.getString("username", null));
            mPassword.setText(sharedPreferences.getString("password", null));
        }
    }

    private void login() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        if (!username.equals("") && !password.equals("")) {
            mEditor.putBoolean("savelogin", true);
            mEditor.putString("username", username);
            mEditor.putString("password", password);
            mEditor.commit();
            Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_LONG).show();
        }
    }
}
