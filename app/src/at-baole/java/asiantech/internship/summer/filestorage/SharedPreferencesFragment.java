package asiantech.internship.summer.filestorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class SharedPreferencesFragment extends Fragment {

    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View spView = inflater.inflate(R.layout.fragment_shared_preferences, container, false);
        mEdtUsername = spView.findViewById(R.id.edtUsername);
        mEdtPassword = spView.findViewById(R.id.edtPassword);
        mBtnLogin = spView.findViewById(R.id.btnLogin);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mPreferences.edit();
        checkSharedPreferences();
        onCLickLogin();
        return spView;
    }

    private void checkSharedPreferences() {
        String username = mPreferences.getString(getString(R.string.username), "");
        String password = mPreferences.getString(getString(R.string.password), "");

        mEdtUsername.setText(username);
        mEdtPassword.setText(password);
    }

    private void onCLickLogin() {
        mBtnLogin.setOnClickListener(view -> {
            if (mEdtUsername.getText().length() == 0 && mEdtPassword.getText().length() == 0) {
                Toast.makeText(getActivity(), R.string.inputNull, Toast.LENGTH_LONG).show();
            } else {
                String username = mEdtUsername.getText().toString();
                mEditor.putString(getString(R.string.username), username);
                mEditor.commit();

                String password = mEdtPassword.getText().toString();
                mEditor.putString(getString(R.string.password), password);
                mEditor.commit();

                Toast.makeText(getContext(), R.string.informationSaved, Toast.LENGTH_LONG).show();
            }
        });
    }
}
