package asiantech.internship.summer.file_storage;

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

import asiantech.internship.summer.R;

public class SharePreferenceFragment extends Fragment {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private Button mBtnReset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View itemView = inflater.inflate(R.layout.fragment_share_preference, container, false);
        mEdtUsername = itemView.findViewById(R.id.edtInputUser);
        mEdtPassword = itemView.findViewById(R.id.edtInputPassword);
        mBtnLogin = itemView.findViewById(R.id.btnLogin);
        mBtnReset = itemView.findViewById(R.id.btnClearData);
        loginHistory();
        mBtnLogin.setOnClickListener(view -> {
            handleLogin();
        });
        mBtnReset.setOnClickListener(view -> {
            handleClearData();
        });
        return itemView;
    }

    public void handleLogin() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("UserInfor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", mEdtUsername.getText().toString());
        editor.putString("password", mEdtPassword.getText().toString());
        editor.apply();
    }

    public void loginHistory() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("UserInfor", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("username") || sharedPreferences.contains("password")) {
            mEdtUsername.setText(sharedPreferences.getString("username", ""));
            mEdtPassword.setText(sharedPreferences.getString("password", ""));
        }
    }

    public void handleClearData() {
        mEdtUsername.setText("");
        mEdtPassword.setText("");
    }
}
