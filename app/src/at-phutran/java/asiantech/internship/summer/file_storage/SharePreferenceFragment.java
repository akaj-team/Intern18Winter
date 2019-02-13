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
import java.util.Objects;
import asiantech.internship.summer.R;

public class SharePreferenceFragment extends Fragment {
    private EditText mEdtUsername;
    private EditText mEdtPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_share_preference, container, false);
        mEdtUsername = itemView.findViewById(R.id.edtInputUser);
        mEdtPassword = itemView.findViewById(R.id.edtInputPassword);
        Button mBtnLogin = itemView.findViewById(R.id.btnLogin);
        loginHistory();
        mBtnLogin.setOnClickListener(view -> handleLogin());
        return itemView;
    }

    public void handleLogin() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences(getString(R.string.userInfor), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.user_key), mEdtUsername.getText().toString());
        editor.putString(getString(R.string.pass_key), mEdtPassword.getText().toString());
        editor.apply();
    }

    public void loginHistory() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences(getString(R.string.userInfor), Context.MODE_PRIVATE);
        if (sharedPreferences.contains(getString(R.string.user_key)) || sharedPreferences.contains(getString(R.string.pass_key))) {
            mEdtUsername.setText(sharedPreferences.getString(getString(R.string.user_key), getString(R.string.space)));
            mEdtPassword.setText(sharedPreferences.getString(getString(R.string.pass_key), getString(R.string.space)));
        }
    }
}
